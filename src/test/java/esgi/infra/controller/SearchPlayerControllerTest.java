package esgi.infra.controller;

import esgi.domain.PlayerDomain;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.SearchPlayersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchPlayerControllerTest {
    @InjectMocks
    private SearchPlayerController searchPlayerController;
    @Mock
    private SearchPlayersService searchPlayersService;
    @Test
    public void testSearchPlayersSuccess() {
        List<PlayerDomain> players = Arrays.asList(
                new PlayerDomain (1L, "player1",null, null),
                new PlayerDomain(2L,"player2",null, null)
        );
        when(searchPlayersService.searchPlayers()).thenReturn(players);

        ResponseEntity<?> response = searchPlayerController.searchPlayers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Action succes !", ((MessageResponse) response.getBody()).getMap().get("message"));
        assertEquals(players,((MessageResponse) response.getBody()).getMap().get("data"));
    }

}
