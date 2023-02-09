package esgi.infra.dto;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PlayerAddDtoTest {
    @InjectMocks
    private PlayerAddDto playerAddDto;

    ValidatorFactory factory = Validation.byDefaultProvider().configure().messageInterpolator(new ParameterMessageInterpolator()).buildValidatorFactory();

     Validator validator = factory.getValidator();



    @Test
    public void testValidPseudo() {
        playerAddDto.setPseudo("John");
        assertEquals(0, validator.validate(playerAddDto).size());
    }

    @Test
    public void testInvalidPseudoTooShort() {
        playerAddDto.setPseudo("Jo");
        assertEquals(1, validator.validate(playerAddDto).size());
    }

    @Test
    public void testInvalidPseudoTooLong() {
        playerAddDto.setPseudo("John123456789012345678901234567890");
        assertEquals(1, validator.validate(playerAddDto).size());
    }
}
