package esgi.infra.service.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
public class FindByHeroCombatServiceTest {
    @Mock
    private CombatRepository combatRepository;

    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private CombatServiceImpl combatService;

    @Test
    public void testFindByHeroCombat() {
        Hero attackerHero = new Hero("Attacker", SpecialityType.Tank, RaretyType.commun);
        Hero defenderHero = new Hero("Defender", SpecialityType.Assassin, RaretyType.commun);
        Combat combat = new Combat(attackerHero, defenderHero, 10, 5, 90, 95, "Draw");
        when(heroRepository.findById(attackerHero.getId())).thenReturn(Optional.of(attackerHero));
        when(heroRepository.findById(defenderHero.getId())).thenReturn(Optional.of(defenderHero));
        when(combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(attackerHero, defenderHero))
                .thenReturn(Optional.of(combat));
        Optional<Combat> result = combatService.findByHeroCombat(attackerHero, defenderHero);
        assertTrue(result.isPresent());
        assertEquals(combat, result.get());
    }

}
