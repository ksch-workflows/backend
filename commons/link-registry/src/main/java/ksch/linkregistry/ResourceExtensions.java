package ksch.linkregistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.hateoas.Link;

import java.util.function.Function;

public abstract class ResourceExtensions implements ApplicationRunner {

    @Autowired
    private ResourceExtensionRegistry resourceExtensionRegistry;

    @Override
    public void run(ApplicationArguments args) {
        init();
    }

    /**
     * Use this method to register the resource extensions.
     *
     * @see #registerLink(Class, Function)
     */
    protected abstract void init();

    public  <T> void registerLink(Class<T> cls, Function<T, Link> linkProvider) {
        resourceExtensionRegistry.registerLink(cls, linkProvider);
    }
}
