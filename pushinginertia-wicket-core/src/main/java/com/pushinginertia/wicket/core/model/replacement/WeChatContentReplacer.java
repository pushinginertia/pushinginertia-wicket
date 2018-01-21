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
 * Replaces WeChat ID references in blocks of text.
 */
public abstract class WeChatContentReplacer implements ContentReplacer {
	private static final long serialVersionUID = 1L;

	private static final String PATTERN =
			"(微 ?信号?|wechat)(\\s*id)?\\s*[:：]?\\s*[a-zA-Z0-9_\\.]+([(（].*[)）])?";

	@Nonnull
	@Override
	public String pattern() {
		return PATTERN;
	}
}
