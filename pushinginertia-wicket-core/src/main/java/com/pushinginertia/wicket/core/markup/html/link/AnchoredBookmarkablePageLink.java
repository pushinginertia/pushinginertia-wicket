/* Copyright (c) 2011-2014 Pushing Inertia
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
package com.pushinginertia.wicket.core.markup.html.link;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * A bookmarkable URL that specifies a custom anchor link. Based off a wicket mail list posting by Thijs Vonk.
 * @see <a href="http://apache-wicket.1842946.n4.nabble.com/Anchors-in-Wicket-td1866779.html">http://apache-wicket.1842946.n4.nabble.com/Anchors-in-Wicket-td1866779.html</a>
 */
public class AnchoredBookmarkablePageLink extends BookmarkablePageLink {
	private static final long serialVersionUID = 1L;

	private final IModel<String> pageAnchor;

	public AnchoredBookmarkablePageLink(final String id, final Class pageClass, final IModel<String> anchor) {
		super(id, pageClass);
		this.pageAnchor = anchor;
	}

	public AnchoredBookmarkablePageLink(final String id, final Class pageClass, final String anchor) {
		this(id, pageClass, new Model<String>(anchor));
	}

	public AnchoredBookmarkablePageLink(
			final String id,
			final Class pageClass,
			final PageParameters params,
			final IModel<String> anchor) {
		super(id, pageClass, params);
		this.pageAnchor = anchor;
	}

	public AnchoredBookmarkablePageLink(
			final String id,
			final Class pageClass,
			final PageParameters params,
			final String anchor) {
		this(id, pageClass, params, new Model<String>(anchor));
	}

	public IModel<String> getPageAnchor() {
		return pageAnchor;
	}

	@Override
	protected CharSequence appendAnchor(final ComponentTag tag, final CharSequence url) {
		return url + "#" + pageAnchor.getObject();
	}
}