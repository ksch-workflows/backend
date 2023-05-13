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
package ksch.testing;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.report.LevelResolver;
import com.atlassian.oai.validator.report.ValidationReport;

import io.swagger.v3.parser.core.models.ParseOptions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Creates pre-configured instances of {@link OpenApiInteractionValidator}.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OasValidatorFactory {

    public static OpenApiInteractionValidator createValidator(String specificationUrl) {
        LevelResolver levelResolver = LevelResolver.create()
            .withLevel("validation.schema.additionalProperties", ValidationReport.Level.IGNORE)
            .build();
        ParseOptions parseOptions = new ParseOptions();

        parseOptions.setResolve(true);
        parseOptions.setResolveFully(false);
        parseOptions.setResolveCombinators(true);

        return OpenApiInteractionValidator
            .createForSpecificationUrl(specificationUrl)
            .withLevelResolver(levelResolver)
            .withParseOptions(parseOptions)
            .build();
    }
}
