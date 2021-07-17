package ksch.commons.http;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class ResourceExtensionsTest {

    private final ResourceExtensionRegistry resourceExtensionRegistry = new ResourceExtensionRegistry();

    private final ExampleEntity exampleEntity = new ExampleEntity(UUID.randomUUID());

    @Test
    public void should_add_link_to_resource_extensions() {
        var resourceExtension = new SingleResourceExtension(resourceExtensionRegistry);

        resourceExtension.init();

        var links = resourceExtensionRegistry.getLinks(ExampleEntity.class, exampleEntity);
        assertThat(links.size(), equalTo(1));
        assertThat(links.get(0).getRel().value(), equalTo("current-example"));
        assertThat(links.get(0).getHref(), containsString(exampleEntity.getId().toString()));
    }

    @Test
    public void should_two_links_to_resource_extensions() {
        var resourceExtensions = new TwoResourceExtensions(resourceExtensionRegistry);

        resourceExtensions.init();

        var links = resourceExtensionRegistry.getLinks(ExampleEntity.class, exampleEntity);
        assertThat(links.size(), equalTo(2));
        assertThat(links.get(0).getRel().value(), equalTo("current-example"));
        assertThat(links.get(0).getHref(), containsString(exampleEntity.getId().toString()));
        assertThat(links.get(1).getRel().value(), equalTo("default-example"));
        assertThat(links.get(1).getHref(), containsString(exampleEntity.getId().toString()));
    }

    @Test
    public void should_get_empty_results_for_unknown_type() {
        var links = resourceExtensionRegistry.getLinks(ExampleEntity.class, exampleEntity);

        assertThat(links.size(), equalTo(0));
    }
}
