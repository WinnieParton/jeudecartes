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
    }

    @Test
    public void testAttack() {
        HeroEntity attacker = new HeroEntity("Attacker", SpecialityTypeDomain.MAGE, RaretyTypeDomain.COMMON);
        HeroEntity defender = new HeroEntity("Defender", SpecialityTypeDomain.TANK, RaretyTypeDomain.LEGENDARY);
        attacker.setPower(10);
        defender.setPower(30);
        attacker.setArmor(20);
        defender.setArmor(5);
        when(heroRepository.findById(1L)).thenReturn(Optional.of(attacker));
        when(heroRepository.findById(2L)).thenReturn(Optional.of(defender));

        int result = combatService.attack(attacker, defender);

        assertEquals(5, result);
        assertEquals(5, defender.getNbLifePoints());
    }
}
