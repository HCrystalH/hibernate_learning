package fa.training.utils;

import fa.training.utils.customAnnotationValidator.ModelValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ModelValidator.class)
public @interface ModelConstraint {
    String message() default "Model must be 6 UPPERCASE characters, excluding special characters: *, #, &";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
