package esgi.infra.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import esgi.domain.Player;
import esgi.infra.dto.PlayerAddDto;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.player.CreatePlayerService;
import esgi.infra.service.player.IsPseudoPlayerExistService;
import esgi.infra.service.player.ViewPlayerDeckService;

@Controller
@RequestMapping("/api/player")
public class PlayerAuthController {
        private IsPseudoPlayerExistService getPlayerByPseudoService;
        private CreatePlayerService addPlayerService;

        public PlayerAuthController(IsPseudoPlayerExistService getPlayerByPseudoService,
                        ViewPlayerDeckService viewPlayerDeckService, CreatePlayerService addPlayerService) {
                this.getPlayerByPseudoService = getPlayerByPseudoService;
                this.addPlayerService = addPlayerService;
        }

        @PostMapping
        public ResponseEntity<?> addPlayer(@Valid @RequestBody PlayerAddDto playerDto, Errors errors) {
                if (errors.hasErrors()) {
                        final List<String> err = new ArrayList<String>();
                        var i = 0;
                        for (final ObjectError error : errors.getFieldErrors()) {
                                err.add(errors.getFieldErrors().get(i).getField() + ": " + error.getDefaultMessage());
                                i++;
                        }
                        return new ResponseEntity<>(
                                        new MessageResponse("Erreur de validation de data", err),
                                        HttpStatus.BAD_REQUEST);
                }
                Player j = getPlayerByPseudoService.findByPseudo(playerDto.getPseudo());

                if (j != null) {
                        return new ResponseEntity<>(new MessageResponse("Pseudo exist already !",
                                        j), HttpStatus.FOUND);
                }

                return new ResponseEntity<>(new MessageResponse("Action succes !",
                                addPlayerService.createPlayer(playerDto.getPseudo())), HttpStatus.CREATED);
        }
}
