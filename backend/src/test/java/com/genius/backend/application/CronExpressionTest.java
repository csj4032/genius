package com.genius.backend.application;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.quartz.CronExpression;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cronutils.model.CronType.QUARTZ;
import static org.hamcrest.core.Is.is;

public class CronExpressionTest {

	@Ignore
	@Test
	public void cronExpressionValidTest() {
		CronExpression.isValidExpression("0 * * ? * * *");
	}

	@Ignore
	@Test
	public void cronExpressionValidForMinutes() {
		String minutes = "0-59";
		Pattern p = Pattern.compile("((([0-9]|[0-5][0-9])(-([0-9]|[0-5][0-9]))?,)*([0-9]|[0-5][0-9])(-([0-9]|[0-5][0-9]))?)|(([\\*]|[0-9]|[0-5][0-9])/([0-9]|[0-5][0-9]))|([\\?])|([\\*])");
		Matcher m = p.matcher(minutes);
		Assert.assertTrue(m.matches());
	}

	@Test
	public void cronDescriptorTest() {
		CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
		CronParser parser = new CronParser(cronDefinition);
		Cron quartzCron = parser.parse("0 2 10-12,14-18 ? * 2-6 2019-2099");
		String descriptor = CronDescriptor.instance(Locale.KOREA).describe(quartzCron);
		System.out.println(descriptor);
		Assert.assertEquals("매 분 매 년 2019 ~ 2099 까지", descriptor);
		quartzCron.validate();
	}
}