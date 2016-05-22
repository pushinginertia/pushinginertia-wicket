package com.pushinginertia.wicket.core.model.replacement;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nonnull;

public class NumberSequenceContentReplacerTest {
	@Test
	public void regex() {
		final NumberSequenceContentReplacer replacer = new NumberSequenceContentReplacer(10) {
			@Nonnull
			@Override
			public String replacement() {
				return "";
			}
		};
		Assert.assertTrue("( 123 ) 456.7890".matches(replacer.pattern()));
		// test some unicode numbers and spaces
		Assert.assertTrue(
				"\uff11\uff12\uff13\u3000\uff14\uff15\uff16\u3000\uff19\uff19\uff19\uff19".matches(replacer.pattern()));
	}
}