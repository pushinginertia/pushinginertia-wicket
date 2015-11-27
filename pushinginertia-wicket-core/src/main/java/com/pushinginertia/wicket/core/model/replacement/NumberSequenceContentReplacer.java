package com.pushinginertia.wicket.core.model.replacement;

import javax.annotation.Nonnull;

/**
 * Replaces a sequence of numbers in a block of text content (presumably identifying a phone number or some other
 * identification number).
 */
public abstract class NumberSequenceContentReplacer implements ContentReplacer {
	private static final long serialVersionUID = 1L;

	/**
	 * Matches a sequence of numbers that might also be written as words with various punctuation in between.
	 */
	private static final String BASE_REGEX_PRE =
			"\\(?([0-9]|zero|one|two|three|four|five|six|seven|eight|nine|[\u2460-\u2469])([\\(\\)\\., -]*([0-9]|zero|one|two|three|four|five|six|seven|eight|nine|[\u2460-\u2469])){";
	private static final String BASE_REGEX_POST = ",}";

	private final String pattern;

	protected NumberSequenceContentReplacer(final int minNumbers) {
		this.pattern = BASE_REGEX_PRE + (minNumbers - 1) + BASE_REGEX_POST;
	}

	@Nonnull
	@Override
	public final String pattern() {
		return pattern;
	}
}
