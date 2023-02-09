package esgi.infra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
public class RetrieveHeroCombatsServiceTest {
    @Mock
    private CombatRepository combatRepository;

    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private CombatServiceImpl combatService;

    @Test
    public void testRetrieveHeroCombats() {
        HeroDomain  hero = new HeroDomain( 1L,"Superman", 100, 1000, 
        500, 300, SpecialityTypeDomain.TANK, RaretyTypeDomain.LEGENDARY, 1, 
        true, true, null, null);
        
        HeroEntity  heroEntity = new HeroEntity(hero.getId(), hero.getName(), hero.getNbLifePoints(), hero.getExperience(),
            hero.getPower(), hero.getArmor(), hero.getSpeciality(), hero.getRarity(), hero.getLevel(), hero.isAvailable(),
            hero.isStatus(), hero.getCreatedAt(), hero.getUpdatedAt());
        CombatEntity combatEntity = new CombatEntity( heroEntity, heroEntity);
        combatEntity.setResult("Draw");
        List<CombatEntity> combatEntities = new ArrayList<>();
        combatEntities.add(combatEntity);

        when(combatRepository.findByAttackingHeroOrDefendingHero(heroEntity, heroEntity)).thenReturn(combatEntities);
        List<CombatDomain> result = combatService.retrieveHeroCombats(hero);
    
        assertEquals(1, result.size());
        assertEquals(hero, result.get(0).getAttackingHero());
        assertEquals(hero, result.get(0).getDefendingHero());
        assertEquals("Draw", result.get(0).getResult());
    }
}
