package esgi.infra.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import esgi.domain.HeroDomain;
import esgi.domain.PlayerDomain;
import esgi.infra.dto.CombatDto;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.EngageCombatService;
import esgi.infra.service.FindByHeroCombatService;
import esgi.infra.service.GetByIdHeroServiceService;
import esgi.infra.service.GetByIdPlayerService;
import esgi.infra.service.VerifyAvailableHeroService;
import esgi.infra.service.VerifyHeroInDeckPlayerService;
import esgi.infra.service.VerifyStatusCombatService;

@Controller
@RequestMapping("/api/combat")
public class CombatController {
        private final GetByIdPlayerService getByIdPlayerService;
        private final EngageCombatService engageCombatService;
        private final GetByIdHeroServiceService getByIdHeroServiceService;
        private final VerifyHeroInDeckPlayerService verifyHeroInDeckPlayerService;
        private final VerifyStatusCombatService verifyStatusCombatService;
        private final FindByHeroCombatService findByHeroCombatService;
        private final VerifyAvailableHeroService verifyAvailableHeroService;

        public CombatController(GetByIdPlayerService getByIdPlayerService, EngageCombatService engageCombatService,
                        GetByIdHeroServiceService getByIdHeroServiceService,
                        VerifyHeroInDeckPlayerService verifyHeroInDeckPlayerService,
                        VerifyStatusCombatService verifyStatusCombatService,
                        FindByHeroCombatService findByHeroCombatService,
                        VerifyAvailableHeroService verifyAvailableHeroService) {
                this.getByIdPlayerService = getByIdPlayerService;
                this.engageCombatService = engageCombatService;
                this.getByIdHeroServiceService = getByIdHeroServiceService;
                this.verifyHeroInDeckPlayerService = verifyHeroInDeckPlayerService;
                this.verifyStatusCombatService = verifyStatusCombatService;
                this.findByHeroCombatService = findByHeroCombatService;
                this.verifyAvailableHeroService = verifyAvailableHeroService;
        }

        @PostMapping
        public ResponseEntity<?> engageCombat(@Valid @RequestBody CombatDto gameStartDto) {

                PlayerDomain currentPlayer = getByIdPlayerService.getById(gameStartDto.getAttackerPlayer());

                HeroDomain currentHero = getByIdHeroServiceService.getById(gameStartDto.getAttackerHero());

                if (!verifyHeroInDeckPlayerService.verifyHeroInDeckPlayerService(currentPlayer, currentHero))
                        return new ResponseEntity<>(
                                        new MessageResponse("The Hero attacked is not for player attacked !"),
                                        HttpStatus.BAD_REQUEST);

                if (!verifyAvailableHeroService.verifyAvailableHeroService(currentHero))
                        return new ResponseEntity<>(new MessageResponse(
                                        "The Hero attacked can no longer make a fight by what it has already used in another and lost the fight. !"),
                                        HttpStatus.BAD_REQUEST);

                PlayerDomain adversePlayer = getByIdPlayerService.getById(gameStartDto.getDefenderPlayer());

                HeroDomain adverseHero = getByIdHeroServiceService.getById(gameStartDto.getDefenderHero());

                if (!verifyHeroInDeckPlayerService.verifyHeroInDeckPlayerService(adversePlayer, adverseHero))
                        return new ResponseEntity<>(
                                        new MessageResponse("The Hero deffensed is not for player deffensed !"),
                                        HttpStatus.BAD_REQUEST);

                if (!verifyAvailableHeroService.verifyAvailableHeroService(adverseHero))
                        return new ResponseEntity<>(new MessageResponse(
                                        "The Hero deffensed can no longer make a fight by what it has already used in another and lost the fight. !"),
                                        HttpStatus.BAD_REQUEST);

                if (!verifyStatusCombatService.verifyStatusCombat(currentHero, adverseHero))
                        return new ResponseEntity<>(
                                        new MessageResponse("Combat finish !",
                                                        findByHeroCombatService.findByHeroCombat(currentHero,
                                                                        adverseHero)),
                                        HttpStatus.OK);

                return new ResponseEntity<>(
                                new MessageResponse("Action succes !",
                                                engageCombatService.engageCombat(currentHero, adverseHero)),
                                HttpStatus.CREATED);

        }
}
