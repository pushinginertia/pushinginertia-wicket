package com.pushinginertia.wicket.core.model.replacement;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nonnull;

public class NumberSequenceContentReplacerTest {
	@Test
	public void regex() {
		final NumberSequenceContentReplacer replacer = new NumberSequenceContentReplacer(8) {
			@Nonnull
			@Override
			public String replacement() {
				return "";
			}
		};
		Assert.assertTrue("( 123 ) 456.7890".matches(replacer.pattern()));

		// words and numbers combined
		Assert.assertTrue("9zero9one2three4five6seven".matches(replacer.pattern()));
		Assert.assertTrue("9zero9 one2three4five6seven".matches(replacer.pattern()));
		Assert.assertTrue("0. 1. 2. 3 45 6 7. 8 9".matches(replacer.pattern()));

		// test some unicode numbers and spaces
		Assert.assertTrue("\u4e03\u516b\u4e5d\u5341---\u4e09\u56db\u4e94\u516d".matches(replacer.pattern()));
		Assert.assertTrue(
				"\uff11\uff12\uff13\u3000\uff14\uff15\uff16\u3000\uff19\uff19\uff19\uff19".matches(replacer.pattern()));
	}
}