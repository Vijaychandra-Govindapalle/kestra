package org.floworc.core.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.floworc.core.exceptions.InvalidFlowException;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.floworc.core.models.flows.Flow;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class YamlFlowParser {
    private static final ObjectMapper mapper = JacksonMapper.ofYaml();

    private static final Validator validator = Validation.byDefaultProvider()
        .configure()
        .messageInterpolator(new ParameterMessageInterpolator())
        .buildValidatorFactory()
        .getValidator();

    public Flow parse(File file) throws IOException {
        Flow flow = mapper.readValue(file, Flow.class);

        Set<ConstraintViolation<Flow>> violations = validator.validate(flow);

        if (violations.size() > 0) {
            throw InvalidFlowException.of(violations);
        }

        return flow;
    }
}

