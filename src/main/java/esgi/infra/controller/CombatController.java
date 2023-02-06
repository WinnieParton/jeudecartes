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

import esgi.domain.Hero;
import esgi.domain.Player;
import esgi.infra.dto.CombatDto;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.combat.EngageCombatService;
import esgi.infra.service.combat.FindByHeroCombatService;
import esgi.infra.service.combat.VerifyStatusCombatService;
import esgi.infra.service.heros.GetByIdHeroServiceService;
import esgi.infra.service.player.GetByIdPlayerService;
import esgi.infra.service.player.VerifyHeroInDeckPlayerService;

@Controller
@RequestMapping("/api/combat")
public class CombatController {
    private final GetByIdPlayerService getByIdPlayerService;
    private final EngageCombatService engageCombatService;
    private final GetByIdHeroServiceService getByIdHeroServiceService;
    private final VerifyHeroInDeckPlayerService verifyHeroInDeckPlayerService;
    private final VerifyStatusCombatService verifyStatusCombatService;
    private final FindByHeroCombatService findByHeroCombatService;

    public CombatController(GetByIdPlayerService getByIdPlayerService, EngageCombatService engageCombatService,
            GetByIdHeroServiceService getByIdHeroServiceService,
            VerifyHeroInDeckPlayerService verifyHeroInDeckPlayerService,
            VerifyStatusCombatService verifyStatusCombatService, FindByHeroCombatService findByHeroCombatService) {
        this.getByIdPlayerService = getByIdPlayerService;
        this.engageCombatService = engageCombatService;
        this.getByIdHeroServiceService = getByIdHeroServiceService;
        this.verifyHeroInDeckPlayerService = verifyHeroInDeckPlayerService;
        this.verifyStatusCombatService = verifyStatusCombatService;
        this.findByHeroCombatService = findByHeroCombatService;
    }

    @PostMapping
    public ResponseEntity<?> engageCombat(@Valid @RequestBody CombatDto gameStartDto, Errors errors) {

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

        Optional<Player> currentPlayer = getByIdPlayerService.getById(gameStartDto.getAttackerPlayer());

        if (!currentPlayer.isPresent())
            return new ResponseEntity<>(new MessageResponse("Player attacked not found !"), HttpStatus.NOT_FOUND);
        Optional<Hero> currentHero = getByIdHeroServiceService.getById(gameStartDto.getAttackerHero());

        if (!currentHero.isPresent())
            return new ResponseEntity<>(new MessageResponse("Hero attacked not found !"), HttpStatus.NOT_FOUND);

        if (!verifyHeroInDeckPlayerService.verifyHeroInDeckPlayerService(currentPlayer.get(), currentHero.get()))
            return new ResponseEntity<>(new MessageResponse("The Hero attacked is not for player attacked !"),
                    HttpStatus.BAD_REQUEST);

        Optional<Player> adversePlayer = getByIdPlayerService.getById(gameStartDto.getDefenderPlayer());

        if (!adversePlayer.isPresent())
            return new ResponseEntity<>(new MessageResponse("Player deffensed not found !"), HttpStatus.NOT_FOUND);

        Optional<Hero> adverseHero = getByIdHeroServiceService.getById(gameStartDto.getDefenderHero());

        if (!adverseHero.isPresent())
            return new ResponseEntity<>(new MessageResponse("Hero attacked not found !"), HttpStatus.NOT_FOUND);

        if (!verifyHeroInDeckPlayerService.verifyHeroInDeckPlayerService(adversePlayer.get(), adverseHero.get()))
            return new ResponseEntity<>(new MessageResponse("The Hero deffensed is not for player deffensed !"),
                    HttpStatus.BAD_REQUEST);

        if (!verifyStatusCombatService.verifyStatusCombat(currentHero.get(), adverseHero.get()))
            return new ResponseEntity<>(
                    new MessageResponse("Combat finish !",
                            findByHeroCombatService.findByHeroCombat(currentHero.get(), adverseHero.get())),
                    HttpStatus.OK);

        return new ResponseEntity<>(
                new MessageResponse("Action succes !",
                        engageCombatService.engageCombat(currentHero.get(), adverseHero.get())),
                HttpStatus.CREATED);

    }
}
