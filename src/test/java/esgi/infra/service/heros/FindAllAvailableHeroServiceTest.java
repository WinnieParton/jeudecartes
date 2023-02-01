package esgi.infra.service.heros;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.infra.repository.HeroRepository;

@ExtendWith(MockitoExtension.class)
public class FindAllAvailableHeroServiceTest {
    @Mock
    private HeroRepository heroRepository;

    @Test
    void testFindAllHerosAvailable_HerosExist() {

    }

    @Test
    void testFindAllHerosAvailable_HerosDoNotExist() {

    }
}
