package esgi.infra.service.heros;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.infra.repository.HeroRepository;

@ExtendWith(MockitoExtension.class)
public class GetByIdHeroServiceServiceTest {
    @Mock
    private HeroRepository heroRepository;

    @Test
    void testGetById_HeroExists() {

    }

    @Test
    void testGetById_HeroDoesNotExist() {
    }
}
