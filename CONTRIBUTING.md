# Contributing

This file contains guideslines for making contributions in the `backend` repository of the KSCH Workflows project.

## Testing

- The test coverage should be at least 80%, better more.
    - As a rule of thumb, every Java class which contains business logic should have a corresponding test class.
- Use the [Arrange / Act / Assert](http://wiki.c2.com/?ArrangeActAssert) pattern for structuring the tests
- Use JUnit5 as test runner.
- Prefer usage of [Hamcrest](http://hamcrest.org/JavaHamcrest/tutorial) for the test assertions.
- Use [Wiremock](https://wiremock.org/docs/junit-jupiter/) for testing HTTP calls.
- Use [Mockito](https://stackoverflow.com/a/40962941/2339010) to create mocks and stubs for unit tests.
