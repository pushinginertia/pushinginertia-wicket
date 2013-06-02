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
package com.pushinginertia.wicket.core.model.replacement;

import com.pushinginertia.commons.lang.StringUtils;
import com.pushinginertia.commons.core.validation.ValidateAs;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

/**
 * Mutates the text stored within a model by replacing it with something else. This is useful for instances such as
 * presentation of user-entered text where certain character sequences should be blocked or replaced with something else.
 * An example might be masking an email address or adding syntax highlighting to certain keywords.
 */
public class ContentReplacementModel extends Model<String> {
	private static final long serialVersionUID = 1L;

	private final IModel<String> nestedModel;
	private ContentReplacerList replacerList;
	private boolean escapeModelString = false;

	/**
	 * Instantiates the content replacement model with a nested model and list of replacer instances.
	 * @param nestedModel model containing a string of text
	 * @param replacerList instances that perform text replacements
	 */
	public ContentReplacementModel(final IModel<String> nestedModel, final ContentReplacerList replacerList) {
		this.nestedModel = nestedModel;
		this.replacerList = ValidateAs.notNull(replacerList, "replacerList");
	}

	/**
	 * Indicates if the string contained within the nested model will be escaped when {@link #getObject()} is called.
	 * @return true if the string will be escaped, else false
	 * @see #setEscapeModelString(boolean)
	 */
	public final boolean isEscapeModelString() {
		return escapeModelString;
	}

	/**
	 * If true, the string contained in the nested model will be escaped by calling {@link Strings#escapeMarkup(CharSequence)}
	 * before any replacements are performed. This means that the component that this model is a member of should have
	 * its {@link org.apache.wicket.Component#setEscapeModelStrings(boolean)} method called with a value of false so
	 * that the string isn't escaped twice. You would want to perform an escape here if one of the {@link ContentReplacer}s
	 * adds HTML tags that should not be escaped to "&amp;lt;" when the component renders, but it's still necessary to
	 * escape the underlying string to guard against malicious input by a user.
	 * @param escapeModelString true if the string should be escaped before replacements are made
	 */
	public final void setEscapeModelString(final boolean escapeModelString) {
		this.escapeModelString = escapeModelString;
	}

	@Override
	public final String getObject() {
		String s = nestedModel.getObject();
		if (s == null) {
			return null;
		}

		// escape the model
		if (escapeModelString) {
			s = Strings.escapeMarkup(s, false, false).toString();
		}

		// perform replacements
		for (final ContentReplacer replacer: replacerList.get()) {
			s = StringUtils.replaceAllCaseInsensitive(s, replacer.pattern(), replacer.replacement());
		}
		return s;
	}

	@Override
	public final void setObject(final String value) {
		nestedModel.setObject(value);
	}
}
