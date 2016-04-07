package com.pushinginertia.wicket.core.model.replacement;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * Replaces email addresses found in a string with a customizable string.
 */
public abstract class EmailContentReplacer implements ContentReplacer {
	private static final long serialVersionUID = 2L;

	public interface IEmailContentReplacerPatternProvider extends Serializable {
		public String pattern();
	}

	private final IEmailContentReplacerPatternProvider provider;

	public EmailContentReplacer() {
		this.provider = EmailContentReplacerPatternProvider.forDefaultRegex();
	}

	public EmailContentReplacer(@Nonnull final IEmailContentReplacerPatternProvider provider) {
		this.provider = provider;
	}

	@Nonnull
	@Override
	public final String pattern() {
		return provider.pattern();
	}
}
