/*
 * Copyright 2021 KS-plus e.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ksch.commons.http;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;

import javax.annotation.PostConstruct;
import java.util.function.Function;

import static lombok.AccessLevel.PROTECTED;

public abstract class ResourceExtensions {

    @Autowired
    @Setter(PROTECTED) // required for testing
    private ResourceExtensionRegistry resourceExtensionRegistry;

    /**
     * Use this method to register the resource extensions.
     *
     * @see #registerLink(Class, Function)
     */
    @PostConstruct
    protected abstract void init();

    /**
     * Registers a callback function which generates links in REST resources for entities
     * of the provided type.
     */
    protected final <T> void registerLink(Class<T> cls, Function<T, Link> linkProvider) {
        resourceExtensionRegistry.registerLink(cls, linkProvider);
    }
}
