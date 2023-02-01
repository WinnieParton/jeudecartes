package esgi.infra.service.player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.infra.repository.PlayerRepository;

@ExtendWith(MockitoExtension.class)
public class GetByIdPlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Test
    public void testGetByIdPlayerService_PlayerExists() {
    }

    @Test
    public void testGetByIdPlayerService_PlayerDoesNotExist() {

    }
}
