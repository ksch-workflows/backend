package ksch.commons.http;

import org.springframework.hateoas.Link;

public class TwoResourceExtensions extends ResourceExtensions {

    public TwoResourceExtensions(ResourceExtensionRegistry resourceExtensionRegistry) {
        setResourceExtensionRegistry(resourceExtensionRegistry);
    }

    @Override
    protected void init() {
        registerLink(
                ExampleEntity.class,
                exampleEntity -> Link.of("http://localhost/examples/" + exampleEntity.getId(), "current-example")
        );
        registerLink(
                ExampleEntity.class,
                exampleEntity -> Link.of("http://localhost/examples/" + exampleEntity.getId(), "default-example")
        );
    }
}
