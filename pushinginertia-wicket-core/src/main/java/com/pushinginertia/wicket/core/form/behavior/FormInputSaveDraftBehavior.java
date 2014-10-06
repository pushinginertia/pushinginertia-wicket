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
package com.pushinginertia.wicket.core.form.behavior;

import com.pushinginertia.commons.core.validation.ValidateAs;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.time.Duration;

import java.io.Serializable;
import java.util.Set;

/**
 * This behavior is added to a page or a form and adds the ability to save drafts for one or more form inputs as a user
 * types. This is typically for &lt;textarea&gt; inputs but could also work with standard &lt;input&gt; inputs. A
 * javascript timer is created on the client that fires every {@link #saveDraftInterval} duration and sends updated form
 * values to the server if the user has made changes. A keepalive is also supported so that the server doesn't time out
 * if the user doesn't enter input long enough that the session expires.
 * <p>
 * A good interval for saving drafts is in the 10-15 second range, but it can be tweaked depending on specific needs or
 * server loads.
 * @see <a href='http://pushinginertia.com/2014/10/save-drafts-of-form-textarea-inputs-in-apache-wicket-automatically/'>Save drafts of form textarea inputs in Apache Wicket automatically</a>
 */
public class FormInputSaveDraftBehavior extends AbstractDefaultAjaxBehavior {
	private static final long serialVersionUID = 1L;

	private final FormComponent<String>[] components;
	private final Duration saveDraftInterval;
	private final Duration keepAliveInterval;
	private final SaveDraftCallback callback;

	/**
	 * This behavior is added to a page or a form and adds the ability to save drafts for one or more form inputs as a
	 * user types.
	 * @param callback This is called each time a form input's value is changed by the user.
	 * @param saveDraftInterval Interval to wait between transmission of form input value changes.
	 * @param keepAliveInterval A maximum time to wait between ajax callbacks before a keep-alive callback is made so
	 * that the user's session doesn't expire. A recommended value is about one third of the server's session time out.
	 * @param components Components to enable saving of draft input.
	 */
	public FormInputSaveDraftBehavior(
			final SaveDraftCallback callback,
			final Duration saveDraftInterval,
			final Duration keepAliveInterval,
			final FormComponent<String>... components) {
		super();
		this.callback = ValidateAs.notNull(callback, "callback");
		this.saveDraftInterval = ValidateAs.notNull(saveDraftInterval, "saveDraftInterval");
		this.keepAliveInterval = ValidateAs.notNull(keepAliveInterval, "keepAliveInterval");
		this.components = components;
		for (final FormComponent component: components) {
			component.setOutputMarkupId(true);
		}
	}

	@Override
	public void renderHead(final Component component, final IHeaderResponse response) {
		super.renderHead(component, response);

		// prepopulate the saved drafts on the client with whatever model objects already exist
		final StringBuilder sb = new StringBuilder();
		for (final FormComponent<String> c: components) {
			sb.append(c.getMarkupId()).append(":'");
			final String inputValue = c.getModelObject();
			if (inputValue != null) {
				sb.append(inputValue.replace("\n", "\\n").replace("'", "\\'"));
			}
			sb.append("',");
		}

		response.renderJavaScript(
				"var savedDrafts={" + sb.toString() + "};\n" +
				"var lastCallbackTime=(new Date()).getTime();\n" +
				"var interval=setInterval(function(){pushDraft();}, " + saveDraftInterval.getMilliseconds() + ");\n" +
				"var keepAliveInterval=" + keepAliveInterval.getMilliseconds() + ";\n" +  // maximum time between callbacks
				"function pushDraft() {\n" +
				"\tvar p={};\n" +
				"\tvar push=false;\n" +
				"\tfor(k in savedDrafts){\n" +
				"\t\tvar el=document.getElementById(k);\n" +
				"\t\tif(savedDrafts[k]!==el.value){\n" +
				"\t\t\tp[k]=el.value;\n" +
				"\t\t\tpush=true;\n" +
				"\t\t}\n" +
				"\t}\n" +
				"\tvar now=(new Date()).getTime();\n" +
				"\tif(push||now-lastCallbackTime>keepAliveInterval){\n" +
				// Insert a dummy post variable for servers that reject POSTs without any key/value pairs.
				// See: https://cwiki.apache.org/confluence/display/WICKET/Calling+Wicket+from+Javascript
				"\t\tvar s='_now='+now;\n" +
				"\t\tfor(k in p){\n" +
				"\t\t\ts+='&'+k+'='+encodeURIComponent(p[k]);\n" +
				"\t\t\tsavedDrafts[k]=p[k];\n" +
				"\t\t}\n" +
				"\t\twicketAjaxPost('" + getCallbackUrl() + "', s);\n" +
				"\t\tlastCallbackTime=now;\n" +
				"\t}\n" +
				"}",
				"FormSaveDraftBehavior");
	}

	@Override
	protected void respond(final AjaxRequestTarget target) {
		// prevent focus change
		target.focusComponent(null);

		// fire callback for any changes sent from the client
		final IRequestParameters parameters = RequestCycle.get().getRequest().getRequestParameters();
		final Set<String> parameterNames = parameters.getParameterNames();
		for (final FormComponent<String> component: components) {
			final String id = component.getMarkupId();
			if (parameterNames.contains(id)) {
				final StringValue value = parameters.getParameterValue(id);
				callback.saveDraft(component, value.toString());
			}
		}
	}

	public interface SaveDraftCallback extends Serializable {
		public void saveDraft(FormComponent<String> component, String value);
	}
}
