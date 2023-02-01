package esgi.infra.service.desk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.infra.repository.HeroRepository;
import esgi.infra.repository.PlayerRepository;

@ExtendWith(MockitoExtension.class)
public class OpenPackServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private HeroRepository heroRepository;

    @Test
    public void testOpenPack() {

    }
}
