package ksch.linkregistry;


import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

// TODO Maybe this can be moved to the "server" module
@SuppressWarnings("rawtypes")
@Service
public class LinkRegistry {

    private Map<Class, List<LinkRegistryEntry>> entries = new HashMap<>();

    public <T> void registerLink(Class<T> cls, String relation, Function<T, Link> linkProvider) {
        if (entries.containsKey(cls)) {
            var links = entries.get(cls);
            var entry = new LinkRegistryEntry(relation, linkProvider);
            if (links.contains(entry)) {
                var msg = String.format("There is already a link with relation '%s' registered from class '%s'.",
                        relation, cls
                );
                throw new IllegalStateException(msg);
            } else {
                links.add(entry);
            }
        } else {
            var links = new ArrayList<LinkRegistryEntry>();
            links.add(new LinkRegistryEntry(relation, linkProvider));
            entries.put(cls, links);
        }
    }

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
    @EqualsAndHashCode(of = "relation")
    private static class LinkRegistryEntry <T> {
        final String relation;
        final Function<T, Link> linkProvider;
    }
}
