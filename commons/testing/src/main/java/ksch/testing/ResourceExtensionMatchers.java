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
