package esgi.infra.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import esgi.infra.service.GetByIdPlayerService;
import esgi.infra.service.ViewPlayerDeckService;

@ExtendWith(MockitoExtension.class)
public class ViewPlayerDeckControllerTest {
    private static final Long PLAYER_ID = 1L;
    private static final String PLAYER_PSEUDO = "test";
    private static final SpecialityTypeDomain HERO_SPECIALITY = SpecialityTypeDomain.TANK;
    private static final RaretyTypeDomain HERO_RARETY = RaretyTypeDomain.COMMON;

    @Mock
    private ViewPlayerDeckService viewPlayerDeckService;

    @Mock
    private GetByIdPlayerService getByIdPlayerService;

    @InjectMocks
    private ViewPlayerDeckController viewPlayerDeckController;

    // @Test
    // public void viewPlayerDeck_PlayerNotFound_ShouldReturn404() {
    //     when(getByIdPlayerService.getById(PLAYER_ID)).thenReturn(Optional.empty());

    //     ResponseEntity<?> result = viewPlayerDeckController.viewPlayerDeck(PLAYER_ID);

    //     assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    //     assertEquals("Player not found !", ((MessageResponse) result.getBody()).getMap().get("message"));
    // }

    // @Test
    // public void viewPlayerDeck_Success_ShouldReturn200() {
    //     Player player = new Player(PLAYER_PSEUDO);
    //     Hero hero = new Hero("test", HERO_SPECIALITY, HERO_RARETY);
    //     player.getDeck().addHero(hero);
    //     when(getByIdPlayerService.getById(PLAYER_ID)).thenReturn(Optional.of(player));
    //     when(viewPlayerDeckService.viewPlayerDeck(player)).thenReturn(player.getDeck().getHeros());

    //     ResponseEntity<?> result = viewPlayerDeckController.viewPlayerDeck(PLAYER_ID);

    //     assertEquals(HttpStatus.OK, result.getStatusCode());
    //     assertEquals("Action succes !", ((MessageResponse) result.getBody()).getMap().get("message"));
    //     assertEquals(player.getDeck().getHeros(), ((MessageResponse) result.getBody()).getMap().get("data"));
    // }

}
