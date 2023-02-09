package esgi.infra.service;

import esgi.domain.CombatDomain;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        HeroDomain atHero = new HeroDomain(1L, "Attacker", 100, 0, 10, 10, SpecialityTypeDomain.ASSASSIN, RaretyTypeDomain.COMMON, 1, true, true, null, null);
        HeroDomain defHero = new HeroDomain(2L, "Defender", 90, 0, 8, 12, SpecialityTypeDomain.TANK, RaretyTypeDomain.LEGENDARY, 1, true, true, null, null);

        HeroEntity attackerHero = new HeroEntity(1L, "Attacker", 100, 0, 10, 10, SpecialityTypeDomain.TANK, RaretyTypeDomain.COMMON, 1, true, true, null, null);
        HeroEntity defenderHero = new HeroEntity(2L, "Defender", 90, 0, 8, 12, SpecialityTypeDomain.ASSASSIN, RaretyTypeDomain.LEGENDARY, 1, true, true, null, null);

        CombatEntity combatEntity = new CombatEntity( attackerHero, defenderHero);
        combatEntity.setResult("Draw");
        Optional<CombatEntity> optionalCombatEntity = Optional.of(combatEntity);


        when(heroRepository.findById(atHero.getId())).thenReturn(Optional.of(attackerHero));
        when(heroRepository.findById(defHero.getId())).thenReturn(Optional.of(defenderHero));
        when(combatRepository.findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(attackerHero, defenderHero)).thenReturn(optionalCombatEntity);

        Optional<CombatDomain> result = combatService.findByHeroCombat(atHero, defHero);

        assertTrue(result.isPresent());
        assertEquals(result.get().getId(), combatEntity.getId());
        assertEquals(result.get().getResult(), combatEntity.getResult());
        assertEquals(result.get().getAttackingHero().getId(), attackerHero.getId());
        assertEquals(result.get().getDefendingHero().getId(), defenderHero.getId());

        verify(heroRepository).findById(atHero.getId());
        verify(heroRepository).findById(defHero.getId());
        verify(combatRepository).findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(attackerHero, defenderHero);
    }

}
