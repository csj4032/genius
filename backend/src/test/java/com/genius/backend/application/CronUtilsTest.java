package com.genius.backend.application;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Date;

@Slf4j
public class CronUtilsTest {

	@Test
	public void cronParserTest() throws ParseException {
		// 초 분 시 일 월 요일 연도
		String schedule = "* 19/20/21/22/23 * * * ?";
		CronExpression cronExpression = new CronExpression(schedule);
		System.out.println(cronExpression.isSatisfiedBy(new Date()));
	}
}