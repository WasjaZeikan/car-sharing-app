package mate.academy.carsharing.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch,Object> {
    private String fieldName;
    private String matchedFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
        matchedFieldName = constraintAnnotation.matchedFieldName();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object fieldValue = field.get(o);
            Field matchedField = o.getClass().getDeclaredField(matchedFieldName);
            matchedField.setAccessible(true);
            Object matchedFieldValue = matchedField.get(o);
            return Objects.equals(fieldValue, matchedFieldValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }
}
