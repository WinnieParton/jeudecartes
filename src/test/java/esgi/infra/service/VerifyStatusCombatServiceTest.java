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
    public void verifyStatusCombatTest() {
        HeroDomain attackerHero = new HeroDomain(1L, "Attacker", 100, 0, 10, 5,
                SpecialityTypeDomain.MAGE, RaretyTypeDomain.COMMON, 1, true, true, null, null);
        HeroEntity attackerHeroEntity = new HeroEntity(attackerHero.getId(), attackerHero.getName(),
                attackerHero.getNbLifePoints(), attackerHero.getExperience(), attackerHero.getPower(),
                attackerHero.getArmor(), attackerHero.getSpeciality(), attackerHero.getRarity(),
                attackerHero.getLevel(), attackerHero.isAvailable(), attackerHero.isStatus(),
                attackerHero.getCreatedAt(), attackerHero.getUpdatedAt());

        HeroDomain defenderHero = new HeroDomain(2L, "Defender", 100, 0, 10, 5,
                SpecialityTypeDomain.MAGE, RaretyTypeDomain.COMMON, 1, true, true, null, null);
        HeroEntity defenderHeroEntity = new HeroEntity(defenderHero.getId(), defenderHero.getName(),
                defenderHero.getNbLifePoints(), defenderHero.getExperience(), defenderHero.getPower(),
                defenderHero.getArmor(), defenderHero.getSpeciality(), defenderHero.getRarity(),
                defenderHero.getLevel(), defenderHero.isAvailable(), defenderHero.isStatus(),
                defenderHero.getCreatedAt(), defenderHero.getUpdatedAt());

        when(heroRepository.findById(attackerHero.getId()))
                .thenReturn(Optional.of(attackerHeroEntity));
        when(heroRepository.findById(defenderHero.getId()))
                .thenReturn(Optional.of(defenderHeroEntity));

        // Draw result
        CombatEntity drawCombat = new CombatEntity(attackerHeroEntity, defenderHeroEntity);
        drawCombat.setResult("Draw");
        when(combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(attackerHeroEntity, defenderHeroEntity))
                .thenReturn(Optional.of(drawCombat));
        when(combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(defenderHeroEntity, attackerHeroEntity))
                .thenReturn(Optional.of(drawCombat));

        assertTrue(combatService.verifyStatusCombat(attackerHero,defenderHero));

    }

}
