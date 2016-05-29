package com.pushinginertia.wicket.core.model.replacement;

import com.google.common.collect.ImmutableSet;
import com.pushinginertia.commons.lang.ListUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EmailContentReplacerTest {
	private static final Set<String> COMMON_EMAIL_DOMAINS =
			ImmutableSet.of("gmail", "hotmail", "yahoo", "outlook", "live");

	private static class EmailContentReplacerMock extends EmailContentReplacer {
		public EmailContentReplacerMock(@Nonnull final IEmailContentReplacerPatternProvider provider) {
			super(provider);
		}

		@Nonnull
		@Override
		public String replacement() {
			throw new UnsupportedOperationException();
		}
	}

	@Test
	public void regex() {
		final EmailContentReplacerPatternProvider provider =
				new EmailContentReplacerPatternProvider(COMMON_EMAIL_DOMAINS);
		final EmailContentReplacer replacer = new EmailContentReplacerMock(provider);
		Assert.assertTrue("user1234 [at] example.com".matches(replacer.pattern()));
		Assert.assertTrue("user1234 {at} example.com".matches(replacer.pattern()));
		Assert.assertTrue("user1234[at]example.com".matches(replacer.pattern()));
		Assert.assertTrue("user1234{at}example.com".matches(replacer.pattern()));
		Assert.assertTrue("user1234at example.com".matches(replacer.pattern()));
		Assert.assertTrue("user(at)example dot com".matches(replacer.pattern()));
		Assert.assertTrue("a.b @out look . com".matches(replacer.pattern()));
		Assert.assertTrue("a.b (at) out look . com".matches(replacer.pattern()));
		Assert.assertTrue("username at yahoo com".matches(replacer.pattern()));
		Assert.assertTrue("username@gmail".matches(replacer.pattern()));
		Assert.assertTrue("user_1234@yahoocom".matches(replacer.pattern()));

		Assert.assertFalse("transportation. And".matches(replacer.pattern()));
	}

	@Test
	public void constructEmailDomainRegex() {
		final Set<String> commonTlds = ImmutableSet.of("com", "co.uk", "ca", "net");
		final String regex = EmailContentReplacerPatternProvider.constructEmailDomainRegex(COMMON_EMAIL_DOMAINS);

		Assert.assertTrue("yahoocom".matches(regex));
		Assert.assertTrue("gmail com".matches(regex));

		commonTlds.stream()
				.forEach(tld -> COMMON_EMAIL_DOMAINS.stream()
						.forEach(domain -> {
							final String fqdn = domain + '.' + tld;
							Assert.assertTrue(
									"Domain does not match: " + fqdn,
									fqdn.matches(regex));
							final String fqdnWithSubdomain = "subdomain." + fqdn;
							Assert.assertTrue(
									"Domain does not match: " + fqdnWithSubdomain,
									fqdnWithSubdomain.matches(regex));
							final String fqdnWithSpaces = insertRandomSpaces(domain) + '.' + tld;
							Assert.assertTrue(
									"Domain does not match: " + fqdnWithSpaces,
									fqdnWithSpaces.matches(regex));
						}));
	}

	@Nonnull
	private static String insertRandomSpaces(@Nonnull final String s) {
		final Random random = new SecureRandom();

		// identify a random number of spaces to insert
		final int spaceCount = random.nextInt(s.length());

		// identify random positions in the string to insert this count
		final List<Integer> intList = IntStream.range(0, s.length()).boxed().collect(Collectors.toList());
		final Set<Integer> indexes = ListUtils.randomSampleFloyd(intList, spaceCount);

		// perform insertions
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			sb.append(s.charAt(i));
			if (indexes.contains(i)) {
				sb.append(' ');
			}
		}
		return sb.toString();
	}
}