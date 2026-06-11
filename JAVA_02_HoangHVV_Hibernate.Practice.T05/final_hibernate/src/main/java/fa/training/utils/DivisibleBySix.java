package fa.training.utils;

import fa.training.utils.customAnnotationValidator.DivisibleBySixValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DivisibleBySixValidator.class)
public @interface DivisibleBySix {
    String message() default "Total seats must be divisible by 6";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
