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

import com.pushinginertia.commons.ui.TimePeriod;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.joda.time.Duration;

/**
 * Generates a string for a time duration in the most appropriate unit of measure for the duration (e.g., days, weeks,
 * months, etc.). The string is generated using {@link TimePeriod}.
 */
public class TimeDurationModel extends LoadableDetachableModel<String> {
	private static final long serialVersionUID = 1L;

	private final TimePeriod period;

	/**
	 * Generates a string for a time duration in the most appropriate unit of measure for the duration (e.g., days, weeks,
	 * months, etc.). The string is generated using {@link TimePeriod}.
	 * @param duration time duration to present
	 * @see TimePeriod
	 */
	public TimeDurationModel(final Duration duration) {
		period = new TimePeriod(duration);
	}

	@Override
	protected String load() {
		final String descriptorResourceKey = period.getDescriptorResourceKey();
		final long quantity = period.getQuantity();

		final String descriptor = loadResourceString(descriptorResourceKey);
		final String durationString = descriptor.replace("${i}", Long.toString(quantity));

		final StringBuilder value = new StringBuilder();
		final CharSequence prefix = getPrefix();
		final CharSequence suffix = getSuffix();
		if (prefix != null)
			value.append(prefix);
		value.append(durationString);
		if (suffix != null)
			value.append(suffix);

		return value.toString();
	}

	protected String loadResourceString(final String resourceKey) {
		final ResourceModel model = new ResourceModel(resourceKey);
		return model.getObject();
	}

	public String getDuration() {
		return getObject();
	}

	/**
	 * Override this method to define a string to prepend to the beginning of the generated duration string (e.g., "(").
	 * @return null for no prefix (default behaviour)
	 */
	public CharSequence getPrefix() {
		return null;
	}

	/**
	 * Override this method to define a string to append to the end of the generated duration string (e.g., ")").
	 * @return null for no suffix (default behaviour)
	 */
	public CharSequence getSuffix() {
		return null;
	}
}
