package esgi.infra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import esgi.domain.HeroDomain;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.GetByIdHeroServiceService;
import esgi.infra.service.RetrieveHeroCombatsService;

@Controller
@RequestMapping("/api/hero")
public class HistoryCombatHeroController {
    private final RetrieveHeroCombatsService combatsService;
    private final GetByIdHeroServiceService getByIdHeroServiceService;

    public HistoryCombatHeroController(RetrieveHeroCombatsService combatsService,
            GetByIdHeroServiceService getByIdHeroServiceService) {
        this.combatsService = combatsService;
        this.getByIdHeroServiceService = getByIdHeroServiceService;
    }

    @GetMapping("/combat/{idhero}")
    public ResponseEntity<?> retrieveHeroCombats(@PathVariable(value = "idhero") Long idhero) {
        HeroDomain hero = getByIdHeroServiceService.getById(idhero);

        return new ResponseEntity<>(new MessageResponse("Action succes !",
                combatsService.retrieveHeroCombats(hero)), HttpStatus.OK);
    }
}
