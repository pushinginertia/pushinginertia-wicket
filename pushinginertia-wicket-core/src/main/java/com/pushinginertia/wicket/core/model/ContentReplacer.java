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
package com.pushinginertia.wicket.core.model;

import java.io.Serializable;

/**
 * Defines the regular expression to match against a string and its replacement value for all matches in the string.
 */
public interface ContentReplacer extends Serializable {
	/**
	 * Pattern to search for (case insensitive).
	 *
	 * @return regex
	 */
	public String pattern();

	/**
	 * Replacement string to apply for matches of {@link #pattern()}. This could be a link to another page or just
	 * some replacement text.
	 *
	 * @return non-null string
	 */
	public String replacement();
}