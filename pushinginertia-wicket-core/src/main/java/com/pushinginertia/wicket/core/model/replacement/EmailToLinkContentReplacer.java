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
 * Replaces email addresses found in a string with a link to a given page with some given text.
 */
public class EmailToLinkContentReplacer extends EmailContentReplacer {
	private static final long serialVersionUID = 1L;

	private final String linkRef;
	private final String linkName;

	public EmailToLinkContentReplacer(final String linkRef, final String linkName) {
		this.linkRef = linkRef;
		this.linkName = linkName;
	}

	@Nonnull
	@Override
	public String replacement() {
		return "<a href=\"" + linkRef + "\">" + linkName + "</a>";
	}

	@Override
	public String toString() {
		return "EmailToLinkContentReplacer{" + "linkRef=" + linkRef + ", linkName=" + linkName + '}';
	}
}