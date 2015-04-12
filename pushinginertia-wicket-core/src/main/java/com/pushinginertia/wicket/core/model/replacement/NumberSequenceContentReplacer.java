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

import javax.annotation.Nonnull;

/**
 * Replaces a sequence of numbers in a block of text content (presumably identifying a phone number or some other
 * identification number) with a link to a given page with some given text.
 */
public class NumberSequenceContentReplacer implements ContentReplacer {
	private static final long serialVersionUID = 1L;

	/**
	 * Matches a sequence of numbers that might also be written as words with various punctuation in between.
	 */
	private static final String BASE_REGEX_PRE =
			"\\(?([0-9]|zero|one|two|three|four|five|six|seven|eight|nine)([\\(\\)\\., -]*([0-9]|zero|one|two|three|four|five|six|seven|eight|nine)){";
	private static final String BASE_REGEX_POST = ",}";

	private final String linkRef;
	private final String linkName;
	private final String pattern;

	public NumberSequenceContentReplacer(final int minNumbers, final String linkRef, final String linkName) {
		this.linkRef = linkRef;
		this.linkName = linkName;
		this.pattern = BASE_REGEX_PRE + (minNumbers - 1) + BASE_REGEX_POST;
	}

	@Nonnull
	@Override
	public String pattern() {
		return pattern;
	}

	@Nonnull
	@Override
	public String replacement() {
		return "<a href=\"" + linkRef + "\">" + linkName + "</a>";
	}
}
