package esgi.infra.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;

@ExtendWith(MockitoExtension.class)
public class HerosAddDtoTest {
    @InjectMocks
    private HerosAddDto herosAddDto;

    @Mock
    private SpecialityTypeDomain speciality;

    @Mock
    private RaretyTypeDomain rarety;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void givenName_whenTooShort_thenViolation() {
        herosAddDto.setName("ab");
        herosAddDto.setSpeciality(speciality);
        herosAddDto.setRarety(rarety);

        Set<ConstraintViolation<HerosAddDto>> violations = validator.validate(herosAddDto);
        assertEquals(1, violations.size());
        assertEquals("size must be between 3 and 50", violations.iterator().next().getMessage());
    }

    @Test
    public void givenName_whenTooLong_thenViolation() {
        herosAddDto.setName("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz");
        herosAddDto.setSpeciality(speciality);
        herosAddDto.setRarety(rarety);

        Set<ConstraintViolation<HerosAddDto>> violations = validator.validate(herosAddDto);
        assertEquals(1, violations.size());
        assertEquals("size must be between 3 and 50", violations.iterator().next().getMessage());
    }

    @Test
    public void givenName_whenValid_thenNoViolation() {
        herosAddDto.setName("validName");
        herosAddDto.setSpeciality(speciality);
        herosAddDto.setRarety(rarety);

        Set<ConstraintViolation<HerosAddDto>> violations = validator.validate(herosAddDto);
        assertEquals(0, violations.size());
    }

}