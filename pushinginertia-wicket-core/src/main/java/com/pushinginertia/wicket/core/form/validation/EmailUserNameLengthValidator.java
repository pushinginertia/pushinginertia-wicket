/* Copyright (c) 2011-2018 Pushing Inertia
 * All rights reserved.  http://pushinginertia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pushinginertia.wicket.core.form.validation;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * Validates that the user name portion of an email address satisfies a min/max
 * length limit based on its domain. This can be used to validate certain email
 * domains where we know the min/max user name lengths. For example, gmail has
 * a 6 char minimum.
 *
 * Example usage:
 * <pre>
 * EmailUserNameLengthValidator validator = new EmailUserNameLengthValidator(
 *     ImmutableMap.of(
 *         "gmail.com", DomainRule.forUserNameMinMaxLength(6, 30),
 *         "googlemail.com", DomainRule.forUserNameMinLength(6),
 *         "yahoo", DomainRule.forUserNameMinLength(4),
 *         "aol", DomainRule.forUserNameMinLength(4)));
 * emailInput.add(validator);
 * </pre>
 */
public class EmailUserNameLengthValidator extends AbstractValidator<String> {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG =
			LoggerFactory.getLogger(EmailUserNameLengthValidator.class);

	private final Map<String, DomainRule> domainRules;
	private final String resourceKey;

	public static class DomainRule implements Serializable {
		private static final long serialVersionUID = 1L;

		private final int userNameMinLength;
		private final Integer userNameMaxLength;

		private DomainRule(
				final int userNameMinLength,
				@Nullable final Integer userNameMaxLength) {
			this.userNameMinLength = userNameMinLength;
			this.userNameMaxLength = userNameMaxLength;
		}

		public static DomainRule forUserNameMinLength(
				final int userNameMinLength) {
			return new DomainRule(userNameMinLength, null);
		}

		public static DomainRule forUserNameMinMaxLength(
				final int userNameMinLength,
				final int userNameMaxLength) {
			return new DomainRule(userNameMinLength, userNameMaxLength);
		}

		boolean satisfiesUserNameLengthRequirement(
				final int userNameLength) {
			return
					userNameLength >= userNameMinLength &&
					(userNameMaxLength == null || userNameLength <= userNameMaxLength);
		}

		@Override
		public boolean equals(final Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			final DomainRule that = (DomainRule) o;
			return userNameMinLength == that.userNameMinLength &&
					Objects.equals(userNameMaxLength, that.userNameMaxLength);
		}

		@Override
		public int hashCode() {
			return Objects.hash(userNameMinLength, userNameMaxLength);
		}

		@Override
		public String toString() {
			return "DomainRule{userNameMinLength=" + userNameMinLength +
					", userNameMaxLength=" + userNameMaxLength + '}';
		}
	}

	/**
	 * @param domainRules The key is a subset of the domain to match. This can
	 *                    broadly match all TLDs by specifying just the non-TLD
	 *                    portion (e.g., "yahoo" to match yahoo.com, yahoo.ca,
	 *                    etc.) or match a specific domain. The more specific
	 *                    match will take precedence.
	 */
	public EmailUserNameLengthValidator(
			@Nonnull final Map<String, DomainRule> domainRules,
			@Nonnull final String resourceKey) {
		this.domainRules = domainRules;
		this.resourceKey = resourceKey;
	}

	public EmailUserNameLengthValidator(
			@Nonnull final Map<String, DomainRule> domainRules) {
		this(domainRules, "EmailUserNameLengthValidator");
	}

	@Override
	protected void onValidate(final IValidatable<String> validatable) {
		final String email = validatable.getValue();
		final DomainRule violatedRule = getDomainRuleEmailViolates(email, domainRules);
		if (violatedRule != null) {
			LOG.info(
					"{} failed validation on input [{}] due to rule {}.",
					getClass().getSimpleName(),
					email,
					violatedRule);
			error(validatable, resourceKey);
		}
	}

	@Nullable
	static DomainRule getDomainRuleEmailViolates(
			@Nullable final String email,
			@Nonnull final Map<String, DomainRule> domainRules) {
		if (email == null || email.length() == 0) {
			return null;
		}

		final int atIndex = email.lastIndexOf('@');
		if (atIndex < 0) {
			// not a valid email, some other validator will take care of this
			return null;
		}

		final DomainRule rule = lookUpDomain(email.substring(atIndex + 1), domainRules);
		if (rule == null || rule.satisfiesUserNameLengthRequirement(atIndex)) {
			return null;
		}
		return rule;
	}

	@Nullable
	static DomainRule lookUpDomain(
			@Nonnull final String domain,
			@Nonnull final Map<String, DomainRule> domainRules) {
		// Iteratively look up domains from the map, starting from the leftmost
		// part of the domain, until one is found or the entire domain has been
		// checked.
		if (domainRules.containsKey(domain)) {
			return domainRules.get(domain);
		}
		for (int i = domain.length() - 1; i >= 0; i--) {
			if (domain.charAt(i) == '.') {
				final String key = domain.substring(0, i);
				if (domainRules.containsKey(key)) {
					return domainRules.get(key);
				}
			}
		}

		return null;
	}
}
