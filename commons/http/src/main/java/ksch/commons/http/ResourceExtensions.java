package ksch.commons.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Function;

public abstract class ResourceExtensions {

    @Autowired
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
    protected <T> void registerLink(Class<T> cls, Function<T, Link> linkProvider) {
        resourceExtensionRegistry.registerLink(cls, linkProvider);
    }

    /**
     * @return the links which should be added to a resource for the provided entity.
     */
    public <T> List<Link> getLinks(Class<T> cls, T entity) {
        return resourceExtensionRegistry.getLinks(cls, entity);
    }
}
