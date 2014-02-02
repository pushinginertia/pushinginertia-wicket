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
package com.pushinginertia.wicket.core.helper;

import com.pushinginertia.commons.service.helper.LoadEntityAction;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseException;

/**
 * Restarts the request processing with a given target page if an entity cannot be loaded.
 * @param <E> type of the entity being loaded
 */
public class RestartResponseOnEntityNotFoundAction<E> extends LoadEntityAction<E> {
	private final Class<? extends Page> targetPage;

	/**
	 * Restarts the request processing with a given target page if an entity cannot be loaded.
	 * @param targetPage page to send the user to if an entity does not exist
	 */
	public RestartResponseOnEntityNotFoundAction(final Class<? extends Page> targetPage) {
		this.targetPage = targetPage;
	}

	@Override
	public final <I> void onEntityNotFound(final I input) {
		throw new RestartResponseException(targetPage);
	}
}
