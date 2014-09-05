/* Copyright (c) 2011-2014 Pushing Inertia
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
package com.pushinginertia.wicket.core.form.validation;

import com.pushinginertia.commons.core.validation.ValidateAs;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;

/**
 * A validator that rejects inputs containing more than a maximum number of digits.
 */
public class MaxDigitsValidator extends AbstractValidator<String> {
	private static final long serialVersionUID = 1L;

	private final int maxDigits;

	public MaxDigitsValidator(final int maxDigits) {
		ValidateAs.positive(maxDigits, "maxDigits");
		this.maxDigits = maxDigits;
	}

	@Override
	protected void onValidate(final IValidatable<String> validatable) {
		final String value = validatable.getValue();
		if (value == null || value.length() <= maxDigits) {
			return;
		}

		if (!isValid(value, maxDigits)) {
			error(validatable);
		}
	}

	static boolean isValid(final String value, final int maxDigits) {
		int count = 0;
		for (int i = 0; i < value.length(); i++) {
			final char c = value.charAt(i);
			if (c >= '0' && c <= '9') {
				count++;
				if (count > maxDigits) {
					// fail immediately when max is exceeded
					return false;
				}
			}
		}
		return true;
	}
}
