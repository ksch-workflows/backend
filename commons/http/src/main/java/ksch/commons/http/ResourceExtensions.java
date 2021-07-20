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

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class ResourceExtensions {

    private final ResourceExtensionsRegistry resourceExtensionsRegistry;

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
    protected final <T> void registerLink(Class<T> cls, Function<T, Optional<Link>> linkProvider) {
        resourceExtensionsRegistry.registerLink(cls, linkProvider);
    }
}
