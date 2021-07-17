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
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Component
@SuppressWarnings({"rawtypes", "unchecked"})
public class ResourceExtensionRegistry {

    private final Map<Class, List<LinkRegistryEntry>> entries = new HashMap<>();

    <T> void registerLink(Class<T> cls, Function<T, Optional<Link>> linkProvider) {
        if (entries.containsKey(cls)) {
            var links = entries.get(cls);
            var entry = new LinkRegistryEntry(linkProvider);
            links.add(entry);
        } else {
            var links = new ArrayList<LinkRegistryEntry>();
            links.add(new LinkRegistryEntry(linkProvider));
            entries.put(cls, links);
        }
    }

    /**
     * @return the links which should be added to a resource for the provided entity.
     */
    public <T> List<Link> getLinks(Class<T> cls, T entity) {
        if (!entries.containsKey(cls)) {
            return new ArrayList<>();
        } else {
            var links = entries.get(cls);
            return links.stream()
                    .map(link -> link.linkProvider.apply(entity))
                    .map(link -> (Optional<Link>) link)
                    .map(Optional::get)
                    .collect(toList());
        }
    }

    @RequiredArgsConstructor
    private static class LinkRegistryEntry <T> {
        final Function<T, Optional<Link>> linkProvider;
    }
}
