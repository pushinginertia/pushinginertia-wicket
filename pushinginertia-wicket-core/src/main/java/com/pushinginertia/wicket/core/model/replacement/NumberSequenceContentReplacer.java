package com.pushinginertia.wicket.core.model.replacement;

import javax.annotation.Nonnull;

/**
 * Replaces a sequence of numbers in a block of text content (presumably identifying a phone number or some other
 * identification number).
 */
public abstract class NumberSequenceContentReplacer implements ContentReplacer {
	private static final long serialVersionUID = 1L;

	/**
	 * Represents the numbers 0-10 in Chinese. Zero has two representations and both are reflected here.
 	 */
	private static final String NUM_ZH =
			"\u96f6\u3007\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d\u5341";
	/**
	 * Matches various ways one might write a number.
	 * See:
	 * http://www.fileformat.info/info/unicode/category/No/list.htm
	 * http://www.fileformat.info/info/unicode/category/Nd/list.htm
	 */
	private static final String NUM =
			"([0-9\u2460-\u249b\u24ea-\u24ff\u2776-\u2793\u0030-\u0039\uff10-\uff19" +
			NUM_ZH +
			"]|[cz]ero|oh|one|two|three|four|five|six|seven|eight|nine)";
	/**
	 * u+ff08 and u+ff09 are fixed width opening and closing parentheses.
	 */
	private static final String SEP = "([\\(\\)\\._*, \u3000\uff08\uff09–-]*";
	/**
	 * Matches a sequence of numbers that might also be written as words with various punctuation in between.
	 */
	private static final String BASE_REGEX_PRE = "([\\(\uff08]\\s*)?" + NUM + SEP + NUM + "){";
	private static final String BASE_REGEX_POST = ",}(\\s*[\uff09\\)])?";

	private final String pattern;

	public NumberSequenceContentReplacer(final int minNumbers) {
		this.pattern = BASE_REGEX_PRE + (minNumbers - 1) + BASE_REGEX_POST;
	}

	@Nonnull
	@Override
	public final String pattern() {
		return pattern;
	}

	@Override
	public String toString() {
		return "NumberSequenceContentReplacer{" + pattern + '}';
	}
}
