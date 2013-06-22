package com.pushinginertia.wicket.core.util;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Assert;
import org.junit.Test;

public class PageParametersUtilsTest {
	@Test
	public void getInt() {
		final PageParameters pp = new PageParameters();
		pp.set("k", "1");
		Assert.assertEquals(1, PageParametersUtils.getInt(pp, "k", 0));
	}
}
