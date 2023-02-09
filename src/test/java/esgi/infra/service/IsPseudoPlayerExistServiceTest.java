package esgi.infra.service;

import esgi.domain.PlayerDomain;
import esgi.infra.entity.DeckEntity;
import esgi.infra.entity.PlayerEntity;
import esgi.infra.repository.PlayerRepository;
import esgi.infra.service.impl.PlayerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IsPseudoPlayerExistServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerServiceImpl;

    @Test
    public void testFindByPseudo_PlayerExists() {
        // Arrange
        String pseudo = "player1";
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(1L);
        playerEntity.setPseudo(pseudo);
        playerEntity.setJeton(100);
        Optional<PlayerEntity> playerEntityOptional = Optional.of(playerEntity);

        DeckEntity deckEntity = new DeckEntity();
        deckEntity.setId(1L);
        deckEntity.setHeros(Collections.emptyList());
        playerEntity.setDeck(deckEntity);

        when(playerRepository.findByPseudo(pseudo)).thenReturn(playerEntityOptional);

        // Act
        Optional<PlayerDomain> playerDomainOptional = playerServiceImpl.findByPseudo(pseudo);

        // Assert
        assertTrue(playerDomainOptional.isPresent());
        PlayerDomain playerDomain = playerDomainOptional.get();
        assertEquals(playerDomain.getId(), playerEntity.getId());
        assertEquals(playerDomain.getPseudo(), playerEntity.getPseudo());
        assertEquals(playerDomain.getJeton(), playerEntity.getJeton());
    }

    @Test
    public void testFindByPseudo_PlayerDoesNotExist() {
        // given
        when(playerRepository.findByPseudo("Jane Doe")).thenReturn(Optional.empty());

        // when
        Optional<PlayerDomain> foundPlayer = playerServiceImpl.findByPseudo("Jane Doe");

        // then
        assertFalse(foundPlayer.isPresent());
        verify(playerRepository, times(1)).findByPseudo("Jane Doe");
    }
}
