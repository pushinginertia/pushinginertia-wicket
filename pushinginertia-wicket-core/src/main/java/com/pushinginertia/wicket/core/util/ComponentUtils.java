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
import org.apache.wicket.request.Request;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstracts some common logic used in various wicket components.
 */
public final class ComponentUtils {
	private static final Logger LOG = LoggerFactory.getLogger(ComponentUtils.class);

	private static final Map<String, Integer> PROTO_TO_PORT = new HashMap<String, Integer>();
	static {
		PROTO_TO_PORT.put("http", 80);
		PROTO_TO_PORT.put("https", 443);
	}

	private ComponentUtils() {}

	/**
	 * Constructs a URL by using the same port and scheme/protocol as what was used to request the current page.
	 * @param component component the call is being made from (used to obtain the container request)
	 * @param absolutePath absolute path to append after the host name (can be null), a leading '/' will be added if
	 * omitted
	 * @return
	 */
	public static String constructUrl(final Component component, final String absolutePath) {
		ValidateAs.notNull(component, "component");
		return constructUrl(component.getRequest(), absolutePath);
	}

	/**
	 * Constructs a URL by using the same port and scheme/protocol as what was used to request the current page.
	 * @param request object encapsulating the request to the server (used to obtain the container request)
	 * @param absolutePath absolute path to append after the host name (can be null), a leading '/' will be added if
	 * omitted
	 * @return
	 */
	public static String constructUrl(final Request request, final String absolutePath) {
		final String hostName = request.getUrl().getHost();
		return constructUrl(request, hostName, absolutePath);
	}

	/**
	 * Constructs a URL by using the same port and scheme/protocol as what was used to request the current page.
	 * @param component component the call is being made from (used to obtain the container request)
	 * @param hostName host name to use in the constructed URL
	 * @param absolutePath absolute path to append after the host name (can be null), a leading '/' will be added if
	 * omitted
	 * @return
	 */
	public static String constructUrl(
		final Component component,
		final String hostName,
		final String absolutePath
	) {
		ValidateAs.notNull(component, "component");
		return constructUrl(component.getRequest(), hostName, absolutePath);
	}

	/**
	 * Constructs a URL by using the same port and scheme/protocol as what was used to request the current page.
	 * @param request object encapsulating the request to the server (used to obtain the container request)
	 * @param hostName host name to use in the constructed URL
	 * @param absolutePath absolute path to append after the host name (can be null), a leading '/' will be added if
	 * omitted
	 * @return
	 */
	public static String constructUrl(
		final Request request,
		final String hostName,
		final String absolutePath
	) {
		ValidateAs.notNull(request, "request");
		ValidateAs.notEmpty(hostName, "hostName");

		final HttpServletRequest req = (HttpServletRequest)request.getContainerRequest();
		final String scheme = req.getScheme();
		final int port = req.getServerPort();

		final StringBuilder sb = new StringBuilder(scheme);
		sb.append("://");
		sb.append(hostName);
		if (port > 0 && !PROTO_TO_PORT.get(scheme).equals(port)) {
			sb.append(':').append(port);
		}
		if (absolutePath != null && absolutePath.length() > 0) {
			if (!absolutePath.startsWith("/")) {
				sb.append('/');
			}
			sb.append(absolutePath);
		}
		return sb.toString();
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
		ValidateAs.notNull(targetPage, "targetPage");

		final HttpServletRequest req = (HttpServletRequest)component.getRequest().getContainerRequest();

		final String relativePath = component.urlFor(targetPage, pageParameters).toString();
		final String requestURI = req.getRequestURI();
		String absolutePath;
		try {
			absolutePath = RequestUtils.toAbsolutePath(requestURI, relativePath);
		} catch (final StringIndexOutOfBoundsException e) {
			// sometimes the wicket method throws an exception when the input is something like:
			// requestPath="/404", relativePagePath="../../../page-name"
			// strip all leading occurrences of "../" from the relativePath
			final StringBuilder sb = new StringBuilder(relativePath);
			while (sb.indexOf("../") == 0) {
				sb.delete(0, 3);
			}
			absolutePath = sb.toString();
		} catch (final RuntimeException e) {
			// this call can throw StringIndexOutOfBoundsException: String index out of range: -1
			// it seems to be a wicket bug but I need to log the input to figure out how to reproduce it
			LOG.error(
					"Error constructing absolute path for inputs requestPath=[" + requestURI +
					"], relativePagePath=[" + relativePath + "]",
					e);
			throw e;
		}

		return constructUrl(component, hostName, absolutePath);
	}

	/**
	 * A facade for {@link org.apache.wicket.Component#findParent(Class)} that fails if no parent component of the
	 * given type exists. This should be called from {@link org.apache.wicket.Component#onInitialize()} and not in the
	 * component's constructor.
	 * @param callingComponent component making the call
	 * @param parentClass type of the parent class
	 * @param <Z> Type of the parent class.
	 * @return Parent component.
	 */
	@Nonnull
	public static <Z> Z findParentOrDie(
			final @Nonnull Component callingComponent,
			final @Nonnull Class<Z> parentClass) {
		ValidateAs.notNull(callingComponent, "callingComponent");
		ValidateAs.notNull(parentClass, "parentClass");

		final Z parentComponent = callingComponent.findParent(parentClass);
		if (parentComponent == null) {
			throw new IllegalStateException(
					"Failed to find parent of type " + parentClass.getName() +
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
