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
package com.pushinginertia.wicket.core.form.model;

import com.pushinginertia.commons.lang.StringUtils;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A model that can be attached to a form input, which will strip a given list characters from the input entered by
 * the user. This wraps another model that represents the string that the form input will be assigned to.
 */
public class RemoveCharactersModel extends Model<String> {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(RemoveCharactersModel.class);

	private final IModel<String> nestedModel;
	private final char[] illegalChars;

	/**
	 * A model that can be attached to a form input, which will strip a given list characters from the input entered by
	 * the user. This wraps another model that represents the string that the form input will be assigned to.
	 * @param model model containing the user's input
	 * @param illegalChars characters to strip
	 */
	public RemoveCharactersModel(final IModel<String> model, final char[] illegalChars) {
		this.nestedModel = model;
		this.illegalChars = illegalChars;
	}

	@Override
	public String getObject() {
		return nestedModel.getObject();
	}

	@Override
	public void setObject(final String value) {
		if (value == null) {
			nestedModel.setObject(null);
		} else {
			final String stripped = StringUtils.removeChars(value, illegalChars).trim();
			if (stripped.length() != value.length()) {
				LOG.info("Stripped characters from string [" + value + "] to produce [" + stripped + "].");
			}
			nestedModel.setObject(stripped);
		}
	}
}
