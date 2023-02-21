package esgi.infra.dto;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CombatDtoTest {
    ValidatorFactory factory = Validation.byDefaultProvider().configure().messageInterpolator(new ParameterMessageInterpolator()).buildValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    public void testCombatDto() {
        CombatDto combatDto = new CombatDto();
        combatDto.setAttackerPlayer(1L);
        combatDto.setDefenderPlayer(2L);
        combatDto.setAttackerHero(3L);
        combatDto.setDefenderHero(4L);
        assertEquals(Long.valueOf(1), combatDto.getAttackerPlayer());
        assertEquals(Long.valueOf(2), combatDto.getDefenderPlayer());
        assertEquals(Long.valueOf(3), combatDto.getAttackerHero());
        assertEquals(Long.valueOf(4), combatDto.getDefenderHero());
    }

    @Test
    public void testCombatDto_NotBlank() {
        CombatDto combatDto = new CombatDto();
        combatDto.setAttackerHero(1L);
        combatDto.setDefenderHero(2L);

        Set<ConstraintViolation<CombatDto>> violations = validator.validate(combatDto);

        assertEquals(2, violations.size());
        for (ConstraintViolation<CombatDto> violation : violations) {
            assertEquals("must not be null", violation.getMessage());
            assertTrue(violation.getPropertyPath().toString().equals("attackerPlayer") ||
                    violation.getPropertyPath().toString().equals("defenderPlayer"));
        }
    }

}
