package esgi.infra.service;

import esgi.domain.HeroDomain;
import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import esgi.infra.entity.CombatEntity;
import esgi.infra.entity.HeroEntity;
import esgi.infra.repository.CombatRepository;
import esgi.infra.repository.HeroRepository;
import esgi.infra.service.impl.CombatServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VerifyStatusCombatServiceTest {
    @Mock
    private CombatRepository combatRepository;

    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private CombatServiceImpl combatService;

    @Test
    public void testVerifyStatusCombat() {
        // given
        HeroEntity attackerHero = new HeroEntity("Attacker", SpecialityTypeDomain.TANK, RaretyTypeDomain.COMMON);
        HeroEntity defenderHero = new HeroEntity("Defender", SpecialityTypeDomain.ASSASSIN, RaretyTypeDomain.COMMON);

        // mock the combat repository
        CombatRepository combatRepository = mock(CombatRepository.class);
        when(combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(attackerHero, defenderHero))
                .thenReturn(Optional.of(new CombatEntity(attackerHero, defenderHero)));
        when(combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(defenderHero, attackerHero))
                .thenReturn(Optional.empty());
        HeroDomain attackerHeroD = new HeroDomain(attackerHero.getId(), attackerHero.getName(),
                attackerHero.getNbLifePoints(),
                attackerHero.getExperience(), attackerHero.getPower(), attackerHero.getArmor(),
                attackerHero.getSpeciality(), attackerHero.getRarity(), attackerHero.getLevel(),
                attackerHero.isAvailable(),
                attackerHero.isStatus(), attackerHero.getCreatedAt(), attackerHero.getUpdatedAt());

        HeroDomain defenderHeroD = new HeroDomain(defenderHero.getId(), defenderHero.getName(),
                defenderHero.getNbLifePoints(),
                defenderHero.getExperience(), defenderHero.getPower(), defenderHero.getArmor(),
                defenderHero.getSpeciality(), defenderHero.getRarity(), defenderHero.getLevel(),
                defenderHero.isAvailable(),
                defenderHero.isStatus(), defenderHero.getCreatedAt(), defenderHero.getUpdatedAt());
        // when
        Boolean result = combatService.verifyStatusCombat(attackerHeroD, defenderHeroD);

        // then
        assertTrue(result);
    }

}
