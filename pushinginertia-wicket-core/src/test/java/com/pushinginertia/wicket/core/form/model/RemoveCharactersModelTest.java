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
package com.pushinginertia.wicket.core.form.model;

import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;

public class RemoveCharactersModelTest {
	private final WicketTester tester = new WicketTester();

	private static class TestBean {
		private String name;
	}

	@Test
	public void setObject() {
		final TestBean bean = new TestBean();
		bean.name = "abcd";
		final PropertyModel<String> innerModel = new PropertyModel<String>(bean, "name");
		final RemoveCharactersModel model = new RemoveCharactersModel(innerModel, new char[]{'1', '2'});
		model.setObject("abc123");
		Assert.assertEquals("abc3", model.getObject());
	}
}
