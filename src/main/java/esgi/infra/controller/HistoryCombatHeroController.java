package esgi.infra.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import esgi.domain.Hero;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.combat.RetrieveHeroCombatsService;
import esgi.infra.service.heros.GetByIdHeroServiceService;

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
        Optional<Hero> hero = getByIdHeroServiceService.getById(idhero);

        if (!hero.isPresent())
            return new ResponseEntity<>(new MessageResponse("Hero not found !"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new MessageResponse("Action succes !",
                combatsService.retrieveHeroCombats(hero.get())), HttpStatus.OK);
    }
}
