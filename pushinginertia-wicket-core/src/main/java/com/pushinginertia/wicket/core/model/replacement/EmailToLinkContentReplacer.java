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

/**
 * Replaces email addresses found in a string with a link to a given page with some given text.
 */
public class EmailToLinkContentReplacer implements ContentReplacer {
	/**
	 * This matches real email addresses and also some common ways users change email addresses so that they're still
	 * human readable but not parseable by bots.
	 */
	public static final String EMAIL_REGEX = "[a-z0-9._%+-]+\\s?@\\s?([a-z0-9-]+\\s?\\.\\s?)+[a-z]{2,4}";

	private final String linkRef;
	private final String linkName;

	public EmailToLinkContentReplacer(final String linkRef, final String linkName) {
		this.linkRef = linkRef;
		this.linkName = linkName;
	}

	@Override
	public String pattern() {
		return EMAIL_REGEX;
	}

	@Override
	public String replacement() {
		return "<a href=\"" + linkRef + "\">" + linkName + "</a>";
	}
}