package com.pushinginertia.wicket.core.model.replacement;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 *
 */
public class EmailContentReplacerPatternProvider implements EmailContentReplacer.IEmailContentReplacerPatternProvider {
	private static final long serialVersionUID = 1L;

	private static final String INNER_DOT = "\\s?\\.\\s?| +dot +";
	/**
	 * Various ways a 'dot' might be typed in the domain of an email.
	 */
	private static final String DOT = "(" + INNER_DOT + ")";
	private static final String COMMON_TLDS =
			"((" + INNER_DOT + "| )?(com|net|[a-z][a-z]|co" + DOT + "[a-z][a-z]|com" + DOT + "[a-z][a-z]))?";
	/**
	 * The part of an email preceding the '@' sign.
	 */
	private static final String LOCAL_PART = "\\b([a-z0-9._%+-]+|([a-z0-9._%+-] )+)";
	/**
	 * Repeating subdomains.
	 */
	private static final String SUBDOMAIN = "(([a-z0-9-]+|([a-z0-9-] )+)" + DOT + ")";
	/**
	 * Variations of an '@' sign.
	 */
	private static final String AT = "(\\s*[@\uff20\u0040\ufe6b]+\\s*| *at +| *[\\[{(]at[\\]})] *)";
	/**
	 * This matches real email addresses and also some common ways users change email addresses so that they're still
	 * human readable but not parseable by bots.
	 */
	private static final String EMAIL_REGEX =
			LOCAL_PART + AT +
			SUBDOMAIN + '+' +                   // repeating subdomains
			"([a-z]{1,5}|([a-z] ){1,5})[a-z]";  // generic top level domain

	private static final EmailContentReplacerPatternProvider DEFAULT_REGEX =
			new EmailContentReplacerPatternProvider(EMAIL_REGEX);

	private final String pattern;

	private EmailContentReplacerPatternProvider(@Nonnull final String pattern) {
		this.pattern = pattern;
	}

	public static EmailContentReplacerPatternProvider forDefaultRegex() {
		return DEFAULT_REGEX;
	}

	public EmailContentReplacerPatternProvider(@Nonnull final Collection<String> emailDomains) {
		this.pattern = '(' + EMAIL_REGEX + '|' + LOCAL_PART + AT + constructEmailDomainRegex(emailDomains) + ')';
	}

	/**
	 * Contructs a regex identifying emails from a list of common email domains. This is a bit more specific so that
	 * something like "user@g mail.com" can be detected, which would produce false positives if a generic regex was
	 * used matching anything with a whitespace and dot in the domain part.
	 * @param emailDomains A collection of unique strings of common domains used for email with the top-level domain
	 *                     (such as ".com") omitted.
	 */
	@Nonnull
	static String constructEmailDomainRegex(@Nonnull final Collection<String> emailDomains) {
		final String baseDomain = emailDomains.stream()
				.map(String::toLowerCase)
				.map(EmailContentReplacerPatternProvider::insertRegexWhitespace)
				.collect(Collectors.joining("|", "(", ")"));
		return SUBDOMAIN + "*" + baseDomain + COMMON_TLDS;
	}

	private static String insertRegexWhitespace(@Nonnull final String s) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			if (sb.length() > 0) {
				sb.append("\\s*");
			}
			sb.append(s.charAt(i));
		}
		return sb.toString();
	}

	@Override
	public String pattern() {
		return pattern;
	}
}
