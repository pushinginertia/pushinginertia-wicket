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

import org.apache.wicket.Application;
import org.apache.wicket.Component;

/**
 * Convenience methods for localization and resource loading.
 */
public final class ResourceModelUtils {
	private ResourceModelUtils() {}

	/**
	 * Get the localized string using all of the supplied parameters. This method is left public to
	 * allow developers full control over string resource loading. However, it is recommended that
	 * one of the other convenience methods in the class are used as they handle all of the work
	 * related to obtaining the current user locale and style information.
	 *
	 * @param resourceKey
	 *            The key to obtain the resource for
	 * @return The string resource
	 * @throws java.util.MissingResourceException
	 *             If resource not found and configuration dictates that exception should be thrown
	 */
	public static String getString(String resourceKey) {
		return Application.get().getResourceSettings().getLocalizer().getString(resourceKey, (Component)null);
	}
}
