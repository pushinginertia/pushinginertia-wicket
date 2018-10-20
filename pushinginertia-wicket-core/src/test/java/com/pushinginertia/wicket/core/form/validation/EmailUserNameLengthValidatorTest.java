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

import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class EmailUserNameLengthValidatorTest {
	private static final EmailUserNameLengthValidator.DomainRule ABC = EmailUserNameLengthValidator.DomainRule.forUserNameMinLength(2);
	private static final EmailUserNameLengthValidator.DomainRule ABC_XY = EmailUserNameLengthValidator.DomainRule.forUserNameMinLength(3);
	private static final EmailUserNameLengthValidator.DomainRule DEF = EmailUserNameLengthValidator.DomainRule.forUserNameMinLength(4);
	private static final EmailUserNameLengthValidator.DomainRule DEF_XY = EmailUserNameLengthValidator.DomainRule.forUserNameMinLength(5);
	private static final EmailUserNameLengthValidator.DomainRule DEF_XX_YY = EmailUserNameLengthValidator.DomainRule.forUserNameMinLength(6);
	private static final Map<String, EmailUserNameLengthValidator.DomainRule> RULES =
			ImmutableMap.of(
					"abc", ABC,
					"abc.xy", ABC_XY,
					"def", DEF,
					"def.xy", DEF_XY,
					"def.xx.yy", DEF_XX_YY);

	@Test
	public void lookUpDomain() {
		Assert.assertEquals(ABC, EmailUserNameLengthValidator.lookUpDomain("abc", RULES));
		Assert.assertEquals(ABC, EmailUserNameLengthValidator.lookUpDomain("abc.xx", RULES));
		Assert.assertEquals(ABC_XY, EmailUserNameLengthValidator.lookUpDomain("abc.xy", RULES));

		Assert.assertEquals(DEF, EmailUserNameLengthValidator.lookUpDomain("def", RULES));
		Assert.assertEquals(DEF_XY, EmailUserNameLengthValidator.lookUpDomain("def.xy", RULES));
		Assert.assertEquals(DEF, EmailUserNameLengthValidator.lookUpDomain("def.xx", RULES));
		Assert.assertEquals(DEF, EmailUserNameLengthValidator.lookUpDomain("def.xx.xy", RULES));
		Assert.assertEquals(DEF_XX_YY, EmailUserNameLengthValidator.lookUpDomain("def.xx.yy", RULES));
	}

	@Test
	public void getDomainRuleEmailViolates() {
		Assert.assertNull(EmailUserNameLengthValidator.getDomainRuleEmailViolates(null, RULES));
		Assert.assertNull(EmailUserNameLengthValidator.getDomainRuleEmailViolates("", RULES));
		Assert.assertNull(EmailUserNameLengthValidator.getDomainRuleEmailViolates("abc", RULES));
		Assert.assertNull(EmailUserNameLengthValidator.getDomainRuleEmailViolates("abc@", RULES));
		Assert.assertNull(EmailUserNameLengthValidator.getDomainRuleEmailViolates("abc@abc.com", RULES));
		Assert.assertNotNull(EmailUserNameLengthValidator.getDomainRuleEmailViolates("a@abc.com", RULES));
		Assert.assertNull(EmailUserNameLengthValidator.getDomainRuleEmailViolates("ab@abc.com", RULES));
		Assert.assertNull(EmailUserNameLengthValidator.getDomainRuleEmailViolates("abc@abc.com", RULES));
	}
}
