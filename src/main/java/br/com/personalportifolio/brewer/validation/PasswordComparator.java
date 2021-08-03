package br.com.personalportifolio.brewer.validation;

import br.com.personalportifolio.brewer.validation.validator.PasswordComparatorValidator;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PasswordComparatorValidator.class})
public @interface PasswordComparator {

    @OverridesAttribute(constraint = Pattern.class, name = "message")
    String message() default "Padrões nao conferem";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default  {};

    String comparable();

    String otherComparable();
}
