package bg.softuni.eliteSportsEquipment.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = FileNotEmptyValidator.class)
public @interface FileNotEmpty {

    String message() default "Picture is required!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
