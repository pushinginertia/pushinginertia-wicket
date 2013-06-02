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

import com.pushinginertia.commons.core.validation.ValidateAs;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.util.lang.Objects;

/**
 * Validates that the input of two form components are not identical. Errors are reported on the second
 * form component with key 'NotEqualInputValidator'.
 * @see org.apache.wicket.markup.html.form.validation.EqualInputValidator
 */
public class NotEqualInputValidator extends AbstractFormValidator {
	private static final long serialVersionUID = 1L;

	/**
	 * Form components to be checked.
	 */
	private final FormComponent<String>[] components;

	/**
	 * Construct.
	 *
	 * @param formComponent1 a form component
	 * @param formComponent2 a form component
	 */
	public NotEqualInputValidator(final FormComponent<String> formComponent1, final FormComponent<String> formComponent2)
	{
		ValidateAs.notNull(formComponent1, "formComponent1");
		ValidateAs.notNull(formComponent2, "formComponent2");
		components = new FormComponent[] { formComponent1, formComponent2 };
	}

	/**
	 * @see org.apache.wicket.markup.html.form.validation.IFormValidator#getDependentFormComponents()
	 */
	public FormComponent<String>[] getDependentFormComponents() {
		return components;
	}

	/**
	 * @see org.apache.wicket.markup.html.form.validation.IFormValidator#validate(org.apache.wicket.markup.html.form.Form)
	 */
	public void validate(final Form<?> form) {
		// we have a choice to validate the type converted values or the raw
		// input values, we validate the raw input
		final FormComponent<String> formComponent1 = components[0];
		final FormComponent<String> formComponent2 = components[1];

		if (Objects.equal(formComponent1.getInput(), formComponent2.getInput())) {
			error(formComponent2);
		}
	}
}