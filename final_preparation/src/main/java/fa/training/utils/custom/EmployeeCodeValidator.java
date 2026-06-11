import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeCodeValidator
        implements ConstraintValidator<EmployeeCode, String> {

    @Override
    public boolean isValid(String value,
                           ConstraintValidatorContext context) {

        if (value == null) {
            return true; // let @NotNull handle null checks
        }

        return value.startsWith("EMP-");
    }
}