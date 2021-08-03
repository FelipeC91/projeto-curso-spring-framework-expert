package br.com.personalportifolio.brewer.validation.validator;

import br.com.personalportifolio.brewer.validation.PasswordComparator;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

public class PasswordComparatorValidator implements ConstraintValidator<PasswordComparator, Object> {

    private String comparable;
    private String otherComparable;

    @Override
    public void initialize(PasswordComparator constraintAnnotation) {
        this.comparable = constraintAnnotation.comparable();
        this.otherComparable = constraintAnnotation.otherComparable();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean isValid = false;
        try {
            var comparableProperty= BeanUtils.getProperty(object, this.comparable);
            var otherComparableProperty= BeanUtils.getProperty(object, this.otherComparable);

            isValid = (comparableProperty == null && otherComparableProperty == null)
                        || (comparableProperty != null && comparableProperty.equals(otherComparableProperty))
                        ?  true : false;

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Erro ao recuperar valores nos atributos a serem comparados", e);
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();

            var message = context.getDefaultConstraintMessageTemplate();
            var validationBuilder = context.buildConstraintViolationWithTemplate(message);
            validationBuilder.addPropertyNode(otherComparable).addConstraintViolation();
        }

        return isValid;
    }
}
