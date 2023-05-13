# K.S.C.H. Workflows Backend

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=ksch-workflows_backend&metric=coverage)](https://sonarcloud.io/summary/new_code?id=ksch-workflows_backend)

This repository contains the source code for the K.S.C.H. Workflows backend that offers a RESTful API to browser, desktop, and mobile clients.

## Architecture

This section gives an overview of the code in this repository.

### Technology stack

- Java
- Spring Boot
- MySQL
- Hibernate
- Gradle

### Module structure

The application consists of two kinds of modules:
First, there are business modules that provide the functionality required by the system's users.
Second, there are technical modules that provide a scaffold for the business modules.

The following diagram gives an overview of the project's module structure:

![module structure](docs/img/modules.png)

The "server" module contains the `main` function of the Java application.
It has a dependency on all the other modules so that their JAR files will be on the classpath when the application starts.

The business modules have the prefix "ksch".
In this way, they are easily distinguishable from the technical modules and the directories which contain miscellaneous files.
Each business module contains two nested sub-modules, one with the suffix "api" and one with the suffix "impl".
If one business module uses another, it can only use what's inside the "api" sub-module.

Circular dependencies between the modules are not allowed, i.e. when "ksch-module-b" depends on "ksch-module-a", there cannot be a module dependency the other way round.
However, it may be that "ksch-module-b" needs to do something when something in "ksch-module-a" happens.
In this case, "ksch-module-a" can listen to events published in "ksch-module-b".

Business modules may further depend on sub-modules in the "commons" module for abstract utilities.

### Repository structure

The following list gives an overview of the contents in the project's directories:

- `.github`: definition of the build process
- `docs`: source files for the REST API documentation
- `commons`: shared code between the `ksch-*` domain modules
- `ksch-billing`: domain module for billing related workflows
- `ksch-patient-management`: domain module for patient-related workflows
- `server`: the application's entry point, global configuration, and database migrations

## Development

### Generate test coverage report

```
./gradlew clean check
open ./server/build/reports/jacoco/testCodeCoverageReport/html/index.html
```

### Start with dev profile

With the `dev` profile, the app will run with a mock authorization server which approves any valid [JWT](https://jwt.io/).

```
export SPRING_PROFILES_ACTIVE=dev
./gradlew bootRun
```

### Embedded database

When you start the app with the `bootRun` Gradle task, the app will use an embedded H2 database.
This database can be introspected under the following URL:

http://localhost:8080/h2-console

`jdbc:h2:mem:ksch`

Use `sa` / `password` as login.

### Add license comments

To add license header comments to all Java files which don't have them yet, you
can call the following shell script:

```
./docs/license/add-license-notices.sh
git add . && git commit -m "docs: add license notices"
```

### Problems with module dependencies

If IntelliJ cannot resolve the dependencies between modules, then most likely this can be fixed by resetting
the caches or re-generating the project files.

[File > Invalidate caches]

```
find . -name "*.iml" -or -name "*.ipr" -or -name "*.iws" | xargs rm
./gradlew idea
```

### OpenAPI spec

**OpenAPI preview**

Run the following script to render a preview of the generated API documentation:

```sh
./docs/lib/openapi_preview/openapi_preview.sh
```

**OpenAPI linting**

Run the following script validate the semantics of the OpenAPI specification:

```sh
./docs/lib/openapi_lint/openapi_lint.sh
```

**OpenAPI formatting**

Run the following script to normalize the formatting of the OpenAPI specification:

```sh
./docs/lib/openapi_format/openapi_format.sh
```

**Also see**

- https://ksch-workflows.github.io/architecture/solution-strategy/openapi/index.html
- https://www.jetbrains.com/help/idea/openapi.html
- https://redocly.com/
- https://github.com/thim81/openapi-format

## Maintenance

### Deploy to staging environment

```
gcloud init
./gradlew appengineDeploy
```

See [cloud.google.com](https://cloud.google.com/sdk/docs/install) for Google Cloud CLI installation instructions.

## License

K.S.C.H. Workflows is maintained by [KS-plus e.V.](https://ks-plus.org/en/welcome/),
a non-profit association dedicated to supporting the [Kirpal Sagar](https://kirpal-sagar.org/en/welcome/) project.

It is licensed under the [Apache License Version 2.0](https://github.com/ksch-workflows/ksch-workflows/blob/master/LICENSE).
