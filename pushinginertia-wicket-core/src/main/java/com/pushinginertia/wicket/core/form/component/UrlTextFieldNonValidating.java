package com.pushinginertia.wicket.core.form.component;

import org.apache.wicket.markup.html.form.TextField;import java.lang.Override;import java.lang.String;

/**
 * Similar to {@link org.apache.wicket.markup.html.form.UrlTextField} but does not perform server side validation on
 * the input given by the user.
 */
public class UrlTextFieldNonValidating extends TextField<String> {
	private static final long serialVersionUID = 1L;

	public UrlTextFieldNonValidating(final String id) {
		super(id);
	}

	@Override
	protected String getInputType() {
		return "url";
	}
}
