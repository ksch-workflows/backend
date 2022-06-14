# Contribution guidelines

## Branch name

Use the date of the branch creation as prefix for the branch name, e.g.

```
git checkout -b 2022-06-14/branch-name
```

## Creating pull requests

For security reasons, pull requests need to be created from branches on the ksch-workflows/backend repository,
i.e. they can only be created from people with write permissions for the project.

**References**

- https://securitylab.github.com/research/github-actions-preventing-pwn-requests
- https://github.blog/2020-08-03-github-actions-improvements-for-fork-and-pull-request-workflows

## Commit messages

The Git commit messages should follow the [Conventional Commits guideslines](https://www.conventionalcommits.org/en/v1.0.0/#summary).

There are no automatic validations of those guidelines in place, but you may get asked to split up a pull request which contains changes of different types.

## Licensing

The source code in the repository is licensed under the Apache License, Version 2.0.
So, by creating a pull request, you agree that this license applies to your changes as well.

All Java files should contain the following license header:

```
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
```
