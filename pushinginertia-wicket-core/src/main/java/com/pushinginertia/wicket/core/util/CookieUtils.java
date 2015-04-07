package com.pushinginertia.wicket.core.util;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
import java.lang.IllegalArgumentException;import java.lang.String;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class CookieUtils {
	private CookieUtils() {}

	/**
	 * Retrieves a cookie for a given name.
	 * @param request Client request.
	 * @param name Name of the cookie (case sensitive).
	 * @return Matching cookie or null if no match.
	 */
	@Nullable
	public static Cookie getRequestCookie(@Nonnull final WebRequest request, @Nonnull final String name) {
		final List<Cookie> cookieList = ((WebRequest) RequestCycle.get().getRequest()).getCookies();
		if (cookieList == null) {
			return null;
		}

		for (final Cookie cookie: cookieList) {
			if (name.equals(cookie.getName())) {
				return cookie;
			}
		}
		return null;
	}

	/**
	 * Retrieves a cookie for a given name.
	 * @param name Name of the cookie (case sensitive).
	 * @return Matching cookie or null if no match.
	 */
	@Nullable
	public static Cookie getRequestCookie(@Nonnull final String name) {
		return getRequestCookie((WebRequest)RequestCycle.get().getRequest(), name);
	}

	/**
	 * Creates a new cookie.
	 * @param name Name of the cookie.
	 * @param value Value of the cookie.
	 * @param maxAgeDays Maximum age of the cookie in days.
	 * @param domain Optional domain within which this cookie should be presented.
	 * @return New cookie instance.
	 * @throws IllegalArgumentException	If the cookie name contains illegal characters
	 *					(for example, a comma, space, or semicolon)
	 *					or it is one of the tokens reserved for use
	 *					by the cookie protocol.
	 */
	public static Cookie newCookie(
			@Nonnull final String name,
			@Nonnull final String value,
			final int maxAgeDays,
			@Nullable final String domain) throws IllegalArgumentException {
		final Cookie cookie = new Cookie(name, value);
		if (domain != null && !domain.isEmpty()) {
			if (domain.charAt(0) == '.') {
				cookie.setDomain(domain);
			} else {
				cookie.setDomain('.' + domain);
			}
		}
		cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(maxAgeDays));
		return cookie;
	}
}
