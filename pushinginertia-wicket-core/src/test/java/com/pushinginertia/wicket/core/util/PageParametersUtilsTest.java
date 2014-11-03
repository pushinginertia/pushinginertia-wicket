/* Copyright (c) 2011-2013 Pushing Inertia
 * All rights reserved.  http://pushinginertia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pushinginertia.wicket.core.util;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.*;
import org.junit.Assert;
import org.junit.Test;

public class PageParametersUtilsTest {
	@Test
	public void copySubset() {
		final PageParameters pp = new PageParameters();

		final PageParameters pp0 = PageParametersUtils.copySubset(pp, "a", "b", "c");
		Assert.assertEquals(0, pp0.getNamedKeys().size());

		pp.add("a", "1");
		final PageParameters pp1 = PageParametersUtils.copySubset(pp, "a", "b", "c");
		Assert.assertEquals(1, pp1.getNamedKeys().size());
		Assert.assertEquals(1, pp1.getValues("a").size());
		Assert.assertEquals("1", pp1.get("a").toString());

		pp.add("a", "2");
		final PageParameters pp2 = PageParametersUtils.copySubset(pp, "a", "b", "c");
		Assert.assertEquals(1, pp2.getNamedKeys().size());
		Assert.assertEquals(2, pp2.getValues("a").size());
		Assert.assertTrue(pp2.getValues("a").contains(StringValue.valueOf("1")));
		Assert.assertTrue(pp2.getValues("a").contains(StringValue.valueOf("2")));

		pp.add("b", "3");
		final PageParameters pp3 = PageParametersUtils.copySubset(pp, "a", "b", "c");
		Assert.assertEquals(2, pp3.getNamedKeys().size());
		Assert.assertEquals(2, pp3.getValues("a").size());
		Assert.assertTrue(pp3.getValues("a").contains(StringValue.valueOf("1")));
		Assert.assertTrue(pp3.getValues("a").contains(StringValue.valueOf("2")));
		Assert.assertEquals(1, pp3.getValues("b").size());
		Assert.assertTrue(pp3.getValues("b").contains(StringValue.valueOf("3")));
	}

	@Test
	public void getInt() {
		final PageParameters pp = new PageParameters();

		Assert.assertEquals(0, PageParametersUtils.getInt(pp, "k", 0));

		pp.set("k", "x");
		Assert.assertEquals(0, PageParametersUtils.getInt(pp, "k", 0));

		pp.set("k", "1");
		Assert.assertEquals(1, PageParametersUtils.getInt(pp, "k", 0));
	}

	public enum TestEnum {
		VALUE_1,
		SECOND_VALUE,
		VALUE_NUMBER_3,
		FOUR
	}

	@Test
	public void getEnum() {
		final PageParameters pp = new PageParameters();

		Assert.assertEquals(TestEnum.VALUE_1, PageParametersUtils.getEnum(pp, "k", TestEnum.class, TestEnum.VALUE_1));

		pp.set("k", "");
		Assert.assertEquals(TestEnum.VALUE_1, PageParametersUtils.getEnum(pp, "k", TestEnum.class, TestEnum.VALUE_1));
		pp.set("k", "x");
		Assert.assertEquals(TestEnum.VALUE_1, PageParametersUtils.getEnum(pp, "k", TestEnum.class, TestEnum.VALUE_1));

		pp.set("k", "four");
		Assert.assertEquals(TestEnum.FOUR, PageParametersUtils.getEnum(pp, "k", TestEnum.class, TestEnum.VALUE_1));
		pp.set("k", "Four");
		Assert.assertEquals(TestEnum.FOUR, PageParametersUtils.getEnum(pp, "k", TestEnum.class, TestEnum.VALUE_1));
		pp.set("k", "FOUR");
		Assert.assertEquals(TestEnum.FOUR, PageParametersUtils.getEnum(pp, "k", TestEnum.class, TestEnum.VALUE_1));
	}
}
