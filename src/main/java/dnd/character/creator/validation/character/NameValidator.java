package dnd.character.creator.validation.character;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {

    private int maxLength;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null &&
                !value.isBlank() &&
                value.length() > 2 &&
                value.length() <= maxLength;
    }

    @Override
    public void initialize(Name constraintAnnotation) {
        maxLength = constraintAnnotation.maxLength();
    }
}
