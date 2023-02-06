package esgi.infra.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import esgi.domain.Player;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.player.GetByIdPlayerService;
import esgi.infra.service.player.ViewPlayerDeckService;

@Controller
@RequestMapping("/api/player")
public class ViewPlayerDeckController {
    private final ViewPlayerDeckService viewPlayerDeckService;
    private final GetByIdPlayerService getByIdPlayerService;

    public ViewPlayerDeckController(ViewPlayerDeckService viewPlayerDeckService,
            GetByIdPlayerService getByIdPlayerService) {
        this.viewPlayerDeckService = viewPlayerDeckService;
        this.getByIdPlayerService = getByIdPlayerService;
    }

    @GetMapping(value = "/deck/{idplayer}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> viewPlayerDeck(@PathVariable(value = "idplayer") Long idplayer) {

        Optional<Player> j = getByIdPlayerService.getById(idplayer);

        if (!j.isPresent())
            return new ResponseEntity<>(new MessageResponse("Player not found !"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new MessageResponse("Action succes !",
                viewPlayerDeckService.viewPlayerDeck(j.get())),
                HttpStatus.OK);
    }
}