package com.pushinginertia.wicket.core.form.behavior;

import com.pushinginertia.commons.lang.CharUtils;
import com.pushinginertia.commons.lang.ValidateAs;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.util.lang.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import java.lang.Override;import java.lang.String;

/**
 * Performs validation on inputs for first and family names, ensuring that they are each at least two characters, that
 * one of those characters is not a period, that they are not the same values (regardless of case), and that they don't
 * contain characters that would not exist in a real name (such as punctuation).
 */
public class RealFullNameValidator extends AbstractFormValidator {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(RealFullNameValidator.class);

	private static final char[] ILLEGAL_CHARS =
			{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			 '!', '@', '#', '$', '%', '^', '&', '*', '(', ')',
			 '+', '_', '=', '<', '>', '?', '/', ':', ';', '"',
			 '[', ']', '{', '}', '|', '~', '\\'};

	private final TextField<String> firstName;
	private final TextField<String> familyName;

	public RealFullNameValidator(final TextField<String> firstName, final TextField<String> familyName) {
		this.firstName = ValidateAs.notNull(firstName, "firstName");
		this.familyName = ValidateAs.notNull(familyName, "familyName");
	}

	public FormComponent<?>[] getDependentFormComponents() {
		return new TextField[] {firstName, familyName};
	}

	public void validate(final Form<?> form) {
		// 1. minimum length and no dot
		if (!satisfiesLengthWithoutDot(firstName)) {
			LOG.info(toLogString(form, firstName));
			error(firstName);
			return;
		}
		if (!satisfiesLengthWithoutDot(familyName)) {
			LOG.info(toLogString(form, familyName));
			error(familyName);
			return;
		}

		// 2. check equality
		if (Objects.equal(firstName.getInput().toLowerCase(), familyName.getInput().toLowerCase())) {
			LOG.info(toLogString(form, firstName));
			error(firstName);
			return;
		}

		// 3. check for illegal characters
		if (CharUtils.inCharArray(firstName.getInput(), ILLEGAL_CHARS) >= 0) {
			error(firstName);
		} else if (CharUtils.inCharArray(familyName.getInput(), ILLEGAL_CHARS) >= 0) {
			error(familyName);
		}
	}

	private String toLogString(final Form form, final TextField<String> errorComponent) {
		final String id = errorComponent.getId();
		final String pageClass = form.getPage().getClass().getSimpleName();
		return pageClass + " failed validation on input [" + id + "]: " + toString(firstName) + ", " + toString(familyName);
	}

	private String toString(final TextField<String> tf) {
		final String id = tf.getId();
		final String input = tf.getInput();
		return id + "=[" + input + ']';
	}

	private static boolean satisfiesLengthWithoutDot(final TextField<String> tf) {
		return satisfiesLengthWithoutDot(tf.getInput());
	}

	static boolean satisfiesLengthWithoutDot(final String input) {
		// fail if input is less than 2 characters long
		if (input.length() < 2) {
			return false;
		}

		// fail if input is 2 characters but contains a dot
		if (input.length() == 2 && input.indexOf('.') >= 0) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "RealFullNameValidator{firstName=" + firstName + ", familyName=" + familyName + '}';
	}
}
