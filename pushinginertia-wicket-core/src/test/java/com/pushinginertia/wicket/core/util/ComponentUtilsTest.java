package com.pushinginertia.wicket.core.util;

import org.apache.wicket.request.Request;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

public class ComponentUtilsTest {
	@Test
	public void constructUrl() {
		innerConstructUrl("http://hostname", "http", "hostname", 80, null);
		innerConstructUrl("http://hostname", "http", "hostname", 80, "");
		innerConstructUrl("http://hostname/abc", "http", "hostname", 80, "abc");
		innerConstructUrl("http://hostname/abc", "http", "hostname", 80, "/abc");
		innerConstructUrl("http://hostname:8080", "http", "hostname", 8080, null);
		innerConstructUrl("http://hostname:8080", "http", "hostname", 8080, "");
		innerConstructUrl("http://hostname:8080/abc", "http", "hostname", 8080, "abc");
		innerConstructUrl("http://hostname:8080/abc", "http", "hostname", 8080, "/abc");
		innerConstructUrl("https://hostname/abc", "https", "hostname", 443, "abc");
		innerConstructUrl("https://hostname/abc", "https", "hostname", 443, "/abc");
		innerConstructUrl("https://hostname:8443/abc", "https", "hostname", 8443, "abc");
		innerConstructUrl("https://hostname:8443/abc", "https", "hostname", 8443, "/abc");
	}

	private void innerConstructUrl(final String expectedUrl, final String scheme, final String hostName, final int port, final String path) {
		final Request request = EasyMock.createMock(Request.class);
		final HttpServletRequest hsr = EasyMock.createMock(HttpServletRequest.class);
		EasyMock.expect(request.getContainerRequest()).andReturn(hsr);
		EasyMock.expect(hsr.getScheme()).andReturn(scheme);
		EasyMock.expect(hsr.getServerPort()).andReturn(port);
		EasyMock.replay(request, hsr);
		Assert.assertEquals(expectedUrl, ComponentUtils.constructUrl(request, hostName, path));
	}
}
