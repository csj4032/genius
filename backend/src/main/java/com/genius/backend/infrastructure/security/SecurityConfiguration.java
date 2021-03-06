package com.genius.backend.infrastructure.security;

import com.genius.backend.infrastructure.security.jwt.JwtAuthenticationFilter;
import com.genius.backend.infrastructure.security.jwt.JwtOnAuthenticationFilter;
import com.genius.backend.infrastructure.security.social.GeniusConnectionSignUp;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetailService;
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
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
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

	private static String apiUrl = "/api/**";

	@Autowired
	private AuthenticationProvider geniusDaoAuthenticationProvider;

	@Value("${ms.allowedOrigins}")
	private List allowedOrigins;

	@Value("${spring.application.url}")
	private String applicationUrl;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) {
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
				.formLogin()
					.loginPage("/signin")
				.and()
				.authorizeRequests()
				.antMatchers("/**").permitAll()
				.antMatchers("/actuator/**").hasRole("ADMIN")
				.and()
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtOnAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.sessionManagement()
				.maximumSessions(1)
				.and()
				.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
				.and()
				.exceptionHandling()
				.defaultAuthenticationEntryPointFor(authenticationEntryPoint(), new AntPathRequestMatcher(apiUrl))
				//.defaultAuthenticationEntryPointFor(new Http403ForbiddenEntryPoint(), new AntPathRequestMatcher("/**"))
				.accessDeniedHandler(accessDeniedHandler());
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(new OrRequestMatcher(new AntPathRequestMatcher(apiUrl)));
	}

	@Bean
	public JwtOnAuthenticationFilter jwtOnAuthenticationFilter() throws Exception {
		return new JwtOnAuthenticationFilter("/auth/", authenticationManager());
	}

	@Bean
	public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository, GeniusConnectionSignUp geniusConnectionSignUp, SignInAdapter signInAdapter) {
		usersConnectionRepository.setConnectionSignUp(geniusConnectionSignUp);
		var providerSignInController = new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, signInAdapter);
		providerSignInController.setApplicationUrl(applicationUrl);
		return providerSignInController;
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
		source.registerCorsConfiguration("/api/**", configuration);
		source.registerCorsConfiguration("/auth/**", configuration);
		return source;
	}
}