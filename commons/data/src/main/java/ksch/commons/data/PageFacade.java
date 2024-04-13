/*
 * Copyright 2024 KS-plus e.V.
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
package ksch.commons.data;

import org.springframework.data.domain.Page;

/**
 * Allows the usage of the {@link Page} interface from Spring Data Commons without a direct dependency on that external
 * package.
 */
public interface PageFacade<T> extends Page<T> {}
