package ksch.commons.http;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Component
@SuppressWarnings({"rawtypes", "unchecked"})
public class ResourceExtensionRegistry {

    private final Map<Class, List<LinkRegistryEntry>> entries = new HashMap<>();

    <T> void registerLink(Class<T> cls, Function<T, Link> linkProvider) {
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
                    .map(l -> (Link) l)
                    .collect(toList());
        }
    }

    @RequiredArgsConstructor
    private static class LinkRegistryEntry <T> {
        final Function<T, Link> linkProvider;
    }
}
