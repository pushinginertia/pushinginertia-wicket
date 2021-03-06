/* Copyright (c) 2011-2015 Pushing Inertia
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

import com.pushinginertia.commons.core.validation.ValidateAs;
import com.pushinginertia.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class ContentReplacerList implements Serializable {
	private static final long serialVersionUID = 1L;

	private final List<ContentReplacer> replacerList;

	private ContentReplacerList(final List<ContentReplacer> replacerList) {
		this.replacerList = new ArrayList<>(replacerList);
	}

	public static class Builder {
		private final List<ContentReplacer> replacerList = new ArrayList<>();

		public void add(final ContentReplacer replacer) {
			replacerList.add(ValidateAs.notNull(replacer, "replacer"));
		}

		public ContentReplacerList build() {
			return new ContentReplacerList(replacerList);
		}
	}

	/**
	 * Sequentially applies the content replacers contained within this list against an input string.
	 * @param input String to apply replacements to.
	 * @return Replaced string.
	 */
	public String replace(final String input) {
		String s = input;
		for (final ContentReplacer replacer: replacerList) {
			s = StringUtils.replaceAllCaseInsensitive(s, replacer.pattern(), replacer.replacement());
		}
		return s;
	}
}
