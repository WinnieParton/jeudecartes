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

import esgi.infra.dto.HerosAddDto;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.heros.CreateHeroService;

@Controller
@RequestMapping("/api/hero")
public class HerosAddController {
    private final CreateHeroService createHeroService;

    public HerosAddController(CreateHeroService createHeroService) {
        this.createHeroService = createHeroService;
    }

    @PostMapping
    public ResponseEntity<?> createHero(@Valid @RequestBody HerosAddDto herosAddDto, Errors errors) {
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
        return new ResponseEntity<>(
                new MessageResponse("Action succes !", createHeroService.createHero(herosAddDto.getName(),
                        herosAddDto.getSpeciality(), herosAddDto.getRarety())),
                HttpStatus.OK);
    }

}
