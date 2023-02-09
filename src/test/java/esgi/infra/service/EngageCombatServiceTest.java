package esgi.infra.service;

import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
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
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

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

        assertEquals(false, result);
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
}
