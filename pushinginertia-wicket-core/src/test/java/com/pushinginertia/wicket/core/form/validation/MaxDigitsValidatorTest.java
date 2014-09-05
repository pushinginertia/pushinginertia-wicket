/* Copyright (c) 2011-2014 Pushing Inertia
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

import org.junit.Assert;
import org.junit.Test;

public class MaxDigitsValidatorTest {
	@Test
	public void isValid() {
		Assert.assertTrue(MaxDigitsValidator.isValid("abc", 3));
		Assert.assertTrue(MaxDigitsValidator.isValid("abc1", 3));
		Assert.assertTrue(MaxDigitsValidator.isValid("abc12", 3));
		Assert.assertTrue(MaxDigitsValidator.isValid("abc123", 3));
		Assert.assertFalse(MaxDigitsValidator.isValid("abc1234", 3));
		Assert.assertFalse(MaxDigitsValidator.isValid("1abc234", 3));
		Assert.assertFalse(MaxDigitsValidator.isValid("1a2bc34", 3));
		Assert.assertFalse(MaxDigitsValidator.isValid("1a2bc34def", 3));
	}
}
