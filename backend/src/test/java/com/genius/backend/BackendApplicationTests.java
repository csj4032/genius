package com.genius.backend;

import com.genius.backend.application.util.CipherUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;

@ActiveProfiles(value = "home")
@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {

	@Test
	public void contextLoads() {
		String original = "genius.choi@wemakeprice.com";
		String destination = CipherUtil.encryptEmail(original);
		String decrypt = CipherUtil.decryptEmail(destination);
		Assert.assertThat(original, is(decrypt));
	}
}