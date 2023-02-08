package esgi.infra.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import esgi.domain.PlayerDomain;
import esgi.infra.dto.PlayerAddDto;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.CreatePlayerService;
import esgi.infra.service.IsPseudoPlayerExistService;
import esgi.infra.service.ViewPlayerDeckService;

@Controller
@RequestMapping("/api/player")
public class PlayerAuthController {
        private final IsPseudoPlayerExistService getPlayerByPseudoService;
        private final CreatePlayerService addPlayerService;

        public PlayerAuthController(IsPseudoPlayerExistService getPlayerByPseudoService,
                        ViewPlayerDeckService viewPlayerDeckService, CreatePlayerService addPlayerService) {
                this.getPlayerByPseudoService = getPlayerByPseudoService;
                this.addPlayerService = addPlayerService;
        }

        @PostMapping
        public ResponseEntity<?> addPlayer(@Valid @RequestBody PlayerAddDto playerDto) {

                Optional<PlayerDomain> j = getPlayerByPseudoService.findByPseudo(playerDto.getPseudo());

                if (j.isPresent()) {
                        return new ResponseEntity<>(new MessageResponse("Pseudo exist already !",
                                        j), HttpStatus.FOUND);
                }

                return new ResponseEntity<>(new MessageResponse("Action succes !",
                                addPlayerService.createPlayer(playerDto.getPseudo())), HttpStatus.CREATED);
        }
}
