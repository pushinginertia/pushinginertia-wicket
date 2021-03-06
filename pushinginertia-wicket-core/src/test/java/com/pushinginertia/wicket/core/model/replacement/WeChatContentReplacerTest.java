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
package com.pushinginertia.wicket.core.model.replacement;

import org.apache.wicket.model.Model;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nonnull;

public class WeChatContentReplacerTest {
	private static final WeChatContentReplacer REPLACER = new WeChatContentReplacer() {
		@Nonnull
		@Override
		public String replacement() {
			return "[blocked]";
		}
	};

	@Test
	public void replace() {
		final ContentReplacerList.Builder builder = new ContentReplacerList.Builder();
		builder.add(REPLACER);

		final ContentReplacementModel model =
				new ContentReplacementModel(
						Model.of(
								"微 信：Username9876（微信）\n" +
								"微 信： Username9876（微信）\n" +
								"或微信:       user1234567\n" +
								"微信号：user_name\n" +
								"微信号Username\n" +
								"WeChat ID: user1234\n" +
								"wechat: user1234\n" +
								"wechat user1234\n" +
								"WeChat: user1234\n"),
						builder.build());

		Assert.assertEquals(
				"[blocked]\n" +
				"[blocked]\n" +
				"或[blocked]\n" +
				"[blocked]\n" +
				"[blocked]\n" +
				"[blocked]\n" +
				"[blocked]\n" +
				"[blocked]\n" +
				"[blocked]\n",
				model.getObject());
	}
}
