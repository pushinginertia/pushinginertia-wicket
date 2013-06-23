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
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * Adds a CSS file to the &lt;head&gt; block of the html document that exists in a static location, such as a CDN.
 */
public class StaticCssAssetBehavior extends Behavior {
	private static final long serialVersionUID = 1L;

	private final String asset;

	public StaticCssAssetBehavior(final String asset) {
		this.asset = ValidateAs.notEmpty(asset, "asset");
	}

	@Override
	public void renderHead(final Component component, final IHeaderResponse response) {
		super.renderHead(component, response);
		response.renderCSSReference(asset);
	}
}