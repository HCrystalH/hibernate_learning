package fa.training.utils.customAnnotationValidator;

import fa.training.utils.ModelConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ModelValidator implements ConstraintValidator<ModelConstraint, String> {
    private static final String MODEL_PATTERN = "^[A-Z0-9]{6}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        return value.matches(MODEL_PATTERN);
    }
}
