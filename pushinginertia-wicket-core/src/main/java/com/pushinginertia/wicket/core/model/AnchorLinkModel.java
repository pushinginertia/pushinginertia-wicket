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
package com.pushinginertia.wicket.core.model;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.string.Strings;

/**
 * Renders an HTML &lt;a&gt; link for given href and link text.
 */
public class AnchorLinkModel extends AbstractReadOnlyModel<String> {
	private final String href;
	private final String linkText;

	public AnchorLinkModel(final String href, final String linkText) {
		this.href = href;
		this.linkText = linkText;
	}

	@Override
	public String getObject() {
		return "<a href=\"" + Strings.escapeMarkup(href) + "\">" + Strings.escapeMarkup(linkText) + "</a>";
	}

	@Override
	public void detach() {
	}
}
