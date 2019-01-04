package com.genius.backend.application;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.regex.Pattern;

public class SendShortMessageServiceTest {

	private String smsauthKey = "tptkddmlwndtlarkwhr";

	@Test
	public void validateCellNumber() {
		Pattern pattern = Pattern.compile("^(010|011|016|017|018|019)\\d{3,4}\\d{4}$");
		Assert.assertFalse(pattern.matcher("01535297530").find());
		Assert.assertFalse(pattern.matcher("01635").find());
		Assert.assertFalse(pattern.matcher("01635297530000").find());
		Assert.assertTrue(pattern.matcher("01635297530").find());

		System.out.println(System.currentTimeMillis());
		System.out.println(LocalDateTime.now().atZone(ZoneId.of("Asia/Tokyo")).toInstant().toEpochMilli());
		System.out.println(LocalDateTime.now().atZone(ZoneId.of("Asia/Tokyo")));
	}

	public String namuMcrypt(String key, String plainText) {
		plainText = StringUtils.trim(plainText);
		return  null;
	}
}