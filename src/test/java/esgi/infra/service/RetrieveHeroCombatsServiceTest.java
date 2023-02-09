package esgi.infra.service;

import esgi.domain.CombatDomain;
import esgi.domain.HeroDomain;
import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import esgi.infra.entity.CombatEntity;
import esgi.infra.entity.HeroEntity;
import esgi.infra.repository.CombatHistoryRepository;
import esgi.infra.repository.CombatRepository;
import esgi.infra.repository.HeroRepository;
import esgi.infra.service.impl.CombatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RetrieveHeroCombatsServiceTest {
    @Mock
    private CombatRepository combatRepository;

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private CombatHistoryRepository combatHistoryRepository;

    @InjectMocks
    private CombatServiceImpl combatService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        combatService = new CombatServiceImpl(heroRepository, combatRepository,combatHistoryRepository);
    }

    @Test
    public void retrieveHeroCombatsTest() {
        // Prepare the data
        HeroDomain hero = new HeroDomain(1L, "Test Hero", 100, 100, 100, 100,
                SpecialityTypeDomain.MAGE, RaretyTypeDomain.LEGENDARY, 1, true, true, null, null);
        HeroEntity heroEntity = new HeroEntity(hero.getId(), hero.getName(), hero.getNbLifePoints(),
                hero.getExperience(), hero.getPower(), hero.getArmor(), hero.getSpeciality(),
                hero.getRarity(), hero.getLevel(), hero.isAvailable(), hero.isStatus(),
                hero.getCreatedAt(), hero.getUpdatedAt());
        HeroEntity attackingHero = new HeroEntity(2L, "Attacker Hero", 80, 80, 80, 80,
                SpecialityTypeDomain.TANK, RaretyTypeDomain.COMMON, 1, true, true, null, null);
        HeroEntity defenderHero = new HeroEntity(3L, "Defender Hero", 60, 60, 60, 60,
                SpecialityTypeDomain.ASSASSIN, RaretyTypeDomain.RARE, 1, true, true, null, null);
        CombatEntity combatEntity = new CombatEntity( attackingHero, defenderHero);
        combatEntity.setResult("Win");
        List<CombatEntity> combatEntities = new ArrayList<>();
        combatEntities.add(combatEntity);

        // Stub the behavior of combatRepository
        when(combatRepository.findByAttackingHeroOrDefendingHero(any(HeroEntity.class), any(HeroEntity.class)))
                .thenReturn(combatEntities);

        // Call the method under test
        List<CombatDomain> result = combatService.retrieveHeroCombats(hero);

        // Verify the result
        assertEquals(1, result.size());
        CombatDomain combat = result.get(0);
        assertEquals(combatEntity.getId(), combat.getId());
        assertEquals(attackingHero.getName(), combat.getAttackingHero().getName());
        assertEquals(defenderHero.getName(), combat.getDefendingHero().getName());
        assertEquals(combatEntity.getResult(), combat.getResult());
    }

}
