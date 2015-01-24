/* Copyright (c) 2011-2015 Pushing Inertia
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
package com.pushinginertia.wicket.core.model;

import com.pushinginertia.commons.core.validation.ValidateAs;
import com.pushinginertia.commons.ui.i18n.IResourceLookupKey;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A resource model that concatenates the output of its contained models.
 */
public class ConcatResourceModel extends AbstractReadOnlyModel<String> {
	private static final long serialVersionUID = 1L;

	public static class Builder {
		private final String separator;
		private final ArrayList<IModel<String>> modelList = new ArrayList<>();

		public Builder(final String separator) {
			ValidateAs.notNull(separator, "separator");
			this.separator = separator;
		}

		public Builder add(final IModel<String> model) {
			modelList.add(model);
			return this;
		}

		/**
		 * Null-safe convenience method to append a {@link org.apache.wicket.model.ResourceModel} to the model list if
		 * the given resolver is not null. When it's null, nothing changes.
		 * @param resolver Something that provides a resource lookup key for the purpose of constructing a model.
		 * @return This instance for method chaining.
		 */
		public Builder add(final IResourceLookupKey resolver) {
			if (resolver != null) {
				return add(new ResourceModel(resolver.getResourceLookupKey()));
			}
			return this;
		}

		public ConcatResourceModel build() {
			return new ConcatResourceModel(separator, modelList);
		}
	}

	private final String separator;
	private final ArrayList<IModel<String>> modelList;

	private ConcatResourceModel(
			final String separator,
			final ArrayList<IModel<String>> modelList) {
		this.separator = separator;
		this.modelList = modelList;
	}

	/**
	 * Instantiates a new model from a serializable list.
	 * @param separator Separator string to insert between each concatenated model.
	 * @param modelList List of models to concatenate.
	 * @param <T> A serializable list.
	 * @return New instance.
	 */
	public static <T extends Serializable & List<IModel<String>>> ConcatResourceModel forList(
			final String separator,
			final T modelList) {
		return new ConcatResourceModel(separator, new ArrayList<>(modelList));
	}

	@Override
	public String getObject() {
		final StringBuilder sb = new StringBuilder();
		for (final IModel<String> model: modelList) {
			if (sb.length() > 0 && separator != null) {
				sb.append(separator);
			}
			sb.append(model.getObject());
		}
		return sb.toString();
	}

	@Override
	public void detach() {
		super.detach();
		for (final IModel<String> model: modelList) {
			model.detach();
		}
	}
}
