package com.genius.backend.interfaces;

import com.genius.backend.domain.model.user.User;
import com.genius.backend.domain.repository.UserRepository;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles(value = "home")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GeniusControllerTest {

	@Autowired
	private GeniusController geniusController;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	private GeniusSocialUserDetail geniusSocialUserDetail;
	private Authentication authentication;
	private User user;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		user = userRepository.findById(1L).get();
		geniusSocialUserDetail = GeniusSocialUserDetail.create(user);
		authentication = new UsernamePasswordAuthenticationToken(geniusSocialUserDetail, null, geniusSocialUserDetail.getAuthorities());
	}

	@Test
	@Ignore
	public void contextLoads() {
		assertThat(geniusController).isNotNull();
	}

	@Test
	@Ignore
	public void alimySaveTest() throws Exception {
		this.mockMvc.perform(post("/")
				.with(SecurityMockMvcRequestPostProcessors.authentication(authentication))
				.param("alimyId", "")
				.param("status", "1")
				.param("subject", "테스트입니다.")
				.param("message", "테스트입니다.")
				.param("unitType.year", "2019-2020")
				.param("unitType.month", "1-12")
				.param("unitType.dayOfMonth", "?")
				.param("unitType.dayOfWeek", "1-7")
				.param("unitType.hours", "0-23")
				.param("unitType.minutes", "0")
				.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
				.header("X-FORWARDED-FOR", "127.0.0.1")
				.contentType(MediaType.TEXT_HTML))
				.andDo(print())
				.andExpect(status().is3xxRedirection());
	}

	@Test
	@Ignore
	public void alimyUpdateTest() throws Exception {
		this.mockMvc.perform(post("/")
				.with(SecurityMockMvcRequestPostProcessors.authentication(authentication))
				.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
				.header("X-FORWARDED-FOR", "127.0.0.1")
				.param("alimyId", "1")
				.param("status", "1")
				.param("subject", "테스트입니다.1")
				.param("message", "테스트입니다.1")
				.param("unitType.year", "2019-2020")
				.param("unitType.month", "1-12")
				.param("unitType.dayOfMonth", "?")
				.param("unitType.dayOfWeek", "1-7")
				.param("unitType.hours", "0-23")
				.param("minutes", "0")
				.contentType(MediaType.TEXT_HTML))
				.andDo(print())
				.andExpect(status().is3xxRedirection());
	}

	@Test
	public void alimyDeleteTest() throws Exception {
		this.mockMvc.perform(delete("/")
				.with(SecurityMockMvcRequestPostProcessors.authentication(authentication))
				.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
				.header("X-FORWARDED-FOR", "127.0.0.1")
				.param("alimyId", "1")
				.contentType(MediaType.TEXT_HTML))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@Ignore
	public void alimyStatusTest() throws Exception {
		this.mockMvc.perform(put("/")
				.with(SecurityMockMvcRequestPostProcessors.authentication(authentication))
				.param("alimyId", "1")
				.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
				.header("X-FORWARDED-FOR", "127.0.0.1")
				.contentType(MediaType.TEXT_HTML))
				.andDo(print())
				.andExpect(status().isOk());
	}

	private static RequestPostProcessor remoteAddr(final String remoteAddr) {
		return (request) -> {
			request.setRemoteAddr(remoteAddr);
			return request;
		};
	}
}