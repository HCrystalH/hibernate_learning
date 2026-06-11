package fa.training.utils.customAnnotationValidator;

import fa.training.utils.DivisibleBySix;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DivisibleBySixValidator implements ConstraintValidator<DivisibleBySix, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return false;

        return (value > 0 && value % 6 == 0);
    }
}
