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
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Response;

/**
 * Decorates an input with a maximum length, html5 required tag (if required), a label before the input, and a feedback
 * message (if applicable) after the input.
 */
public class InputDecoratorWithLabel extends InputDecorator {
	private static final long serialVersionUID = 1L;
	public static final ComponentPlacement LABEL_PLACEMENT_DEFAULT = ComponentPlacement.BEFORE;

	private final ComponentPlacement labelPlacement;
	private String labelClass = "top";

	private IModel<String> labelModel;

	public InputDecoratorWithLabel(final IModel<String> model) {
		this(0, model, LABEL_PLACEMENT_DEFAULT);
	}

	public InputDecoratorWithLabel(final IModel<String> model, final ComponentPlacement labelPlacement) {
		this(0, model, labelPlacement);
	}

	public InputDecoratorWithLabel(final int maxLength, final IModel<String> model) {
		this(maxLength, model, LABEL_PLACEMENT_DEFAULT);
	}

	public InputDecoratorWithLabel(final int maxLength, final IModel<String> model, final ComponentPlacement labelPlacement) {
		super(maxLength);
		this.labelModel = ValidateAs.notNull(model, "model");
		this.labelPlacement = ValidateAs.notNull(labelPlacement, "labelPlacement");
	}

	public final String getLabelClass() {
		return labelClass;
	}

	/**
	 * Sets the CSS class to include in the &lt;label&gt; tag.
	 * @param labelClass CSS class
	 * @return instance of this class for method chaining
	 */
	public InputDecoratorWithLabel setLabelClass(final String labelClass) {
		this.labelClass = ValidateAs.notNull(labelClass, "labelClass");
		return this;
	}

	private void renderLabel(final FormComponent<?> fc) {
		final Response r = fc.getResponse();
		final String label = labelModel.getObject();

		r.write("<label for=\"");
		r.write(fc.getMarkupId());
		r.write("\"");

		// class
		final StringBuffer sbClass = new StringBuffer();
		sbClass.append(labelClass);
		if (!fc.isValid()) {
			if (sbClass.length() > 0)
				sbClass.append(' ');
			sbClass.append("input-error");
		}
		if (sbClass.length() > 0) {
			r.write(" class=\"");
			r.write(sbClass);
			r.write("\"");
		}

		r.write(">");
		r.write(label);
		r.write("</label>");
//			if (fc.isRequired()) {
//				r.write("<span class=\"required\">*</span>");
//			}
	}

	@Override
	public void beforeRender(final Component component) {
		if (ComponentPlacement.BEFORE.equals(labelPlacement)) {
			final FormComponent<?> fc = (FormComponent<?>) component;
			renderLabel(fc);
			final Response r = fc.getResponse();
			r.write(" ");
		}
		super.beforeRender(component);
	}

	@Override
	public void afterRender(final Component component) {
		if (ComponentPlacement.AFTER.equals(labelPlacement)) {
			final FormComponent<?> fc = (FormComponent<?>) component;
			final Response r = fc.getResponse();
			r.write(" ");
			renderLabel(fc);
		}
		super.afterRender(component);
	}
}
