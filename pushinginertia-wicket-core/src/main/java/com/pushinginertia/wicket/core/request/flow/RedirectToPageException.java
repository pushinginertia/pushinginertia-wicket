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
package com.pushinginertia.wicket.core.request.flow;

import com.pushinginertia.wicket.core.util.ComponentUtils;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.servlet.http.HttpServletResponse;

/**
 * Redirects the user to a given page and its parameters, but with the host name overridden.
 */
public class RedirectToPageException extends RedirectToUrlException {
	private static final long serialVersionUID = 1L;

	/**
	 * Redirects the user to a given page and its parameters, but with the host name overridden.
	 * @param component component the call is being made from (used to obtain the container request)
	 * @param hostName host name to use in the constructed URL
	 * @param targetPage target page
	 * @param pageParameters parameters for the target page (may be null)
	 */
	public RedirectToPageException(
			final Component component,
			final String hostName,
			final Class<? extends Page> targetPage,
			final PageParameters pageParameters) {
		this(component, hostName, targetPage, pageParameters, HttpServletResponse.SC_MOVED_PERMANENTLY);
	}

	/**
	 * Redirects the user to a given page and its parameters, but with the host name overridden.
	 * @param component component the call is being made from (used to obtain the container request)
	 * @param hostName host name to use in the constructed URL
	 * @param targetPage target page
	 * @param pageParameters parameters for the target page (may be null)
	 * @param statusCode HTTP status code (301 or 302)
	 */
	public RedirectToPageException(
			final Component component,
			final String hostName,
			final Class<? extends Page> targetPage,
			final PageParameters pageParameters,
			final int statusCode) {
		super(
			ComponentUtils.constructRedirectUrl(component, hostName, targetPage, pageParameters, null),
			statusCode
		);
	}
}
