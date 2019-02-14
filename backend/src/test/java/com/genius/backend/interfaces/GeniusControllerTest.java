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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "office")
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
	public void contextLoads() {
		assertThat(geniusController).isNotNull();
	}

	@Test
	public void alimyMain() throws Exception {
		this.mockMvc.perform(get("/")
				.with(SecurityMockMvcRequestPostProcessors.authentication(authentication))
				.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
				.header("X-FORWARDED-FOR", "127.0.0.1")
				.contentType(MediaType.TEXT_HTML))
				.andDo(print())
				.andExpect(status().isOk());
	}
}