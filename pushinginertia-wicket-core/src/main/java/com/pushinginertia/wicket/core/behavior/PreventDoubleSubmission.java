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

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * Prevents a user from double clicking a form's submit button by setting a data element on the form the first time
 * the submit button is pushed and ignoring all further clicks on the button. Requires the jquery plugin
 * preventDoubleSubmission.
 * @see <a href="http://stackoverflow.com/questions/2830542/prevent-double-submission-of-forms-in-jquery">http://stackoverflow.com/questions/2830542/prevent-double-submission-of-forms-in-jquery</a>
 */
public class PreventDoubleSubmission extends Behavior {
	private static final long serialVersionUID = 1L;

	public PreventDoubleSubmission() {
	}

	@Override
	public void renderHead(final Component component, final IHeaderResponse response) {
		super.renderHead(component, response);
		response.renderJavaScript(
				"$(document).ready(function(){\n" +
						"\t$('form').preventDoubleSubmission();\n" +
						"});",
				"PreventDoubleSubmission");
	}
}
