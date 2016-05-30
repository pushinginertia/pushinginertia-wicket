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
package com.pushinginertia.wicket.core.form.behavior;

import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Test;

public class RealFullNameValidatorTest {
	@Test
	public void containsIllegalValue() {
		Assert.assertTrue(RealFullNameValidator.containsIllegalValue(ImmutableSet.of("bad phrase"), "Bad Phrase"));
		Assert.assertTrue(RealFullNameValidator.containsIllegalValue(ImmutableSet.of("bad phrase"), "bad phrase"));
		Assert.assertFalse(RealFullNameValidator.containsIllegalValue(ImmutableSet.of("bad phrase"), "Bad Phrase abc"));
	}

	@Test
	public void satisfiesLengthWithoutDot() {
		Assert.assertFalse(RealFullNameValidator.satisfiesLengthWithoutDot("d"));
		Assert.assertFalse(RealFullNameValidator.satisfiesLengthWithoutDot("d."));
		Assert.assertTrue(RealFullNameValidator.satisfiesLengthWithoutDot("dx"));
		Assert.assertTrue(RealFullNameValidator.satisfiesLengthWithoutDot("dxa"));
		Assert.assertTrue(RealFullNameValidator.satisfiesLengthWithoutDot("전"));
		Assert.assertTrue(RealFullNameValidator.satisfiesLengthWithoutDot("清"));
		Assert.assertTrue(RealFullNameValidator.satisfiesLengthWithoutDot("成松"));
		Assert.assertTrue(RealFullNameValidator.satisfiesLengthWithoutDot("Éva"));
	}

	@Test
	public void containsTitle() {
		Assert.assertTrue(RealFullNameValidator.containsTitle("Mr.x"));
		Assert.assertTrue(RealFullNameValidator.containsTitle("Mr."));
		Assert.assertTrue(RealFullNameValidator.containsTitle("Mr"));
		Assert.assertTrue(RealFullNameValidator.containsTitle("Mrs."));
		Assert.assertTrue(RealFullNameValidator.containsTitle("Mrs"));
		Assert.assertTrue(RealFullNameValidator.containsTitle("Mrs A"));
		Assert.assertFalse(RealFullNameValidator.containsTitle("Bob"));
	}

	@Test
	public void allVowelsOrConsonants() {
		Assert.assertFalse(RealFullNameValidator.allVowelsOrConsonants("Ly"));
		Assert.assertFalse(RealFullNameValidator.allVowelsOrConsonants("Ay"));
		Assert.assertTrue(RealFullNameValidator.allVowelsOrConsonants("HH"));
		Assert.assertFalse(RealFullNameValidator.allVowelsOrConsonants("abc"));
		Assert.assertTrue(RealFullNameValidator.allVowelsOrConsonants("ae"));
		Assert.assertTrue(RealFullNameValidator.allVowelsOrConsonants("aáeéiíoóöőuúüű"));
		Assert.assertFalse(RealFullNameValidator.allVowelsOrConsonants("Éva"));
	}

	@Test
	public void isDomain() {
		Assert.assertTrue(RealFullNameValidator.isDomain("example.com"));
		Assert.assertTrue(RealFullNameValidator.isDomain("example.co.uk"));
		Assert.assertTrue(RealFullNameValidator.isDomain("example.website"));
		Assert.assertFalse(RealFullNameValidator.isDomain("John"));
		Assert.assertFalse(RealFullNameValidator.isDomain("Doe"));
		Assert.assertFalse(RealFullNameValidator.isDomain("John Jr."));
	}

	@Test
	public void exceedsOneCharLimits() {
		Assert.assertFalse(RealFullNameValidator.exceedsOneCharLimits("John"));
		Assert.assertFalse(RealFullNameValidator.exceedsOneCharLimits("John & Jane"));
		Assert.assertFalse(RealFullNameValidator.exceedsOneCharLimits("John / Jane"));
		Assert.assertFalse(RealFullNameValidator.exceedsOneCharLimits("John & Jane / Jill"));
		Assert.assertTrue(RealFullNameValidator.exceedsOneCharLimits("John & Jane & Jill"));
	}

	@Test
	public void firstAndLastAreLetters() {
		Assert.assertFalse(RealFullNameValidator.firstAndLastAreLetters("C-"));
		Assert.assertFalse(RealFullNameValidator.firstAndLastAreLetters("-C"));
		Assert.assertFalse(RealFullNameValidator.firstAndLastAreLetters("C."));
		Assert.assertFalse(RealFullNameValidator.firstAndLastAreLetters(".C"));
		Assert.assertFalse(RealFullNameValidator.firstAndLastAreLetters("-C-"));
		Assert.assertFalse(RealFullNameValidator.firstAndLastAreLetters("-"));
		Assert.assertTrue(RealFullNameValidator.firstAndLastAreLetters("Cabc"));
		Assert.assertTrue(RealFullNameValidator.firstAndLastAreLetters("Ca"));
		Assert.assertTrue(RealFullNameValidator.firstAndLastAreLetters("전"));
		Assert.assertTrue(RealFullNameValidator.firstAndLastAreLetters("清"));
		Assert.assertTrue(RealFullNameValidator.firstAndLastAreLetters("成松"));
		Assert.assertTrue(RealFullNameValidator.firstAndLastAreLetters("Éva"));
	}
}
