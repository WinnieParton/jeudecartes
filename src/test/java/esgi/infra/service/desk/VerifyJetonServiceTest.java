package esgi.infra.service.desk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.infra.repository.PlayerRepository;

@ExtendWith(MockitoExtension.class)
public class VerifyJetonServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @Test
    public void testVerifJetonByPackPlayer_PlayerHasEnoughJeton() {

    }

    @Test
    public void testVerifJetonByPackPlayer_PlayerDoesNotHaveEnoughJeton() {

    }
}
