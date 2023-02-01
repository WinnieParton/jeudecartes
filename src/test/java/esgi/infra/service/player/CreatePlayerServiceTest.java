package esgi.infra.service.player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.infra.repository.DeckRepository;
import esgi.infra.repository.PlayerRepository;

@ExtendWith(MockitoExtension.class)
public class CreatePlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private DeckRepository deckRepository;

    @Test
    public void createPlayer_ShouldCreatePlayerWithDeckAndJeton() {
    }
}
