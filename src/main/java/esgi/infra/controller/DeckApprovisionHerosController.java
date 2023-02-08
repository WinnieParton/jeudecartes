package esgi.infra.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import esgi.domain.PlayerDomain;
import esgi.infra.dto.DeskAddPlayerDto;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.GetByIdPlayerService;
import esgi.infra.service.OpenPackService;
import esgi.infra.service.VerifyJetonService;

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
    public ResponseEntity<?> addDeskPlayer(@Valid @RequestBody DeskAddPlayerDto addDeskPlayerDto) {

        PlayerDomain j = getByIdPlayerService.getById(addDeskPlayerDto.getPlayer());

        if (!verifyJetonService.verifJetonByPackPlayer(addDeskPlayerDto.getPack(), j))
            return new ResponseEntity<>(new MessageResponse("Player don't have jeton to buy heros"),
                    HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(new MessageResponse("Action succes !",
                openPackService.openPack(j, addDeskPlayerDto.getPack())),
                HttpStatus.CREATED);
    }

}
