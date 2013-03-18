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

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;
import org.junit.Assert;
import org.junit.Test;

public class ObfuscatedMailtoJavaScriptBehaviorTest {
	@Test
	public void renderHead() {
		final ObfuscatedMailtoJavaScriptBehavior behavior =
				new ObfuscatedMailtoJavaScriptBehavior(
						"id",
						"We'd love it if you ${emailLink}!",
						"support@example.com",
						"email us");

		// TODO: replace with easymock
		final HeaderResponseTest response = new HeaderResponseTest();
		behavior.renderHead(null, response);
		Assert.assertTrue(response.isCalled());
	}

	private static class HeaderResponseTest implements IHeaderResponse {
		private boolean called = false;

		public boolean isCalled() {
			return called;
		}

		@Override
		public void renderJavaScript(CharSequence javascript, String id) {
			Assert.assertEquals(
					"$(window).load(function(){$('#id').html('We'd love it if you <a href=\"'+'&#109'+'&#97'+'&#105'+'&#108'+'&#116'+'&#111'+'&#58'+'&#115&#117&#112&#112&#111&#114&#116&#64&#101&#120&#97&#109&#112&#108&#101&#46&#99&#111&#109\">&#101&#109&#97&#105&#108&#32&#117&#115</a>!');});",
					javascript);
			called = true;
		}

		@Override
		public void renderJavaScriptReference(ResourceReference reference) {
			Assert.fail();
		}

		@Override
		public void renderJavaScriptReference(ResourceReference reference, String id) {
			Assert.fail();
		}

		@Override
		public void renderJavaScriptReference(ResourceReference reference, PageParameters pageParameters, String id) {
			Assert.fail();
		}

		@Override
		public void renderJavaScriptReference(ResourceReference reference, PageParameters pageParameters, String id, boolean defer) {
			Assert.fail();
		}

		@Override
		public void renderJavaScriptReference(ResourceReference reference, PageParameters pageParameters, String id, boolean defer, String charset) {
			Assert.fail();
		}

		@Override
		public void renderJavaScriptReference(String url) {
			Assert.fail();
		}

		@Override
		public void renderJavaScriptReference(String url, String id) {
			Assert.fail();
		}

		@Override
		public void renderJavaScriptReference(String url, String id, boolean defer) {
			Assert.fail();
		}

		@Override
		public void renderJavaScriptReference(String url, String id, boolean defer, String charset) {
			Assert.fail();
		}

		@Override
		public void renderCSS(CharSequence css, String id) {
			Assert.fail();
		}

		@Override
		public void renderCSSReference(ResourceReference reference) {
			Assert.fail();
		}

		@Override
		public void renderCSSReference(String url) {
			Assert.fail();
		}

		@Override
		public void renderCSSReference(ResourceReference reference, String media) {
			Assert.fail();
		}

		@Override
		public void renderCSSReference(ResourceReference reference, PageParameters pageParameters, String media) {
			Assert.fail();
		}

		@Override
		public void renderCSSReference(ResourceReference reference, PageParameters pageParameters, String media, String condition) {
			Assert.fail();
		}

		@Override
		public void renderCSSReference(String url, String media) {
			Assert.fail();
		}

		@Override
		public void renderCSSReference(String url, String media, String condition) {
			Assert.fail();
		}

		@Override
		public void renderString(CharSequence string) {
			Assert.fail();
		}

		@Override
		public void markRendered(Object object) {
			Assert.fail();
		}

		@Override
		public boolean wasRendered(Object object) {
			Assert.fail();
			return false;
		}

		@Override
		public Response getResponse() {
			Assert.fail();
			return null;
		}

		@Override
		public void renderOnDomReadyJavaScript(String javascript) {
			Assert.fail();
		}

		@Override
		public void renderOnLoadJavaScript(String javascript) {
			Assert.fail();
		}

		@Override
		public void renderOnEventJavaScript(String target, String event, String javascript) {
			Assert.fail();
		}

		@Override
		public void close() {
			Assert.fail();
		}

		@Override
		public boolean isClosed() {
			Assert.fail();
			return false;
		}
	}
}
