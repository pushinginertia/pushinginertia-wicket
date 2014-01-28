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
package com.pushinginertia.wicket.core.util;

import com.pushinginertia.commons.core.validation.ValidateAs;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.servlet.http.HttpServletRequest;
import java.lang.Class;import java.lang.IllegalStateException;import java.lang.String;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstracts some common logic used in various wicket components.
 */
public final class ComponentUtils {
	private ComponentUtils() {}

	private static final Map<String, Integer> PROTO_TO_PORT = new HashMap<String, Integer>();
	static {
		PROTO_TO_PORT.put("http", 80);
		PROTO_TO_PORT.put("https", 443);
	}

	/**
	 * Constructs the full URL to redirect to for a given target page and its parameters, with the option to override
	 * the host name.
	 * @param component component the call is being made from (used to obtain the container request)
	 * @param hostName host name to use in the constructed URL
	 * @param targetPage target page
	 * @param pageParameters parameters for the target page (may be null)
	 * @return full URL
	 * @see org.apache.wicket.request.UrlRenderer#renderFullUrl(org.apache.wicket.request.Url)
	 */
	public static String constructRedirectUrl(
			final Component component,
			final String hostName,
			final Class<? extends Page> targetPage,
			final PageParameters pageParameters) {
		ValidateAs.notNull(component, "component");
		ValidateAs.notEmpty(hostName, "hostName");
		ValidateAs.notNull(targetPage, "targetPage");

		final HttpServletRequest req = (HttpServletRequest)component.getRequest().getContainerRequest();
		final String scheme = req.getScheme();
		final int port = req.getServerPort();

		final String relativePath = component.urlFor(targetPage, pageParameters).toString();
		final String absolutePath = RequestUtils.toAbsolutePath(req.getRequestURI(), relativePath);

		final StringBuilder sb = new StringBuilder(scheme);
		sb.append("://");
		sb.append(hostName);
		if (port > 0 && !PROTO_TO_PORT.get(scheme).equals(port)) {
			sb.append(':').append(port);
		}
		sb.append(absolutePath);
		return sb.toString();
	}

	/**
	 * A facade for {@link org.apache.wicket.Component#findParent(Class)} that fails if no parent component of the
	 * given type exists. This should be called from {@link org.apache.wicket.Component#onInitialize()} and not in the
	 * component's constructor.
	 * @param callingComponent component making the call
	 * @param parentClass type of the parent class
	 * @param <Z> type of the parent class
	 * @return never null
	 */
	public static <Z> Z findParentOrDie(final Component callingComponent, final Class<Z> parentClass) {
		ValidateAs.notNull(callingComponent, "callingComponent");
		ValidateAs.notNull(parentClass, "parentClass");

		final Z parentComponent = callingComponent.findParent(parentClass);
		if (parentComponent == null) {
			throw new IllegalStateException(
					"Failed to find parent of type " + parentClass.getClass().getName() +
					" from callingComponent. A common cause is that this method is called from a component's constructor instead of its onInitialize() method. Component: " +
					callingComponent);
		}
		return parentComponent;
	}

	/**
	 * Identifies if a component has had the given {@link AttributeModifier} added to it.
	 * @param callingComponent component making the call
	 * @param modifier modifier to search for
	 * @return true if the modifier has been added to the component
	 */
	public static boolean isAttributeModifierAdded(final Component callingComponent, final AttributeModifier modifier) {
		ValidateAs.notNull(callingComponent, "callingComponent");
		ValidateAs.notNull(modifier, "modifier");

		final List<AttributeModifier> behaviorList = callingComponent.getBehaviors(AttributeModifier.class);
		final String attribute = modifier.getAttribute();

		for (final AttributeModifier behavior: behaviorList) {
			if (attribute.equals(behavior.getAttribute())) {
				return modifier.equals(behavior);
			}
		}
		return false;
	}
}
