package bg.softuni.eliteSportsEquipment.model.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileNotEmptyValidator implements ConstraintValidator<FileNotEmpty, MultipartFile> {


    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return (value.getSize() > 0);
    }
}
