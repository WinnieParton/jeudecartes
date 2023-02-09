package esgi.infra.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import esgi.infra.response.MessageResponse;
import esgi.infra.service.SearchPlayersService;

@ExtendWith(MockitoExtension.class)
public class SearchPlayerControllerTest {

    // @Test
    // public void testSearchPlayers() {
    //     // Create a mock instance of the SearchPlayersService
    //     SearchPlayersService mockSearchPlayersService = mock(SearchPlayersService.class);
    //     // Define the behavior of the mock service
    //     when(mockSearchPlayersService.searchPlayers())
    //             .thenReturn(Arrays.asList(new Player("player1"), new Player("player2")));
    //     // Create an instance of the SearchPlayerController with the mock service
    //     SearchPlayerController controller = new SearchPlayerController(mockSearchPlayersService);
    //     // Call the searchPlayers method
    //     ResponseEntity<?> result = controller.searchPlayers();
    //     // Verify that the mock service's searchPlayers method was called
    //     verify(mockSearchPlayersService).searchPlayers();
    //     // Assert that the response has a 200 OK status
    //     assertEquals(HttpStatus.OK, result.getStatusCode());
    //     // Assert that the response body contains the expected message and data
    //     assertEquals("Action succes !", ((MessageResponse) result.getBody()).getMap().get("message"));
    //     assertEquals(Arrays.asList(new Player("player1"), new Player("player2")),
    //             ((MessageResponse) result.getBody()).getMap().get("data"));
    // }

}
