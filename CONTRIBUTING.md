# Contribution guidelines

This file contains guideslines for making contributions in the `backend` repository of the KSCH Workflows project.

## Contributors

This project accepts contributions only from people inside the [KSCH Workflows](https://github.com/ksch-workflows) organisation.

## Testing

- The test coverage should be at least 80%, better more.
  - As a rule of thumb, every Java class which contains business logic should have a corresponding test class.
- Use the [Arrange / Act / Assert](http://wiki.c2.com/?ArrangeActAssert) pattern for structuring the tests
- Use JUnit5 as test runner.
- Prefer usage of [Hamcrest](http://hamcrest.org/JavaHamcrest/tutorial) for the test assertions.
- Use [Wiremock](https://wiremock.org/docs/junit-jupiter/) for testing HTTP calls.
- Use [Mockito](https://stackoverflow.com/a/40962941/2339010) to create mocks and stubs for unit tests.

## Commit messages

The Git commit messages should follow the [Conventional Commits guideslines](https://www.conventionalcommits.org/en/v1.0.0/#summary).

There are no automatic validations of those guidelines in place, but you may get asked to split up a pull request which contains changes of different types.

## Licensing

The source code in the repository is licensed under the Apache License, Version 2.0.
So, by creating a pull request, you agree that this license applies to your changes as well.

All Java files should contain the following license header:

```
/*
 * Copyright 2022 KS-plus e.V.
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
```
