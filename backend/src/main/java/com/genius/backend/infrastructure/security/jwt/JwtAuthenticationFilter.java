package com.genius.backend.infrastructure.security.jwt;

import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	private RequestMatcher requiresAuthenticationRequestMatcher;

	public JwtAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		this.requiresAuthenticationRequestMatcher = requiresAuthenticationRequestMatcher;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
		if (!requiresAuthenticationRequestMatcher.matches(httpServletRequest))
			filterChain.doFilter(httpServletRequest, httpServletResponse);

		var jwt = getJwtFromRequest(httpServletRequest);

		if (jwtTokenProvider.validateToken(jwt)) {
			var geniusSocialUserDetail = GeniusSocialUserDetail.create(jwtTokenProvider.getGeniusUserDetailTokenFromJWT(jwt));
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(geniusSocialUserDetail, geniusSocialUserDetail.getPassword(), geniusSocialUserDetail.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		var bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}