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
package com.pushinginertia.wicket.core.form.component;

import com.pushinginertia.wicket.core.converter.TextFieldTitleCaseConverter;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;import java.lang.Class;import java.lang.Override;import java.lang.String;

/**
 * Sublass of {@link org.apache.wicket.markup.html.form.TextField} that transorms user input into title case via the
 * converter {@link TextFieldTitleCaseConverter}.
 */
public class TextFieldTitleCase extends TextField<String> {
	private static final long serialVersionUID = 1L;

	public TextFieldTitleCase(final String id) {
		super(id);
	}

	public TextFieldTitleCase(final String id, final Class<String> type) {
		super(id, type);
	}

	public TextFieldTitleCase(final String id, final IModel<String> model) {
		super(id, model);
	}

	public TextFieldTitleCase(final String id, final IModel<String> model, final Class<String> type) {
		super(id, model, type);
	}

	@Override
	public <String> IConverter<String> getConverter(Class<String> type) {
		return (IConverter<String>)new TextFieldTitleCaseConverter();
	}
}
