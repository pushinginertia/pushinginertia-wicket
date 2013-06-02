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
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessages;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.validator.StringValidator;

/**
 * Decorates an input with a maximum length, html5 required tag (if the input is required), and a feedback message (if
 * applicable) before or after the input.
 * <p>
 * Feedback will be rendered inside a DIV element with the CSS classes "form-feedback" and "form-feedback-error" so that
 * special styling can be applied to the the feedback. Inside this DIV, each message will be generated as a list element
 * inside a UL tag. The list element will be given a CSS class corresponding to the error level of the message, in
 * uppercase (e.g., "ERROR"). See {@link org.apache.wicket.feedback.FeedbackMessage#levelStrings} for a list of possible error levels.
 */
public class InputDecorator extends Behavior {
	private static final long serialVersionUID = 1L;
	private static final String ATTR_REQUIRED = "required";
	private static final String ATTR_MAXLENGTH = "maxlength";
	private static final AttributeModifier REQUIRED_MODIFIER = new AttributeModifier(ATTR_REQUIRED, new Model<String>(ATTR_REQUIRED));

	public static final ComponentPlacement FEEDBACK_PLACEMENT_DEFAULT = ComponentPlacement.AFTER;

	/**
	 * Maximum length of the input (zero means no maximum length is enforced).
	 */
	private final int maxLength;
	private final ComponentPlacement feedbackPlacement;

	/** Indicates whether to place feedback messages before or after the component. Defaults to AFTER when not specified. */
	public enum ComponentPlacement {
		BEFORE,
		AFTER
	}

	/**
	 * Instantiates the decorator with no maximum length and feedback placed in the default position.
	 */
	public InputDecorator() {
		this.maxLength = 0;
		this.feedbackPlacement = FEEDBACK_PLACEMENT_DEFAULT;
	}

	/**
	 * Instantiates the decorator with a given maximum length and feedback placed in the default position.
	 * @param maxLength maximum input length
	 */
	public InputDecorator(final int maxLength) {
		this.maxLength = maxLength;
		this.feedbackPlacement = FEEDBACK_PLACEMENT_DEFAULT;
	}

	/**
	 * Instantiates the decorator with no maximum length and feedback placed in the given position.
	 * @param feedbackPlacement indicates where to place feedback messages
	 */
	public InputDecorator(final ComponentPlacement feedbackPlacement) {
		this.maxLength = 0;
		this.feedbackPlacement = ValidateAs.notNull(feedbackPlacement, "feedbackPlacement");
	}

	/**
	 * Instantiates the decorator with a given maximum length and feedback position.
	 * @param maxLength maximum input length
	 * @param feedbackPlacement indicates where to place feedback messages
	 */
	public InputDecorator(final int maxLength, final ComponentPlacement feedbackPlacement) {
		this.maxLength = maxLength;
		this.feedbackPlacement = ValidateAs.notNull(feedbackPlacement, "feedbackPlacement");
	}

	@Override
	public void bind(final Component component) {
		component.setOutputMarkupId(true);
	}

	/**
	 * Indicates if the HTML5 'required' attribute should be included in the input's tag if it a required input.
	 * This returns true by default but can be overridden.
	 * @return true by default
	 */
	public boolean html5RequiredModifierSupport() {
		return true;
	}

	@Override
	public void onConfigure(final Component component) {
		final FormComponent<?> fc = (FormComponent<?>) component;

		if (maxLength > 0) {
			fc.add(StringValidator.MaximumLengthValidator.maximumLength(maxLength));
			fc.add(new AttributeModifier(ATTR_MAXLENGTH, new Model<Integer>(maxLength)));
		}

		if (fc.isRequired() && html5RequiredModifierSupport()) {
			// append html5 'required' attribute
			fc.add(REQUIRED_MODIFIER);
		}

		super.onConfigure(component);
	}

	@Override
	public void beforeRender(final Component component) {
		final FormComponent<?> fc = (FormComponent<?>) component;
		if (ComponentPlacement.BEFORE.equals(feedbackPlacement)) {
			renderFeedback(fc);
		}
	}

	private void renderFeedback(final FormComponent<?> fc) {
		final FeedbackMessages messages = fc.getSession().getFeedbackMessages();
		if (messages.hasMessageFor(fc)) {
			final Response r = fc.getResponse();

			r.write("<div class=\"form-feedback form-feedback-error\">");
			r.write("<ul>");

			final IFeedbackMessageFilter filter = new ComponentFeedbackMessageFilter(fc);
			for (FeedbackMessage message: messages.messages(filter)) {
				r.write("<li class=\"");
				r.write(message.getLevelAsString().toUpperCase());
				r.write("\">");
				r.write(Strings.escapeMarkup(message.getMessage().toString()));
				r.write("</li>");
			}
			messages.clear(filter);

			r.write("</ul>");
			r.write("</div>");
		}
	}

	@Override
	public void afterRender(final Component component) {
		final FormComponent<?> fc = (FormComponent<?>) component;
		if (ComponentPlacement.AFTER.equals(feedbackPlacement)) {
			renderFeedback(fc);
		}
	}

	@Override
	public void onComponentTag(final Component component, final ComponentTag tag) {
		final FormComponent<?> fc = (FormComponent<?>) component;
		if (!fc.isValid()) {
			final String cl = tag.getAttribute("class");
			if (cl == null) {
				tag.put("class", "error");
			} else {
				tag.put("class", "error " + cl);
			}
		}
	}

	/**
	 * Generates the class attribute to add to the label tag.
	 * @param fc checked if valid - adds a 'input-error' class if the component is not valid
	 * @param labelClass can be null
	 * @return null if nothing to add
	 */
	protected static String getLabelClass(final FormComponent<?> fc, final String labelClass) {
		final StringBuilder sb = new StringBuilder();
		if (labelClass != null) {
			sb.append(labelClass);
		}
		if (!fc.isValid()) {
			if (sb.length() > 0) {
				sb.append(' ');
			}
			sb.append("input-error");
		}

		if (sb.length() == 0) {
			return null;
		}
		return sb.toString();
	}
}
