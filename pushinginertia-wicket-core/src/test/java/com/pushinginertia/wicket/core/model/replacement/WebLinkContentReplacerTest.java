/* Copyright (c) 2011-2018 Pushing Inertia
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
package com.pushinginertia.wicket.core.model.replacement;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebLinkContentReplacerTest {
	private static class WebLinkContentReplacerMock extends WebLinkContentReplacer {
		@Nonnull
		@Override
		public String replacement() {
			throw new UnsupportedOperationException();
		}
	}

	private final WebLinkContentReplacer replacer = new WebLinkContentReplacerMock();
	private Pattern replacerPattern =
			Pattern.compile(replacer.pattern(), Pattern.CASE_INSENSITIVE);

	private void assertStringMatchesWebLinkPattern(final String s) {
		// ContentReplacerList performs a case insensitive pattern match
		final Matcher m = replacerPattern.matcher(s);
		Assert.assertTrue(
				MessageFormat.format(
						"String [{0}] does not match pattern: {1}",
						s,
						replacer.pattern()),
				m.matches());
	}

	@Test
	public void regex() {
		assertStringMatchesWebLinkPattern("www. example. com");
		assertStringMatchesWebLinkPattern("www. youtube. com/watch?v=1234abcDEF&feature=youtu.be");
		assertStringMatchesWebLinkPattern("https://www.example.com/page/4682734");
		assertStringMatchesWebLinkPattern("https://www.abcdefgh/path/to/page");
	}
}