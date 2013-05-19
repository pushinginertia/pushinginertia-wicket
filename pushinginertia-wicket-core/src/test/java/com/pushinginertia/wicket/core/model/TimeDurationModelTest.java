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
package com.pushinginertia.wicket.core.model;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Test;

public class TimeDurationModelTest {
	@Test
	public void load() {
		final DateTime start = DateTime.now();
		final DateTime end = start.plusDays(2);
		final Duration duration = new Duration(start, end);

		final TimeDurationModel model = new TimeDurationModel(duration) {
			private static final long serialVersionUID = 1L;

			@Override
			protected String loadResourceString(final String resourceKey) {
				Assert.assertEquals("TimePeriod.Days", resourceKey);
				return "${i} days";
			}
		};

		Assert.assertEquals("2 days", model.load());
	}
}
