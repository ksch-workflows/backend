package ksch.commons.http;

import org.springframework.hateoas.Link;

public class SingleResourceExtension extends ResourceExtensions {

    public SingleResourceExtension(ResourceExtensionRegistry resourceExtensionRegistry) {
        setResourceExtensionRegistry(resourceExtensionRegistry);
    }

    @Override
    protected void init() {
        registerLink(
                ExampleEntity.class,
                exampleEntity -> Link.of("http://localhost/examples/" + exampleEntity.getId(), "current-example")
        );
    }
}
