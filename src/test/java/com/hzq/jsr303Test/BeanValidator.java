package com.hzq.jsr303Test;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

/**
 * BeanValidator
 * Created by hzq on 16/9/6.
 */
@Component
public class BeanValidator{

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static Validator validator;
    static {
        validator = factory.getValidator();
    }
    public static void validBean(Object object) {
        Set<ConstraintViolation<Object>> validate = validator.validate(object);
        Optional<ConstraintViolation<Object>> first = validate.stream().findFirst();
        if (first.isPresent())
            throw new RuntimeException(first.get().getMessage());
    }
}
