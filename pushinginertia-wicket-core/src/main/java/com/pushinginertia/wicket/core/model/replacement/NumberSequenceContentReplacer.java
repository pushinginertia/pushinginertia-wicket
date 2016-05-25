package com.pushinginertia.wicket.core.model.replacement;

import javax.annotation.Nonnull;

/**
 * Replaces a sequence of numbers in a block of text content (presumably identifying a phone number or some other
 * identification number).
 */
public abstract class NumberSequenceContentReplacer implements ContentReplacer {
	private static final long serialVersionUID = 1L;

	/**
	 * Matches various ways one might write a number.
	 * See:
	 * http://www.fileformat.info/info/unicode/category/No/list.htm
	 * http://www.fileformat.info/info/unicode/category/Nd/list.htm
	 */
	public static final String NUM =
			"([0-9\u2460-\u249b\u24ea-\u24ff\u2776-\u2793\u0030-\u0039\uff10-\uff19]|zero|one|two|three|four|five|six|seven|eight|nine)";
	public static final String SEP = "([\\(\\)\\., \u3000-]*";
	/**
	 * Matches a sequence of numbers that might also be written as words with various punctuation in between.
	 */
	private static final String BASE_REGEX_PRE = "(\\(\\s*)?" + NUM + SEP + NUM + "){";
	private static final String BASE_REGEX_POST = ",}";

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
