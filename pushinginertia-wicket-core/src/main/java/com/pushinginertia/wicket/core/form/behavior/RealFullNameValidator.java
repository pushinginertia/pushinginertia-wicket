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
package com.pushinginertia.wicket.core.form.behavior;

import com.pushinginertia.commons.lang.CharUtils;
import com.pushinginertia.commons.lang.StringUtils;
import com.pushinginertia.commons.core.validation.ValidateAs;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.util.lang.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Performs validation on inputs for first and family names, ensuring that the following rules are followed.
 * <ul>
 *     <li>they are each at least two characters (unless CJK characters are present - see below)</li>
 *     <li>one of the characters in each name is not a period</li>
 *     <li>they are not the same values (regardless of case)</li>
 *     <li>they don't contain characters that would not exist in a real name (such as punctuation)</li>
 * </ul>
 * If CJK (Chinese, Japanese, Korean) characters are entered, the minimum length of two characters is not enforced.
 * This is because it's common for a name to appear as only one character in these languages.
 */
public class RealFullNameValidator extends AbstractFormValidator {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(RealFullNameValidator.class);

	private static final char[] ILLEGAL_CHARS =
			{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			 '!', '@', '#', '$', '%', '^', '&', '*', '(', ')',
			 '+', '_', '=', '<', '>', '?', '/', ':', ';', '"',
			 '[', ']', '{', '}', '|', '~', '\\'};

	private final TextField<String> firstName;
	private final TextField<String> familyName;

	/**
	 * Performs validation on inputs for first and family names, ensuring that a set of rules are followed.
	 * @param firstName input for the user's first name
	 * @param familyName input for the user's family name
	 * @see RealFullNameValidator
	 */
	public RealFullNameValidator(final TextField<String> firstName, final TextField<String> familyName) {
		this.firstName = ValidateAs.notNull(firstName, "firstName");
		this.familyName = ValidateAs.notNull(familyName, "familyName");
	}

	@Override
	public FormComponent<?>[] getDependentFormComponents() {
		return new TextField[] {firstName, familyName};
	}

	@Override
	public void validate(final Form<?> form) {
		// 1. minimum length and no dot
		if (!satisfiesLengthWithoutDot(firstName)) {
			LOG.info(toLogString(form, firstName));
			error(firstName);
			return;
		}
		if (!satisfiesLengthWithoutDot(familyName)) {
			LOG.info(toLogString(form, familyName));
			error(familyName);
			return;
		}

		// 2. check equality
		if (Objects.equal(firstName.getInput().toLowerCase(), familyName.getInput().toLowerCase())) {
			LOG.info(toLogString(form, firstName));
			error(firstName);
			return;
		}

		// 3. check for illegal characters
		if (CharUtils.inCharArray(firstName.getInput(), ILLEGAL_CHARS) >= 0) {
			LOG.info(toLogString(form, firstName));
			error(firstName);
		} else if (CharUtils.inCharArray(familyName.getInput(), ILLEGAL_CHARS) >= 0) {
			LOG.info(toLogString(form, familyName));
			error(familyName);
		}
	}

	private String toLogString(final Form form, final TextField<String> errorComponent) {
		final String id = errorComponent.getId();
		final String pageClass = form.getPage().getClass().getSimpleName();
		return pageClass + " failed validation on input [" + id + "]: " + toString(firstName) + ", " + toString(familyName);
	}

	private String toString(final TextField<String> tf) {
		final String id = tf.getId();
		final String input = tf.getInput();
		return id + "=[" + input + ']';
	}

	private static boolean satisfiesLengthWithoutDot(final TextField<String> tf) {
		return satisfiesLengthWithoutDot(tf.getInput());
	}

	static boolean satisfiesLengthWithoutDot(final String input) {
		final boolean latin = StringUtils.isLatin(input);
		if (latin) {
			// fail if input is less than 2 characters long
			if (input.length() < 2) {
				return false;
			}
		} else {
			// fail if input is less than 1 character long when non-latin characters are present
			// (some languages can represent a name with only one character)
			if (input.length() < 1) {
				return false;
			}
		}

		// fail if input is 2 characters but contains a dot
		if (input.length() == 2 && input.indexOf('.') >= 0) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "RealFullNameValidator{firstName=" + firstName + ", familyName=" + familyName + '}';
	}
}
