/* Copyright (c) 2011-2013 Pushing Inertia
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
package com.pushinginertia.wicket.core.behavior;

import com.pushinginertia.commons.lang.ValidateAs;
import com.pushinginertia.commons.web.HtmlCharacterUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * An obfuscated mailto link. This inserts a small piece of javascript in the HEAD portion of the HTML output with an
 * obfuscated email link. It then uses jquery to replace the contents of an HTML element with a string containing a
 * mailto link to the given email address. This is useful to prevent most scraper bots from harvesting an email
 * address on the page.
 */
public class ObfuscatedMailtoJavaScriptBehavior extends Behavior {
	private static final long serialVersionUID = 1L;

	/** "mailto:" = "&#109&#97&#105&#108&#116&#111&#58" */
	protected static final String MAILTO_OBFUSCATED = "'+'&#109'+'&#97'+'&#105'+'&#108'+'&#116'+'&#111'+'&#58'+'";

	private final String id;
	/**
	 * A string containing the verbiage to display to the user, with an embedded ${emailLink} placeholder for the &lt;a&gt;
	 * mailto tag. This placeholder is changed to a mailto link using the email address and link text.
	 */
	private final String verbiage;
	/**
	 * Email address to obfuscate.
	 */
	private final String emailAddress;
	/**
	 * Text to display within the &lt;a&gt;...&lt;/a&gt; tags.
	 */
	private final String linkText;

	/**
	 *
	 * @param id ID of the HTML element to attach the javascript event to
	 * @param verbiage Verbiage is a string with an embedded ${emailLink} placeholder. This placeholder is changed to a
	 * mailto link using the email address and link text.
	 * @param emailAddress email address to obfuscate
	 * @param linkText text to display within the &lt;a&gt;...&lt;/a&gt; tags
	 */
	public ObfuscatedMailtoJavaScriptBehavior(final String id, final String verbiage, final String emailAddress, final String linkText) {
		this.id = ValidateAs.notNull(id, "id");
		this.verbiage = ValidateAs.notNull(verbiage, "verbiage");
		this.emailAddress = ValidateAs.notNull(emailAddress, "emailAddress");
		this.linkText = ValidateAs.notNull(linkText, "linkText");
	}

	@Override
	public void renderHead(final Component component, final IHeaderResponse response) {
		super.renderHead(component, response);

		final StringBuilder emailLink = new StringBuilder();
		emailLink
				.append("<a href=\"")
				.append(MAILTO_OBFUSCATED)
				.append(HtmlCharacterUtils.toCharacterEntities(emailAddress))
				.append("\">")
				.append(HtmlCharacterUtils.toCharacterEntities(linkText))
				.append("</a>");

		final String s = verbiage.replaceAll("\\$\\{emailLink\\}", emailLink.toString());

		response.renderJavaScript(
				"$(window).load(function(){" +
				"$('#" + id + "').html('" + s + "');" +
				"});",
				ObfuscatedMailtoJavaScriptBehavior.class.getSimpleName() + '.' + id);
	}
}