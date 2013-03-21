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
package com.pushinginertia.wicket.core.form.component;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;import java.lang.Class;import java.lang.Override;import java.lang.String;

/**
 * A cancel button that directs a user to another class when pressed. If {@link #responsePage} is null, the button's
 * visibility is disabled.
 */
public class CancelButton<C extends IRequestablePage> extends Button {
	private static final long serialVersionUID = 1L;

	private final Class<C> responsePage;
	private final PageParameters pageParameters;

	public CancelButton(final String id, final Class<C> responsePage) {
		this(id, responsePage, null, (IModel<String>)null);
	}

	public CancelButton(final String id, final Class<C> responsePage, final PageParameters pageParameters) {
		this(id, responsePage, pageParameters, (IModel<String>)null);
	}

	public CancelButton(final String id, final Class<C> responsePage, final IModel<String> model) {
		this(id, responsePage, null, model);
	}

	public CancelButton(final String id, final Class<C> responsePage, final PageParameters pageParameters, final IModel<String> model) {
		super(id, model);
		this.responsePage = responsePage;
		this.pageParameters = pageParameters;
		setDefaultFormProcessing(false);
	}

	public PageParameters getPageParameters() {
		return pageParameters;
	}

	@Override
	public boolean isVisible() {
		return responsePage != null;
	}

	@Override
	public void onSubmit() {
		if (responsePage != null) {
			setResponsePage(responsePage, pageParameters);
		}
	}
}