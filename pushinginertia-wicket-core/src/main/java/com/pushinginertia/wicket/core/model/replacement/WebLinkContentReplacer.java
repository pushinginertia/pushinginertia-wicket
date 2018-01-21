/* Copyright (c) 2011-2018 Pushing Inertia
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
 * Replaces common ways that users might enter links to other sites with static replacement text.
 */
public abstract class WebLinkContentReplacer implements ContentReplacer {
	private static final long serialVersionUID = 1L;

	/**
	 * Matches a path in a URL.
	 */
	private static final String LINK_PATH = "(/[^ \\n]*){0,1}";
	/**
	 * Matches common links users might include in their inputs.
	 */
	private static final String LINK_REGEX =
			"(https?://([a-z0-9-]+\\.)+[a-z]{2,}" + LINK_PATH + "|www\\s*\\.\\s*[a-z0-9-]+\\s*\\.\\s*[a-z]{2,7}" + LINK_PATH + ")";

	@Nonnull
	@Override
	public String pattern() {
		return LINK_REGEX;
	}
}
