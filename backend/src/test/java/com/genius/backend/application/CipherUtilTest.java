package com.genius.backend.application;

import com.genius.backend.application.util.CipherUtil;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class CipherUtilTest {

	@Test
	public void encryptEmailAndDecryptEmailTest() throws DecoderException {
		String originalEmail = "genius.choi@wemakeprice.com";
		String destinationEmail = CipherUtil.decryptEmail(CipherUtil.encryptEmail(originalEmail));
		Assert.assertThat(originalEmail, is(destinationEmail));
		//Hex.decodeHex("c1ff97f6774a34cae993221bd79382a8edca1e2b79a6edb2fbf8565ac89fc853");
	}
}