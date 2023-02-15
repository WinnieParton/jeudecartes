package esgi.infra.service;

import esgi.domain.HeroDomain;
import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import esgi.infra.entity.CombatEntity;
import esgi.infra.entity.CombatHistoryEntity;
import esgi.infra.entity.HeroEntity;
import esgi.infra.repository.CombatHistoryRepository;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EngageCombatServiceTest {

    @InjectMocks
    private CombatServiceImpl combatService;

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private CombatRepository combatRepository;

    @Mock
    private CombatHistoryRepository combatHistoryRepository;

    @Test
    public void testIsAlive() {
        HeroEntity attacker = new HeroEntity("Attacker", SpecialityTypeDomain.MAGE, RaretyTypeDomain.COMMON);

        when(heroRepository.findById(1L)).thenReturn(Optional.of(attacker));

        boolean result = combatService.isAlive(1L);

        assertFalse(result);
    }

    @Test
    public void testCalculateDamage() {
        HeroEntity attacker = new HeroEntity("Attacker", SpecialityTypeDomain.MAGE, RaretyTypeDomain.COMMON);
        HeroEntity defender = new HeroEntity("Defender", SpecialityTypeDomain.TANK, RaretyTypeDomain.LEGENDARY);
        attacker.setPower(10);
        defender.setPower(30);
        attacker.setArmor(20);
        defender.setArmor(5);
        int damage = combatService.calculateDamage(attacker, defender);
        assertEquals(5, damage);

        attacker.setSpeciality(SpecialityTypeDomain.ASSASSIN);
        int damage1 = combatService.calculateDamage(attacker, defender);
        assertEquals(35, damage1);

        attacker.setSpeciality(SpecialityTypeDomain.TANK);
        defender.setSpeciality(SpecialityTypeDomain.MAGE);
        int damage2 = combatService.calculateDamage(attacker, defender);
        assertEquals(25, damage2);

        attacker.setSpeciality(SpecialityTypeDomain.MAGE);
        defender.setSpeciality(SpecialityTypeDomain.ASSASSIN);
        int damage3 = combatService.calculateDamage(attacker, defender);
        assertEquals(30, damage3);
    }

    @Test
    public void testAttack() {
        Long HERO_ID = 1L;
        Long ENEMY_ID = 2L;
        HeroEntity hero = new HeroEntity();
        hero.setId(1L);
        hero.setPower(10);
        hero.setArmor(5);
        hero.setSpeciality(SpecialityTypeDomain.MAGE);
        hero.setNbLifePoints(100);

        HeroEntity enemy = new HeroEntity();
        enemy.setId(2L);
        enemy.setPower(5);
        enemy.setArmor(7);
        enemy.setSpeciality(SpecialityTypeDomain.TANK);
        enemy.setNbLifePoints(90);

        lenient().when(heroRepository.findById(HERO_ID)).thenReturn(Optional.of(hero));
        lenient().when(heroRepository.findById(ENEMY_ID)).thenReturn(Optional.of(enemy));

        int damage = combatService.attack(hero, enemy);
        assertEquals(3, damage);
        assertEquals(87, enemy.getNbLifePoints());
    }

    @Test
    public void testAddExperiencePoints() {
        // Create a mock HeroEntity with some initial experience
        HeroEntity hero = new HeroEntity();
        hero.setExperience(10);
        hero.setNbLifePoints(10);
        hero.setPower(10);
        // Call the method to add experience points
        combatService.addExperiencePoints(hero, 5);

        // Verify that the HeroEntity was updated with the correct experience points and level
        assertEquals(15, hero.getExperience());
        assertEquals(2, hero.getLevel());
        assertEquals(11, hero.getNbLifePoints());
        assertEquals(11, hero.getPower());
        assertEquals(0, hero.getArmor());

        // Verify that the HeroRepository save method was called with the updated HeroEntity
        verify(heroRepository).save(hero);
    }

    @Test
    public void testEngageCombat() {
        // Create some HeroDomain instances for testing
        HeroDomain atHero = new HeroDomain(1L, "Attacker", 100, 0, 50, 30, SpecialityTypeDomain.TANK, RaretyTypeDomain.COMMON, 1, true, true, null, null);
        HeroDomain defHero = new HeroDomain(2L, "Defender", 80, 0, 40, 25, SpecialityTypeDomain.ASSASSIN, RaretyTypeDomain.LEGENDARY, 1, true, true, null, null);

        // Create some HeroEntity instances for mocking
        HeroEntity atHeroEntity = new HeroEntity(1L, "Attacker", 100, 0, 50, 30, SpecialityTypeDomain.TANK, RaretyTypeDomain.COMMON, 1, true, true, null, null);
        HeroEntity defHeroEntity = new HeroEntity(2L, "Defender", 80, 0, 40, 25, SpecialityTypeDomain.ASSASSIN, RaretyTypeDomain.LEGENDARY, 1, true, true, null, null);


        // Mock the heroRepository to return the appropriate HeroEntity instances

        CombatEntity combat = new CombatEntity(atHeroEntity, defHeroEntity);
        CombatHistoryEntity combatHistory = new CombatHistoryEntity();
        combatHistory.setDamageAttackerHero(2);
        combatHistory.setNewLifePointsDefender(8);
        combatHistory.setResult("Draw");

        // Mock the repository calls
        when(heroRepository.findById(atHero.getId())).thenReturn(Optional.of(atHeroEntity));
        when(heroRepository.findById(defHero.getId())).thenReturn(Optional.of(defHeroEntity));

        when(heroRepository.save(atHeroEntity)).thenReturn(atHeroEntity);
        when(heroRepository.save(defHeroEntity)).thenReturn(defHeroEntity);
        when(combatRepository.save(any(CombatEntity.class))).thenReturn(combat);
        when(combatHistoryRepository.save(any(CombatHistoryEntity.class))).thenReturn(combatHistory);

        // Call the method being tested
        String result = combatService.engageCombat(atHero, defHero);

        // Verify the expected results
        verify(heroRepository, times(24)).findById(any(Long.class));
        verify(combatRepository, times(4)).save(any(CombatEntity.class));
        verify(combatRepository, never()).findById(any(Long.class));
        verify(heroRepository, times(8)).save(any(HeroEntity.class));
        verify(combatHistoryRepository, times(3)).save(any(CombatHistoryEntity.class));
        verifyNoMoreInteractions(heroRepository, combatRepository, combatHistoryRepository);

    }
}
