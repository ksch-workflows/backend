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
