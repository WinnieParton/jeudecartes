package esgi.infra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.CombatDomain;
import esgi.domain.HeroDomain;
import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import esgi.infra.entity.CombatEntity;
import esgi.infra.entity.HeroEntity;
import esgi.infra.repository.CombatRepository;
import esgi.infra.repository.HeroRepository;
import esgi.infra.service.impl.CombatServiceImpl;

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
        HeroEntity attackerHero = new HeroEntity("Attacker", SpecialityTypeDomain.TANK, RaretyTypeDomain.COMMON);
        HeroEntity defenderHero = new HeroEntity("Defender", SpecialityTypeDomain.ASSASSIN, RaretyTypeDomain.COMMON);
        CombatEntity combat = new CombatEntity(attackerHero, defenderHero);
        when(heroRepository.findById(attackerHero.getId())).thenReturn(Optional.of(attackerHero));
        when(heroRepository.findById(defenderHero.getId())).thenReturn(Optional.of(defenderHero));
        when(combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(attackerHero, defenderHero))
                .thenReturn(Optional.of(combat));

        HeroDomain attackerHeroD = new HeroDomain(attackerHero.getId(), attackerHero.getName(), attackerHero.getNbLifePoints(), 
        attackerHero.getExperience(), attackerHero.getPower(), attackerHero.getArmor(),
        attackerHero.getSpeciality(), attackerHero.getRarity(), attackerHero.getLevel(), attackerHero.isAvailable(),
         attackerHero.isStatus(), attackerHero.getCreatedAt(), attackerHero.getUpdatedAt());

        HeroDomain defenderHeroD = new HeroDomain(defenderHero.getId(), defenderHero.getName(), defenderHero.getNbLifePoints(), 
        defenderHero.getExperience(), defenderHero.getPower(), defenderHero.getArmor(),
        defenderHero.getSpeciality(), defenderHero.getRarity(), defenderHero.getLevel(), defenderHero.isAvailable(),
        defenderHero.isStatus(), defenderHero.getCreatedAt(), defenderHero.getUpdatedAt());
        Optional<CombatDomain> result = combatService.findByHeroCombat(attackerHeroD, defenderHeroD);
        assertTrue(result.isPresent());
        assertEquals(combat, result.get());
    }

}
