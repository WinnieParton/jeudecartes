package esgi.infra.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import esgi.infra.dto.DeskAddPlayerDto;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.desk.OpenPackService;
import esgi.infra.service.desk.VerifyJetonService;
import esgi.infra.service.player.GetByIdPlayerService;

@Controller
@RequestMapping("/api/desk")
public class DeckApprovisionHerosController {

        private final VerifyJetonService verifyJetonService;
        private final GetByIdPlayerService getByIdPlayerService;
        private final OpenPackService openPackService;

        public DeckApprovisionHerosController(VerifyJetonService verifyJetonService,
                        GetByIdPlayerService getByIdPlayerService, OpenPackService openPackService) {
                this.verifyJetonService = verifyJetonService;
                this.getByIdPlayerService = getByIdPlayerService;
                this.openPackService = openPackService;
        }

        @PostMapping
        public ResponseEntity<?> addDeskPlayer(@Valid @RequestBody DeskAddPlayerDto addDeskPlayerDto, Errors errors) {
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
                Optional<Player> j = getByIdPlayerService.getById(addDeskPlayerDto.getPlayer());

                if (!j.isPresent())
                        return new ResponseEntity<>(new MessageResponse("Player not found !"), HttpStatus.NOT_FOUND);

                if (!verifyJetonService.verifJetonByPackPlayer(addDeskPlayerDto.getPack(), j.get()))
                        return new ResponseEntity<>(new MessageResponse("Player don't have jeton to buy heros"),
                                        HttpStatus.FORBIDDEN);

                return new ResponseEntity<>(new MessageResponse("Action succes !",
                                openPackService.openPack(j.get(), addDeskPlayerDto.getPack())),
                                HttpStatus.CREATED);
        }

}
