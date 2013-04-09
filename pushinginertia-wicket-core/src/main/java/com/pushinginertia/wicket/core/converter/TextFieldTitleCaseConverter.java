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
package com.pushinginertia.wicket.core.converter;

import com.pushinginertia.commons.lang.NormalizeStringCaseUtils;
import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * A converter that fixes the case entered by a user.
 */
public class TextFieldTitleCaseConverter extends AbstractConverter<String> {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(TextFieldTitleCaseConverter.class);
	public static final int UPPERCASE_THRESHOLD = 55;
	public static final int LOWERCASE_THRESHOLD = 85;

	private final int uppercaseThreshold;
	private final int lowercaseThreshold;

	public TextFieldTitleCaseConverter() {
		uppercaseThreshold = UPPERCASE_THRESHOLD;
		lowercaseThreshold = LOWERCASE_THRESHOLD;
	}

	/**
	 * A converter that fixes the case entered by a user.
	 * @param uppercaseThreshold only perform conversion if the string is > N% uppercase
	 * @param lowercaseThreshold only perform conversion if the string is > N% lowercase
	 */
	public TextFieldTitleCaseConverter(final int uppercaseThreshold, final int lowercaseThreshold) {
		this.uppercaseThreshold = uppercaseThreshold;
		this.lowercaseThreshold = lowercaseThreshold;
	}

	/**
	 * Converts to the value rendered in the form input.
	 */
	@Override
	public String convertToString(final String value, final Locale locale) {
		return value;
	}

	/**
	 * Converts to the value used internally. This is where changes take place.
	 */
	public String convertToObject(final String value, final Locale locale) {
		// 1. if no input, return null
		if ((value == null) || Strings.isEmpty(value))
			return null;

		final String normalized =
				NormalizeStringCaseUtils.normalizeCase(
						value,
						NormalizeStringCaseUtils.TargetCase.TITLE,
						NormalizeStringCaseUtils.Scope.PER_WORD,
						uppercaseThreshold,
						lowercaseThreshold);
		if (!value.equals(normalized)) {
			LOG.info("Normalized input string to title case from [{}] to [{}].", value, normalized);
		}
		return normalized;
	}

	@Override
	protected Class<String> getTargetType() {
		return String.class;
	}
}
