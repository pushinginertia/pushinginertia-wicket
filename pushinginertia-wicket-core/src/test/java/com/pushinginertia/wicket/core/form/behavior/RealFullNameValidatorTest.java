package com.pushinginertia.wicket.core.form.behavior;

import junit.framework.TestCase;

public class RealFullNameValidatorTest extends TestCase {
	public void testSatisfiesLengthWithoutDot() {
		assertFalse(RealFullNameValidator.satisfiesLengthWithoutDot("d"));
		assertFalse(RealFullNameValidator.satisfiesLengthWithoutDot("d."));
		assertTrue(RealFullNameValidator.satisfiesLengthWithoutDot("dx"));
		assertTrue(RealFullNameValidator.satisfiesLengthWithoutDot("dxa"));
	}
}
