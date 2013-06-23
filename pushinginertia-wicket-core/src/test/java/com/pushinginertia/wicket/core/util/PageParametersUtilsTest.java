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
import org.junit.Assert;
import org.junit.Test;

public class PageParametersUtilsTest {
	@Test
	public void getInt() {
		final PageParameters pp = new PageParameters();

		Assert.assertEquals(0, PageParametersUtils.getInt(pp, "k", 0));

		pp.set("k", "x");
		Assert.assertEquals(0, PageParametersUtils.getInt(pp, "k", 0));

		pp.set("k", "1");
		Assert.assertEquals(1, PageParametersUtils.getInt(pp, "k", 0));
	}
}
