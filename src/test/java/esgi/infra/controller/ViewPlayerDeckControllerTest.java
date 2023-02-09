package esgi.infra.controller;

import esgi.domain.HeroDomain;
import esgi.domain.PlayerDomain;
import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.GetByIdPlayerService;
import esgi.infra.service.ViewPlayerDeckService;
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
import static org.mockito.Mockito.*;

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

    @Test
    public void testViewPlayerDeck() {
        // setup
        Long idplayer = 1L;
        PlayerDomain player = mock(PlayerDomain.class);
        List<HeroDomain> heros = Arrays.asList(new HeroDomain(), new HeroDomain());

        // stub methods
        when(getByIdPlayerService.getById(idplayer)).thenReturn(player);
        when(viewPlayerDeckService.viewPlayerDeck(player)).thenReturn(heros);

        // execute
        ResponseEntity<?> response = viewPlayerDeckController.viewPlayerDeck(idplayer);

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Action succes !", ((MessageResponse) response.getBody()).getMap().get("message"));
        assertEquals(heros, ((MessageResponse) response.getBody()).getMap().get("data"));

        // verify interaction with mock objects
        verify(getByIdPlayerService).getById(idplayer);
        verify(viewPlayerDeckService).viewPlayerDeck(player);
    }


}
