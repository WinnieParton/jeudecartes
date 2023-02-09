package esgi.infra.dto;

import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HerosAddDtoTest {
    @InjectMocks
    private HerosAddDto herosAddDto;

    ValidatorFactory factory = Validation.byDefaultProvider().configure().messageInterpolator(new ParameterMessageInterpolator()).buildValidatorFactory();
    Validator validator = factory.getValidator();
    @org.junit.Test(expected = NullPointerException.class)
    public void whenNameIsBlank_thenConstraintViolationException() {
        herosAddDto.setName("");
        herosAddDto.setSpeciality(SpecialityTypeDomain.TANK);
        herosAddDto.setRarety(RaretyTypeDomain.COMMON);

        Set<ConstraintViolation<HerosAddDto>> violations = validator.validate(herosAddDto);
        assertEquals(1, violations.size());

        throw new ConstraintViolationException(violations);
    }

    @Test(expected = NullPointerException.class)
    public void whenSpecialityIsNull_thenConstraintViolationException() {
        herosAddDto.setName("Name");
        herosAddDto.setSpeciality(null);
        herosAddDto.setRarety(RaretyTypeDomain.COMMON);

        Set<ConstraintViolation<HerosAddDto>> violations = validator.validate(herosAddDto);
        assertEquals(1, violations.size());

        throw new ConstraintViolationException(violations);
    }

    @org.junit.Test(expected = NullPointerException.class)
    public void whenRaretyIsNull_thenConstraintViolationException() {
        herosAddDto.setName("Name");
        herosAddDto.setSpeciality(SpecialityTypeDomain.TANK);
        herosAddDto.setRarety(null);

        Set<ConstraintViolation<HerosAddDto>> violations = validator.validate(herosAddDto);
        assertEquals(1, violations.size());

        throw new ConstraintViolationException(violations);
    }
}