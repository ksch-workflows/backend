package ksch.patientmanagement.http;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.report.LevelResolver;
import com.atlassian.oai.validator.report.ValidationReport;

import io.swagger.v3.parser.core.models.ParseOptions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
