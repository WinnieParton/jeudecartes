package esgi.infra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.PlayerDomain;
import esgi.infra.entity.PlayerEntity;
import esgi.infra.repository.PlayerRepository;
import esgi.infra.service.impl.PlayerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class IsPseudoPlayerExistServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerServiceImpl;

    @Test
    public void testFindByPseudo_PlayerExists() {
        // given
        PlayerEntity player = new PlayerEntity("John Doe");
        when(playerRepository.findByPseudo("John Doe")).thenReturn(Optional.of(player));

        // when
        Optional<PlayerDomain> foundPlayer = playerServiceImpl.findByPseudo("John Doe");

        // then
        assertTrue(foundPlayer.isPresent());
        assertEquals(player, foundPlayer.get());
        verify(playerRepository, times(1)).findByPseudo("John Doe");
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
