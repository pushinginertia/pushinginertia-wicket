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

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.model.Model;

import javax.annotation.Nonnull;

/**
 * A radio button for a specific value in an enumeration. No state is required for this radio button and therefore
 * {@link #getStatelessHint()} returns true.
 */
public class EnumRadio<E extends Enum<E>> extends Radio<E> {
	private static final long serialVersionUID = 1L;

	private final E enumValue;

	/**
	 * @param id ID of the {@link org.apache.wicket.markup.html.form.RadioGroup} this radio is a child of. The ID of
	 *           this radio will be a concatenation of the radio group's ID and the given enum value, which produces
	 *           what should be a globally unique ID for the form.
	 * @param enumValue Value for this radio button.
	 */
	public EnumRadio(@Nonnull final String id, @Nonnull final E enumValue) {
		super(id + "_" + enumValue.name(), Model.of(enumValue));
		this.enumValue = enumValue;
	}

	@Override
	public String getValue() {
		// this returns the enum string instead of the 'radioNN' value based on the uid
		// it fixes problems in the search homestay page but could break other things in the future
		return enumValue.toString();
	}

	@Override
	protected void onComponentTag(final ComponentTag tag) {
		super.onComponentTag(tag);
		if (getGroup().isRequired()) {
			tag.put("required", "required");
		}
	}

	@Override
	public String getMarkupId() {
		return getId();
	}

	@Override
	protected boolean getStatelessHint() {
		return true;
	}
}
