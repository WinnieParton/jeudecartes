package esgi.infra.service.combat;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.Hero;
import esgi.domain.RaretyType;
import esgi.domain.SpecialityType;
import esgi.infra.repository.CombatRepository;
import esgi.infra.repository.HeroRepository;

@ExtendWith(MockitoExtension.class)
public class RetrieveHeroCombatsServiceTest {
    @Mock
    private CombatRepository combatRepository;

    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private CombatServiceImpl combatService;

    @Test
    public void testVerifyStatusCombat() {
        // given
        Hero attackerHero = new Hero("Attacker", SpecialityType.Tank, RaretyType.commun);
        Hero defenderHero = new Hero("Defender", SpecialityType.Assassin, RaretyType.commun);

        // mock the combat repository
        // CombatRepository combatRepository = mock(CombatRepository.class);
        // when(combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(attackerHero, defenderHero))
        //         .thenReturn(Optional.of(new Combat(attackerHero, defenderHero, 10, 5, 90, 95, "Draw")));
        // when(combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(defenderHero, attackerHero))
        //         .thenReturn(Optional.empty());

        // create the service and set the mocked repository
        CombatServiceImpl service = new CombatServiceImpl();

        // when
        Boolean result = service.verifyStatusCombat(attackerHero, defenderHero);

        // then
        assertTrue(result);
    }

}
