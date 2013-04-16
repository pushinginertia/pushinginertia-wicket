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
package com.pushinginertia.wicket.core;

import com.pushinginertia.commons.net.util.HttpServletRequestUtils;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Static utility methods to help with the request cycle.
 */
public final class RequestCycleUtils {
	private static final Logger LOG = LoggerFactory.getLogger(RequestCycleUtils.class);

	private RequestCycleUtils() {}

	/**
	 * Obtains the HttpServletRequest from the request cycle.
	 * @return null if the servlet request cannot be retrieved
	 */
	public static HttpServletRequest getHttpServletRequest() {
		final RequestCycle rc = RequestCycle.get();
		if (rc == null)
			return null;
		final Request r = rc.getRequest();
		return (HttpServletRequest)r.getContainerRequest();
	}

	/**
	 * Parses the host name from the request.
	 * @return null if unable to parse
	 */
	public static String getRequestHostName() {
		return HttpServletRequestUtils.getRequestHostName(RequestCycleUtils.getHttpServletRequest());
	}
}
