package esgi.infra.service.combat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.infra.repository.CombatRepository;
import esgi.infra.repository.HeroRepository;

@ExtendWith(MockitoExtension.class)
public class EngageCombatServiceTest {

    @Mock
    private CombatRepository combatRepository;

    @Mock
    private HeroRepository heroRepository;

    @Test
    public void testEngageCombat() {
    }
}
