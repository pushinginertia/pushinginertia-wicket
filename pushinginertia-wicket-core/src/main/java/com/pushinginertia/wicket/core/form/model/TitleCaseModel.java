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

import com.pushinginertia.commons.lang.NormalizeStringCaseUtils;
import com.pushinginertia.commons.lang.StringUtils;
import com.pushinginertia.commons.core.validation.ValidateAs;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A model that can be attached to a form input, which will convert the user's input into title case. This wraps another
 * model that represents the string that the form input will be assigned to.
 */
public class TitleCaseModel extends Model<String> {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(TitleCaseModel.class);
	public static final int UPPERCASE_THRESHOLD = 55;
	public static final int LOWERCASE_THRESHOLD = 85;

	private final IModel<String> nestedModel;
	private final int uppercaseThreshold;
	private final int lowercaseThreshold;

	public TitleCaseModel(final IModel<String> model) {
		this(model, LOWERCASE_THRESHOLD, UPPERCASE_THRESHOLD);
	}

	public TitleCaseModel(final IModel<String> model, final int lowercaseThreshold, final int uppercaseThreshold) {
		this.nestedModel = ValidateAs.notNull(model, "model");
		this.lowercaseThreshold = lowercaseThreshold;
		this.uppercaseThreshold = uppercaseThreshold;
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
			final String titleCase =
					NormalizeStringCaseUtils.normalizeCase(
							value,
							NormalizeStringCaseUtils.TargetCase.TITLE,
							NormalizeStringCaseUtils.Scope.PER_WORD,
							uppercaseThreshold,
							lowercaseThreshold);
			if (!value.equals(titleCase)) {
				LOG.info("Converted input [" + value + "] to title case [" + titleCase + "].");
			}
			nestedModel.setObject(titleCase);
		}
	}
}
