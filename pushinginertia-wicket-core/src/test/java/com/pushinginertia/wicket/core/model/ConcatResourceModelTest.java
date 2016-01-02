/* Copyright (c) 2011-2015 Pushing Inertia
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
package com.pushinginertia.wicket.core.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ConcatResourceModelTest {
	@Test
	public void getObjectFromList() {
		final ArrayList<IModel<String>> list = new ArrayList<>();
		list.add(Model.of("value1"));
		list.add(Model.of("value2"));
		list.add(Model.of("value3"));
		final ConcatResourceModel model = ConcatResourceModel.forList(", ", list);
		Assert.assertEquals("value1, value2, value3", model.getObject());
	}

	@Test
	public void getObjectFromBuilder() {
		final ConcatResourceModel.Builder builder = ConcatResourceModel.builder(", ");
		builder.add(Model.of("value1"));
		builder.add(Model.of("value2"));
		builder.add(Model.of("value3"));
		Assert.assertEquals("value1, value2, value3", builder.build().getObject());
	}

	@Test
	public void noSeparator() {
		final ConcatResourceModel.Builder builder = ConcatResourceModel.builderNoSeparator();
		builder.add(Model.of("value1"));
		builder.add(Model.of("value2"));
		Assert.assertEquals("value1value2", builder.build().getObject());
	}
}