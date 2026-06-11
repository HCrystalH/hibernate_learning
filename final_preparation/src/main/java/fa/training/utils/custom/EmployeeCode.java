package fa.training.utils.custom;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmployeeCodeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeCode {

    String message() default "Invalid employee code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}