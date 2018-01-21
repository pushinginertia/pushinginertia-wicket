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

public class NumberSequenceContentReplacerTest {
	private final NumberSequenceContentReplacer replacer = new NumberSequenceContentReplacer(8) {
		@Nonnull
		@Override
		public String replacement() {
			return "";
		}
	};
	private Pattern replacerPattern =
			Pattern.compile(replacer.pattern(), Pattern.CASE_INSENSITIVE);

	private void assertStringMatchesNumberSequencePattern(final String s) {
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
		assertStringMatchesNumberSequencePattern("( 123 ) 456.7890");
		assertStringMatchesNumberSequencePattern("1-2-3-4-5-6-7-8-9-0");
		assertStringMatchesNumberSequencePattern("123 – 456- 7890");
		assertStringMatchesNumberSequencePattern("9zero9one2three4five6seven");
		assertStringMatchesNumberSequencePattern("9zero9 one2three4five6seven");
		assertStringMatchesNumberSequencePattern("0. 1. 2. 3 45 6 7. 8 9");
		assertStringMatchesNumberSequencePattern("（123）（456）（7890）");
		assertStringMatchesNumberSequencePattern("one two three, zero nine two, four three two oh");
		assertStringMatchesNumberSequencePattern("( 123 ) 456-7890");
		assertStringMatchesNumberSequencePattern("one two _three____four___five___six__ seven__ eight__ nine__ zero");
		assertStringMatchesNumberSequencePattern("oh one two - three four five- six seven eight nine");

		// test some unicode numbers and spaces
		assertStringMatchesNumberSequencePattern("\u4e03\u516b\u4e5d\u5341---\u4e09\u56db\u4e94\u516d");
		assertStringMatchesNumberSequencePattern(
				"\uff11\uff12\uff13\u3000\uff14\uff15\uff16\u3000\uff19\uff19\uff19\uff19");
	}
}