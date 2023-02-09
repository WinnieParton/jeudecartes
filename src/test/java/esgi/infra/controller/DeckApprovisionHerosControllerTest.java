package esgi.infra.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import esgi.domain.HeroDomain;
import esgi.domain.PackHeroTypeDomain;
import esgi.domain.PlayerDomain;
import esgi.infra.dto.DeskAddPlayerDto;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.GetByIdPlayerService;
import esgi.infra.service.OpenPackService;
import esgi.infra.service.VerifyJetonService;

@ExtendWith(MockitoExtension.class)
public class DeckApprovisionHerosControllerTest {
    @Mock
    private VerifyJetonService verifyJetonService;
    @Mock
    private GetByIdPlayerService getByIdPlayerService;
    @Mock
    private OpenPackService openPackService;

    @InjectMocks
    private DeckApprovisionHerosController deckApprovisionHerosController;

    @Test
    public void addDeskPlayer_validData_returnsSuccess() {
        DeskAddPlayerDto addDeskPlayerDto = new DeskAddPlayerDto();
        addDeskPlayerDto.setPack(PackHeroTypeDomain.argent);
        addDeskPlayerDto.setPlayer(1L);

        PlayerDomain player = new PlayerDomain();
        player.setId(1L);
        player.setPseudo("player1");
        player.setJeton(1);

        when(getByIdPlayerService.getById(1L)).thenReturn(player);
        when(verifyJetonService.verifJetonByPackPlayer(PackHeroTypeDomain.argent, player)).thenReturn(true);
        when(openPackService.openPack(player, PackHeroTypeDomain.argent)).thenReturn(new ArrayList<HeroDomain>());

        ResponseEntity<?> result = deckApprovisionHerosController.addDeskPlayer(addDeskPlayerDto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Action succes !", ((MessageResponse) result.getBody()).getMap().get("message"));
    }

    @Test
    public void addDeskPlayer_invalidData_returnsBadRequest() {
        // given
        Errors errors = new BeanPropertyBindingResult(new DeskAddPlayerDto(), "DeskAddPlayerDto");
        ((AbstractBindingResult) errors).addError(new FieldError("DeskAddPlayerDto", "pack", "Pack is required"));

        // when
        ResponseEntity<?> result = deckApprovisionHerosController.addDeskPlayer(new DeskAddPlayerDto());

        // then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Erreur de validation de data", ((MessageResponse) result.getBody()).getMap().get("message"));
        // assertTrue(result.getBody().getClass()..contains("pack: Pack is required"));
    }
}
