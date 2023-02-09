package esgi.infra.controller;

import esgi.domain.PlayerDomain;
import esgi.infra.dto.PlayerAddDto;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.CreatePlayerService;
import esgi.infra.service.IsPseudoPlayerExistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerAuthControllerTest {
    @Mock
    private IsPseudoPlayerExistService getPlayerByPseudoService;
    @Mock
    private CreatePlayerService addPlayerService;

    @InjectMocks
    private PlayerAuthController playerAuthController;

    @Test
    public void testAddPlayer_PlayerExists() {
        PlayerAddDto playerDto = new PlayerAddDto();
        playerDto.setPseudo("existingPseudo");

        PlayerDomain existingPlayer = new PlayerDomain();
        existingPlayer.setPseudo("existingPseudo");

        Optional<PlayerDomain> playerOptional = Optional.of(existingPlayer);

        when(getPlayerByPseudoService.findByPseudo(playerDto.getPseudo())).thenReturn(playerOptional);

        ResponseEntity<?> result = playerAuthController.addPlayer(playerDto);

        assertEquals(HttpStatus.FOUND, result.getStatusCode());
        assertEquals("Pseudo exist already !", ((MessageResponse) result.getBody()).getMap().get("message"));
        assertEquals(playerOptional, ((MessageResponse) result.getBody()).getMap().get("data"));

        verify(getPlayerByPseudoService, times(1)).findByPseudo(playerDto.getPseudo());
        verify(addPlayerService, never()).createPlayer(playerDto.getPseudo());
    }

    @Test
    public void testAddPlayer_Success() {
        PlayerAddDto playerDto = new PlayerAddDto();
        playerDto.setPseudo("newPseudo");

        Optional<PlayerDomain> playerOptional = Optional.empty();

        when(getPlayerByPseudoService.findByPseudo(playerDto.getPseudo())).thenReturn(playerOptional);

        PlayerDomain createdPlayer = new PlayerDomain();
        createdPlayer.setPseudo("newPseudo");

        when(addPlayerService.createPlayer(playerDto.getPseudo())).thenReturn(createdPlayer);

        ResponseEntity<?> result = playerAuthController.addPlayer(playerDto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Action succes !", ((MessageResponse) result.getBody()).getMap().get("message"));
        assertEquals(createdPlayer, ((MessageResponse) result.getBody()).getMap().get("data"));

        verify(getPlayerByPseudoService, times(1)).findByPseudo(playerDto.getPseudo());
        verify(addPlayerService, times(1)).createPlayer(playerDto.getPseudo());
    }
}
