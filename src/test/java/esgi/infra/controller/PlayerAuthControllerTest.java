package esgi.infra.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.infra.service.CreatePlayerService;
import esgi.infra.service.IsPseudoPlayerExistService;

@ExtendWith(MockitoExtension.class)
public class PlayerAuthControllerTest {
    @Mock
    private IsPseudoPlayerExistService getPlayerByPseudoService;
    @Mock
    private CreatePlayerService addPlayerService;

    @InjectMocks
    private PlayerAuthController playerAuthController;

    // @Test
    // public void testAddPlayer_validData_returnsSuccess() {
    //     // create a mock player object to return from the getPlayerByPseudoService
    //     Player player = new Player();
    //     player.setPseudo("testplayer");
    //     when(getPlayerByPseudoService.findByPseudo("testplayer")).thenReturn(Optional.empty());
    //     // create a mock player object to return from the addPlayerService
    //     Player newPlayer = new Player();
    //     newPlayer.setPseudo("testplayer");
    //     when(addPlayerService.createPlayer("testplayer")).thenReturn(newPlayer);

    //     // create a request body with valid data
    //     PlayerAddDto playerDto = new PlayerAddDto();
    //     playerDto.setPseudo("testplayer");
    //     // create a mock errors object with no errors
    //     Errors errors = mock(Errors.class);
    //     when(errors.hasErrors()).thenReturn(false);

    //     // call the addPlayer method
    //     ResponseEntity<?> result = playerAuthController.addPlayer(playerDto, errors);

    //     // assert that the response has a status of CREATED
    //     assertEquals(HttpStatus.CREATED, result.getStatusCode());
    //     // assert that the response body has the correct message and data
    //     MessageResponse responseBody = (MessageResponse) result.getBody();
    //     assertEquals("Action succes !", responseBody.getMap().get("message"));
    //     assertEquals(newPlayer, responseBody.getMap().get("data"));
    // }

    // @Test
    // public void testAddPlayer_existingPseudo_returnsConflict() {
    //     // setup
    //     PlayerAddDto playerDto = new PlayerAddDto();
    //     playerDto.setPseudo("JohnDoe");
    //     Errors errors = new BeanPropertyBindingResult(playerDto, "playerDto");

    //     Optional<Player> existingPlayer = Optional.of(new Player("JohnDoe"));

    //     when(getPlayerByPseudoService.findByPseudo(playerDto.getPseudo())).thenReturn(existingPlayer);

    //     // when
    //     ResponseEntity<?> result = playerAuthController.addPlayer(playerDto, errors);

    //     // then
    //     assertEquals(HttpStatus.FOUND, result.getStatusCode());
    //     assertEquals("Pseudo exist already !", ((MessageResponse) result.getBody()).getMap().get("message"));
    //     assertEquals(existingPlayer.get(), ((MessageResponse) result.getBody()).getMap().get("data"));
    //     verify(getPlayerByPseudoService, times(1)).findByPseudo(playerDto.getPseudo());
    //     verify(addPlayerService, never()).createPlayer(playerDto.getPseudo());
    // }
}
