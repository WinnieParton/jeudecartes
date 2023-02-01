package esgi.infra.service.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.Combat;
import esgi.domain.Hero;
import esgi.domain.RaretyType;
import esgi.domain.SpecialityType;
import esgi.infra.repository.CombatRepository;
import esgi.infra.repository.HeroRepository;

@ExtendWith(MockitoExtension.class)
public class EngageCombatServiceTest {

    @Mock
    private CombatRepository combatRepository;

    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private CombatServiceImpl combatService;

    @Test
    public void testEngageCombat() {
        // Given
        Hero attackerHero = new Hero("Attacker", SpecialityType.Tank, RaretyType.commun);
        Hero defenderHero = new Hero("Defender", SpecialityType.Assassin, RaretyType.commun);
        when(heroRepository.findById(attackerHero.getId())).thenReturn(Optional.of(attackerHero));
        when(heroRepository.findById(defenderHero.getId())).thenReturn(Optional.of(defenderHero));

        // When
        Combat combat = combatService.engageCombat(attackerHero, defenderHero);

        // Then
        assertEquals(attackerHero, combat.getAttackingHero());
        assertEquals(defenderHero, combat.getDefendingHero());
        assertEquals(10, combat.getDamageAttackerHero());
        assertEquals(5, combat.getDamageDefenderHero());
        assertEquals(90, combat.getNewLifePointsAttacker());
        assertEquals(75, combat.getNewLifePointsDefender());
        assertEquals("Attacker wins", combat.getResult());
        verify(heroRepository, times(1)).save(attackerHero);
        verify(heroRepository, times(1)).save(defenderHero);
        verify(combatRepository, times(1)).save(combat);
    }
}
