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
package com.pushinginertia.wicket.core.form.validation;

import org.apache.wicket.validation.validator.StringValidator;import java.lang.Override;import java.lang.String;

/**
 * This is used so that a custom resource key name can be used for password minimum lengths.
 */
public class PasswordMinimumLengthValidator extends StringValidator.MinimumLengthValidator {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that sets a minimum length value.
	 * @param minimum the minimum length value
	 */
	public PasswordMinimumLengthValidator(final int minimum) {
		super(minimum);
	}

	@Override
	protected String resourceKey() {
		return "PasswordMinimumLengthValidator";
	}
}
