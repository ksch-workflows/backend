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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class ResourceExtensionsTest {

    private final ResourceExtensionsRegistry resourceExtensionsRegistry = new ResourceExtensionsRegistry();

    private final ExampleEntity exampleEntity = new ExampleEntity(UUID.randomUUID());

    @Test
    void should_add_link_to_resource_extensions() {
        var resourceExtension = new SingleResourceExtension(resourceExtensionsRegistry);

        resourceExtension.init();

        var links = resourceExtensionsRegistry.getLinks(ExampleEntity.class, exampleEntity);
        assertThat(links.size(), equalTo(1));
        assertThat(links.get(0).getRel().value(), equalTo("current-example"));
        assertThat(links.get(0).getHref(), containsString(exampleEntity.getId().toString()));
    }

    @Test
    void should_two_links_to_resource_extensions() {
        var resourceExtensions = new TwoResourceExtensions(resourceExtensionsRegistry);

        resourceExtensions.init();

        var links = resourceExtensionsRegistry.getLinks(ExampleEntity.class, exampleEntity);
        assertThat(links.size(), equalTo(2));
        assertThat(links.get(0).getRel().value(), equalTo("current-example"));
        assertThat(links.get(0).getHref(), containsString(exampleEntity.getId().toString()));
        assertThat(links.get(1).getRel().value(), equalTo("default-example"));
        assertThat(links.get(1).getHref(), containsString(exampleEntity.getId().toString()));
    }

    @Test
    void should_get_empty_results_for_unknown_type() {
        var links = resourceExtensionsRegistry.getLinks(ExampleEntity.class, exampleEntity);

        assertThat(links.size(), equalTo(0));
    }
}
