package esgi.infra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.PlayerDomain;
import esgi.infra.entity.DeckEntity;
import esgi.infra.entity.PlayerEntity;
import esgi.infra.repository.DeckRepository;
import esgi.infra.repository.PlayerRepository;
import esgi.infra.service.impl.PlayerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CreatePlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;
    
    @Mock
    private DeckRepository deckRepository;
    
    @InjectMocks
    private PlayerServiceImpl playerServiceImpl;
    
    @Test
    public void createPlayer_ShouldCreatePlayerWithDeckAndJeton() {
        // Given
        String pseudo = "test";
        PlayerEntity player = new PlayerEntity();
        player.setPseudo(pseudo);
        player.setJeton(4);
        DeckEntity deck = new DeckEntity();
    
        // When
        when(deckRepository.save(any(DeckEntity.class))).thenReturn(deck);
        when(playerRepository.save(any(PlayerEntity.class))).thenReturn(player);
        PlayerDomain createdPlayer = playerServiceImpl.createPlayer(pseudo);
    
        // Then
        assertEquals(pseudo, createdPlayer.getPseudo());
        assertEquals(4, createdPlayer.getJeton().intValue());
        assertEquals(deck, createdPlayer.getDeck());
        verify(deckRepository, times(1)).save(any(DeckEntity.class));
        verify(playerRepository, times(1)).save(any(PlayerEntity.class));
    }
}
