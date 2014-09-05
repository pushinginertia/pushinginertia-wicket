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
package com.pushinginertia.wicket.core.model.replacement;

import org.apache.wicket.model.Model;
import org.junit.Assert;
import org.junit.Test;

public class ContentReplacementModelTest {
	private static final EmailToLinkContentReplacer EMAIL_TO_LINK =
			new EmailToLinkContentReplacer("/contact", "email link");
	private static final WebLinkContentReplacer WEB_LINK =
			new WebLinkContentReplacer() {
				private static final long serialVersionUID = 1L;

				@Override
				public String replacement() {
					return "[web link]";
				}
			};
	public static final NumberSequenceContentReplacer NUMBER_TO_LINK =
			new NumberSequenceContentReplacer(7, "/contact", "number link");

	@Test
	public void emailReplacer() {
		Assert.assertEquals("<a href=\"/contact\">email link</a>", EMAIL_TO_LINK.replacement());
	}

	@Test
	public void emailToLinkNoEscape() {
		final ContentReplacerList.Builder builder = new ContentReplacerList.Builder();
		builder.add(EMAIL_TO_LINK);
		final ContentReplacementModel model =
				new ContentReplacementModel(
						Model.of("you can <b>contact me</b> at user@gmail.com or user+3@gmail.com blah blah blah."),
						builder.build());
		Assert.assertEquals(
				"you can <b>contact me</b> at <a href=\"/contact\">email link</a> or <a href=\"/contact\">email link</a> blah blah blah.",
				model.getObject());

		model.setObject("<b>contact me</b> at user@gmail.com.");
		Assert.assertEquals(
				"<b>contact me</b> at <a href=\"/contact\">email link</a>.",
				model.getObject());

		model.setObject("<b>contact me</b> at user at gmail.com.");
		Assert.assertEquals(
				"<b>contact me</b> at <a href=\"/contact\">email link</a>.",
				model.getObject());

		model.setObject("<b>contact me</b> at user  at  gmail.com.");
		Assert.assertEquals(
				"<b>contact me</b> at <a href=\"/contact\">email link</a>.",
				model.getObject());

		model.setObject("<b>contact me</b> at user at gmail dot com.");
		Assert.assertEquals(
				"<b>contact me</b> at <a href=\"/contact\">email link</a>.",
				model.getObject());
	}

	@Test
	public void emailToLinkWithEscape() {
		final ContentReplacerList.Builder builder = new ContentReplacerList.Builder();
		builder.add(EMAIL_TO_LINK);
		final ContentReplacementModel model =
				new ContentReplacementModel(
						Model.of("you can <b>contact me</b> at user@gmail.com or user+3@gmail.com blah blah blah."),
						builder.build());
		model.setEscapeModelString(true);
		Assert.assertEquals(
				"you can &lt;b&gt;contact me&lt;/b&gt; at <a href=\"/contact\">email link</a> or <a href=\"/contact\">email link</a> blah blah blah.",
				model.getObject());

		model.setObject("<b>contact me</b> at user@gmail.com.");
		Assert.assertEquals(
				"&lt;b&gt;contact me&lt;/b&gt; at <a href=\"/contact\">email link</a>.",
				model.getObject());
	}

	@Test
	public void numberToLink() {
		final ContentReplacerList.Builder builder = new ContentReplacerList.Builder();
		builder.add(NUMBER_TO_LINK);

		final ContentReplacementModel model =
				new ContentReplacementModel(
						Model.of("you can <b>contact me</b> at 123-4567 or (123) 456-7890 blah blah blah."),
						builder.build());
		Assert.assertEquals(
				"you can <b>contact me</b> at <a href=\"/contact\">number link</a> or <a href=\"/contact\">number link</a> blah blah blah.",
				model.getObject());

		model.setObject("<b>contact me</b> at (zero one two) three four five, six seven eight nine.");
		Assert.assertEquals(
				"<b>contact me</b> at <a href=\"/contact\">number link</a>.",
				model.getObject());
	}

	@Test
	public void webLink() {
		final ContentReplacerList.Builder builder = new ContentReplacerList.Builder();
		builder.add(WEB_LINK);
		final ContentReplacementModel model =
				new ContentReplacementModel(
						Model.of("go to http://example.ca/a-long-directory-name-with-hyphens-and-UPPERCASE-and-an-@-sign/SDDFKD3827495743 for my awesome web page"),
						builder.build());
		Assert.assertEquals(
				"go to [web link] for my awesome web page",
				model.getObject());

		model.setObject("check out my photos:\nhttp://www.flickr.com/photos/827592947475440538723632@A12/sets/93287643590549026723/\n\n");
		Assert.assertEquals(
				"check out my photos:\n[web link]\n\n",
				model.getObject());

		model.setObject("check out my site at www.example.com, you'll like it");
		Assert.assertEquals(
				"check out my site at [web link], you'll like it",
				model.getObject());

		model.setObject("check out my site at www.example.com/path1/path2 - you'll like it");
		Assert.assertEquals(
				"check out my site at [web link] - you'll like it",
				model.getObject());
	}
}