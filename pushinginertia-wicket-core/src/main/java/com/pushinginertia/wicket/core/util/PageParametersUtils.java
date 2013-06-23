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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Some common logic when working with page parameters.
 */
public final class PageParametersUtils {
	public static final Logger LOG = LoggerFactory.getLogger(PageParametersUtils.class);

	/**
	 * Retrieves an integer value from given page parameters, returning a default value if the name doesn't exist in the
	 * parameters or the value cannot be parsed into an integer.
	 * @param pp parameters to load the value from
	 * @param name name to look up
	 * @param defaultValue default value if one cannot be parsed
	 * @return parsed value
	 */
	public static int getInt(final PageParameters pp, final String name, final int defaultValue) {
		final StringValue value = pp.get(name);
		if (value.isEmpty()) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(value.toString());
		} catch (final NumberFormatException e) {
			LOG.info("Page parameter value [{}] from name [{}] could not be parsed into an integer.", value, name);
			return defaultValue;
		}
	}

	private PageParametersUtils() {}
}
