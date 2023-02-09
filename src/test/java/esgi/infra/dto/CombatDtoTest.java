package esgi.infra.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CombatDtoTest {
    @Test
    public void testCombatDto() {
        CombatDto combatDto = new CombatDto();
        combatDto.setAttackerPlayer(1L);
        combatDto.setDefenderPlayer(2L);
        combatDto.setAttackerHero(3L);
        combatDto.setDefenderHero(4L);
        assertEquals(new Long(1), combatDto.getAttackerPlayer());
        assertEquals(new Long(2), combatDto.getDefenderPlayer());
        assertEquals(new Long(3), combatDto.getAttackerHero());
        assertEquals(new Long(4), combatDto.getDefenderHero());
    }

    @Test
    public void testCombatDto_NotBlank() {
        CombatDto combatDto = new CombatDto();
        assertNotNull(combatDto.getAttackerPlayer());
        assertNotNull(combatDto.getDefenderPlayer());
        assertNotNull(combatDto.getAttackerHero());
        assertNotNull(combatDto.getDefenderHero());
    }
}
