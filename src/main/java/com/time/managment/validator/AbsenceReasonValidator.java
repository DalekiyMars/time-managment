package com.time.managment.validator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.time.managment.constants.AbsenceReason;

public class AbsenceReasonValidator implements ConstraintValidator<ValidAbsenceReason, AbsenceReason> {

    @Override
    public boolean isValid(AbsenceReason value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // Или можно разрешить null в зависимости от требований
        }

        // Проверяем, что значение поля присутствует в enum
        return AbsenceReason.isValidReason(value);
    }
}