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
package com.pushinginertia.wicket.core.form.behavior;

import com.pushinginertia.commons.core.validation.ValidateAs;
import com.pushinginertia.wicket.core.ResourceModelUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.request.Response;

/**
 * Decorates an input with:
 * <pre>
 * &lt;div class="input-help"&gt;
 *     &lt;div class="q">?&lt;/div&gt;
 *     &lt;span class="text"&gt; (help text) &lt;/span&gt;
 * &lt;/div&gt;
 * </pre>
 */
public class InputHelpDecorator extends Behavior {
	private static final long serialVersionUID = 1L;
	private static final String HTML_PREAMBLE = "<div class=\"input-help\"><div class=\"q\">?</div><span class=\"text\">";
	private static final String HTML_POSTAMBLE = "</span></div>";

	private final String textResourceKey;

	public InputHelpDecorator(final String textResourceKey) {
		this.textResourceKey = ValidateAs.notNull(textResourceKey, "textResourceKey");
	}

	@Override
	public void bind(final Component component) {
		component.setOutputMarkupId(true);
	}

	@Override
	public final void beforeRender(final Component component) {
		final Response r = component.getResponse();
		writePreamble(r);
		r.write(ResourceModelUtils.getString(textResourceKey));
		writePostamble(r);
	}

	/**
	 * Writes the HTML preamble before the help text.
	 * @param r response to write the text to
	 */
	protected void writePreamble(final Response r) {
		r.write(HTML_PREAMBLE);
	}

	/**
	 * Writes the HTML postamble before the help text.
	 * @param r response to write the text to
	 */
	protected void writePostamble(final Response r) {
		r.write(HTML_POSTAMBLE);
	}
}
