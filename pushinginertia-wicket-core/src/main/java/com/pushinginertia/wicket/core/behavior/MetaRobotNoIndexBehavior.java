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
package com.pushinginertia.wicket.core.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * Generates a "noindex" meta tag in the HEAD section of the page. This prevents search engines from including the page
 * in search results but other pages linked to from the page will still be traversed.
 * e.g.,
 * &lt;meta name="robots" content="noindex,follow"/&gt;
 */
public class MetaRobotNoIndexBehavior extends Behavior {
	private static final long serialVersionUID = 1L;

	/**
	 * Generates a "noindex" meta tag in the HEAD section of the page. This prevents search engines from including the page
	 * in search results but other pages linked to from the page will still be traversed.
	 * e.g.,
	 * &lt;meta name="robots" content="noindex,follow"/&gt;
	 */
	public MetaRobotNoIndexBehavior() {
	}

	@Override
	public void renderHead(final Component component, final IHeaderResponse response) {
		response.renderString("<meta name=\"robots\" content=\"noindex,follow\"/>");
	}
}
