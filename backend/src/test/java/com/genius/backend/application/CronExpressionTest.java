package com.genius.backend.application;

import org.junit.Assert;
import org.junit.Test;
import org.quartz.CronExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.core.Is.is;

public class CronExpressionTest {

	@Test
	public void CronExpressionValidTest() {
		CronExpression.isValidExpression("0 * * ? * * *");
	}

	@Test
	public void CronExpressionValidForMinutes() {
		String minutes = "0-59";
		Pattern p = Pattern.compile("((([0-9]|[0-5][0-9])(-([0-9]|[0-5][0-9]))?,)*([0-9]|[0-5][0-9])(-([0-9]|[0-5][0-9]))?)|(([\\*]|[0-9]|[0-5][0-9])/([0-9]|[0-5][0-9]))|([\\?])|([\\*])");
		Matcher m = p.matcher(minutes);
		Assert.assertTrue(m.matches());
	}
}