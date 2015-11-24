package com.pushinginertia.wicket.core.model.replacement;

import javax.annotation.Nonnull;

/**
 * Replaces email addresses found in a string with a customizable string.
 */
public abstract class EmailContentReplacer implements ContentReplacer {
	private static final long serialVersionUID = 1L;

	/**
	 * This matches real email addresses and also some common ways users change email addresses so that they're still
	 * human readable but not parseable by bots.
	 */
	public static final String EMAIL_REGEX =
			"\\b([a-z0-9._%+-]+|([a-z0-9._%+-] )+)(\\s*@+\\s*| +at +)(([a-z0-9-]+|([a-z0-9-] )+)(\\s?\\.\\s?| +dot +))+([a-z]{1,3}|([a-z] ){1,3})[a-z]";

	public EmailContentReplacer() {
	}

	@Nonnull
	@Override
	public final String pattern() {
		return EMAIL_REGEX;
	}
}
