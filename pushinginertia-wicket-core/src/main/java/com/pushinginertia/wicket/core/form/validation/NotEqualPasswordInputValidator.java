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

import org.apache.wicket.markup.html.form.FormComponent;import java.lang.String;

/**
 * Can be used to verify that a new password isn't the same as an old password in a 'change password' form. Add this
 * validator to the form containing inputs for the current password and the new password.
 */
public class NotEqualPasswordInputValidator extends NotEqualInputValidator {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates the validator to compare the old and new passwords.
	 * @param currentPassword input for the user's current password
	 * @param newPassword input for the user's new password
	 */
	public NotEqualPasswordInputValidator(final FormComponent<String> currentPassword, final FormComponent<String> newPassword) {
		super(currentPassword, newPassword);
	}
}
