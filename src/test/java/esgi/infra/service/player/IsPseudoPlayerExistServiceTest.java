package esgi.infra.service.player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.infra.repository.PlayerRepository;

@ExtendWith(MockitoExtension.class)
public class IsPseudoPlayerExistServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @Test
    public void testFindByPseudo_PlayerExists() {
    }

    @Test
    public void testFindByPseudo_PlayerDoesNotExist() {
    }
}
