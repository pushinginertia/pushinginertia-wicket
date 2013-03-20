package com.pushinginertia.wicket.core.form.behavior;

import com.pushinginertia.commons.lang.ValidateAs;
import com.pushinginertia.wicket.i18n.ResourceModelUtil;
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
		r.write(ResourceModelUtil.getString(textResourceKey));
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
