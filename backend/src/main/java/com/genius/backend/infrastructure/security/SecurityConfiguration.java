package com.genius.backend.infrastructure.security;

import com.genius.backend.infrastructure.security.jwt.JwtAuthenticationFilter;
import com.genius.backend.infrastructure.security.jwt.JwtOnAuthenticationFilter;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetailService;
import com.genius.backend.infrastructure.security.social.KakaoConnectionSignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
)
@ComponentScan(basePackages = {"com.genius.backend"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static String alimyUrl = "/alimy/**";
	private static String logUrl = "/log/**";

	@Autowired
	private AuthenticationProvider geniusDaoAuthenticationProvider;

	@Value("${ms.allowedOrigins}")
	private List allowedOrigins;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(geniusDaoAuthenticationProvider);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/favicon.ico", "/favicon_fast.ico", "/static/**", "/js/**", "/css/**", "/webjars/**", "/error");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors()
				.and()
				.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers("/**").permitAll()
				.antMatchers("/actuator/**").hasRole("ADMIN")
				.and()
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtOnAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling()
				.defaultAuthenticationEntryPointFor(authenticationEntryPoint(), new AntPathRequestMatcher(alimyUrl))
				.defaultAuthenticationEntryPointFor(authenticationEntryPoint(), new AntPathRequestMatcher("/log/**"))
				.defaultAuthenticationEntryPointFor(new Http403ForbiddenEntryPoint(), new AntPathRequestMatcher("/**"))
				.accessDeniedHandler(accessDeniedHandler());
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(new OrRequestMatcher(new AntPathRequestMatcher(logUrl), new AntPathRequestMatcher(alimyUrl)));
	}

	@Bean
	public JwtOnAuthenticationFilter jwtOnAuthenticationFilter() throws Exception {
		return new JwtOnAuthenticationFilter("/auth/", authenticationManager());
	}

	@Bean
	public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository, KakaoConnectionSignUp kakaoConnectionSignup, SignInAdapter signInAdapter) {
		((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(kakaoConnectionSignup);
		return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, signInAdapter);
	}

	@Bean
	public SocialUserDetailsService socialUserDetailsService() {
		return new GeniusSocialUserDetailService(userDetailsService());
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		return new GeniusUserDetailsService();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new GeniusAccessDeniedHandler();
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new GeniusAuthenticationEntryPoint();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/alimy/**", configuration);
		source.registerCorsConfiguration("/auth/**", configuration);
		source.registerCorsConfiguration("/error/**", configuration);
		return source;
	}
}