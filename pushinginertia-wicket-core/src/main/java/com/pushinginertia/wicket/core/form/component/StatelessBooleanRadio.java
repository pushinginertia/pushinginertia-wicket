/* Copyright (c) 2011-2016 Pushing Inertia
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
package com.pushinginertia.wicket.core.form.component;

import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.annotation.Nonnull;

/**
 * Provides a stateless radio button for boolean values. The difference is that the 'value' attribute in the html
 * input tag will be 'true' or 'false' instead of a wicket-generated incrementing value specific to the session.
 */
public class StatelessBooleanRadio extends Radio<Boolean> {
	public StatelessBooleanRadio(@Nonnull final String id, final boolean value) {
		this(id, Model.of(value));
	}

	public StatelessBooleanRadio(@Nonnull final String id, @Nonnull final IModel<Boolean> model) {
		super(id, model);
	}

	@Override
	public String getValue() {
		final Boolean modelObject = getModelObject();
		if (modelObject == null) {
			return "null";
		}
		return modelObject.toString();
	}

	@Override
	protected boolean getStatelessHint() {
		return true;
	}
}
