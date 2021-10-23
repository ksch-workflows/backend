package ksch.testing;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.hateoas.Link;

import java.util.List;

@NoArgsConstructor
public class ResourceExtensionMatchers {

    public static Matcher<List<Link>> containsLinkWithRel(String rel) {
        return new ContainsRel(rel);
    }

    public static Matcher<List<Link>> containsNoLinkWithRel(String rel) {
        return new ContainsNoRel(rel);
    }

    @RequiredArgsConstructor
    private static class ContainsRel extends TypeSafeMatcher<List<Link>> {

        private final String rel;

        @Override
        protected boolean matchesSafely(List<Link> item) {
            return item.stream().anyMatch(link -> rel.equals(link.getRel().value()));
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Link with relation '" + rel + "'");
        }

        @Override
        public void describeMismatchSafely(List<Link> item, Description mismatchDescription) {
            if (item.isEmpty()) {
                mismatchDescription.appendValue("no links present");
            } else {
                mismatchDescription.appendText("found only links ");
                item.stream().map(link -> link.getRel().value()).forEach(mismatchDescription::appendValue);
            }
        }
    }

    @RequiredArgsConstructor
    private static class ContainsNoRel extends TypeSafeMatcher<List<Link>> {

        private final String rel;

        @Override
        protected boolean matchesSafely(List<Link> item) {
            return item.stream().noneMatch(link -> rel.equals(link.getRel().value()));
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("No link with relation '" + rel + "'");
        }
    }
}
