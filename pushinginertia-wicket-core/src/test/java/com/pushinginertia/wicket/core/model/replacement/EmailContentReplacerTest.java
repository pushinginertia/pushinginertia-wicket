package com.pushinginertia.wicket.core.model.replacement;

import com.google.common.collect.ImmutableSet;
import com.pushinginertia.commons.lang.ListUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EmailContentReplacerTest {
	private static final Set<String> COMMON_EMAIL_DOMAINS =
			ImmutableSet.of("gmail", "hotmail", "yahoo", "outlook", "live");

	private static class EmailContentReplacerMock extends EmailContentReplacer {
		EmailContentReplacerMock(@Nonnull final IEmailContentReplacerPatternProvider provider) {
			super(provider);
		}

		@Nonnull
		@Override
		public String replacement() {
			throw new UnsupportedOperationException();
		}
	}

	private final EmailContentReplacer replacer =
			new EmailContentReplacerMock(
					new EmailContentReplacerPatternProvider(COMMON_EMAIL_DOMAINS));
	private Pattern replacerPattern =
			Pattern.compile(replacer.pattern(), Pattern.CASE_INSENSITIVE);

	private void assertStringMatchesEmailPattern(final String s) {
		// ContentReplacerList performs a case insensitive pattern match
		final Matcher m = replacerPattern.matcher(s);
		Assert.assertTrue(
				MessageFormat.format(
						"String [{0}] does not match pattern: {1}",
						s,
						replacer.pattern()),
				m.matches());
	}

	@Test
	public void regex() {
		assertStringMatchesEmailPattern("user1234 [at] example.com");
		assertStringMatchesEmailPattern("user1234 {at} example.com");
		assertStringMatchesEmailPattern("user1234[at]example.com");
		assertStringMatchesEmailPattern("user1234{at}example.com");
		assertStringMatchesEmailPattern("user1234at example.com");
		assertStringMatchesEmailPattern("user(at)example dot com");
		assertStringMatchesEmailPattern("a.b @out look . com");
		assertStringMatchesEmailPattern("a.b (at) out look . com");
		assertStringMatchesEmailPattern("username at yahoo com");
		assertStringMatchesEmailPattern("username@gmail");
		assertStringMatchesEmailPattern("user_1234@yahoocom");
		assertStringMatchesEmailPattern("user(a)example.com");
		assertStringMatchesEmailPattern("user(at)example(period)com");
		// seen as (user)(@)(example.com)
		assertStringMatchesEmailPattern("user)(@)(example.com");
		assertStringMatchesEmailPattern("user)(at)(example.com");
		assertStringMatchesEmailPattern("user (at) (example) dot com");
		assertStringMatchesEmailPattern("user at yahoo. com");
		assertStringMatchesEmailPattern("user.name (at) gmail . com");
		assertStringMatchesEmailPattern("user at gmail com");
		assertStringMatchesEmailPattern("User1234 at. hotmail..com");
		assertStringMatchesEmailPattern("Someone at Gmail.Com");
		assertStringMatchesEmailPattern("user123 @ hotmail . com");
		assertStringMatchesEmailPattern("user999 <at> gmail <dot> com");
		assertStringMatchesEmailPattern("user999<at>gmail<dot>com");

		Assert.assertFalse(replacerPattern.matcher("transportation. And").matches());
		Assert.assertFalse(replacerPattern.matcher("I like to eat meat pies.").matches());
		Assert.assertFalse(replacerPattern.matcher("we eat meat every night").matches());
		Assert.assertFalse(replacerPattern.matcher("a private bathroom. There are other").matches());
	}

	@Test
	public void constructEmailDomainRegex() {
		final Set<String> commonTlds = ImmutableSet.of("com", "co.uk", "ca", "net");
		final String regex = EmailContentReplacerPatternProvider.constructEmailDomainRegex(COMMON_EMAIL_DOMAINS);

		Assert.assertTrue("yahoocom".matches(regex));
		Assert.assertTrue("gmail com".matches(regex));

		commonTlds.forEach(
				tld -> COMMON_EMAIL_DOMAINS.forEach(
						domain -> {
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