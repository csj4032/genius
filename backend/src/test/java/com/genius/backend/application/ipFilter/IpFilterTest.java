package com.genius.backend.application.ipFilter;

import org.chimi.ipfilter.Config;
import org.chimi.ipfilter.IpFilter;
import org.chimi.ipfilter.IpFilters;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * https://github.com/madvirus/ip-filter/wiki
 */
public class IpFilterTest {

	@Test
	public void basicUsageOfIpFilter() {
		Config config = new Config();
		config.setAllowFirst(true);
		config.setDefaultAllow(false);
		config.allow("1.2.3.4");
		config.allow("10.20.30.40");
		config.deny("101.102.103.104");

		IpFilter ipFilter = IpFilters.create(config);

		assertTrue(ipFilter.accept("1.2.3.4"));
		assertFalse(ipFilter.accept("101.102.103.104"));
	}
}
