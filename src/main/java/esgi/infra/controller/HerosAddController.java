package esgi.infra.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import esgi.infra.dto.HerosAddDto;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.CreateHeroService;

@Controller
@RequestMapping("/api/hero")
public class HerosAddController {
    private final CreateHeroService createHeroService;

    public HerosAddController(CreateHeroService createHeroService) {
        this.createHeroService = createHeroService;
    }

    @PostMapping
    public ResponseEntity<?> createHero(@Valid @RequestBody HerosAddDto herosAddDto) {

        return new ResponseEntity<>(
                new MessageResponse("Action succes !", createHeroService.createHero(herosAddDto.getName(),
                        herosAddDto.getSpeciality(), herosAddDto.getRarety())),
                HttpStatus.OK);
    }

}
