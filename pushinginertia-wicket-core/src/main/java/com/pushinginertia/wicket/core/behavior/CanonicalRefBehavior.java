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

import com.pushinginertia.commons.core.validation.ValidateAs;
import com.pushinginertia.wicket.core.RequestCycleUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Generates a rel="canonical" link to either the current page (without any arguments) or a given domain and page on that
 * domain. This is useful for pages with similar content, such as paginated search results.
 * <p>
 * Sample output:
 * <br/>
 * &lt;link rel="canonical" href="http://www.example.com/pagepath"/&gt;
 */
public class CanonicalRefBehavior extends Behavior {
	private static final long serialVersionUID = 1L;

	private final String domain;
	private final String path;

	/**
	 * Generates a canonical link to a page on a given domain.
	 * <p>
	 * e.g.,
	 * &lt;link rel="canonical" href="http://www.example.com/pagepath"&gt;
	 * @param domain domain portion of the URL
	 * @param path path portion of the URL
	 */
	public CanonicalRefBehavior(final String domain, final String path) {
		this.domain = ValidateAs.notNull(domain, "domain");
		this.path = ValidateAs.notNull(path, "path");
	}

	/**
	 * Builds a canonical link to the current page but without any page parameters.
	 * Used for pages containing the same general content regardless of what parameters they are given and prevents
	 * search engines from indexing the same page over and over with only variations in page parameters.
	 */
	public CanonicalRefBehavior() {
		domain = RequestCycleUtils.getRequestHostName();
		path = RequestCycle.get().getUrlRenderer().getBaseUrl().getPath();
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		final StringBuilder sb = new StringBuilder();
		sb.append("<link rel=\"canonical\" href=\"http://");
		sb.append(domain);
		sb.append('/');
		sb.append(path);
		sb.append("\"/>");
		response.renderString(sb.toString());
	}
}
