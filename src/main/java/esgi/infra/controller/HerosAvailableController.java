package esgi.infra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import esgi.infra.response.MessageResponse;
import esgi.infra.service.heros.FindAllAvailableHeroService;

@Controller
@RequestMapping("/api/hero")
public class HerosAvailableController {
    private FindAllAvailableHeroService herosService;

    public HerosAvailableController(FindAllAvailableHeroService herosService) {
        this.herosService = herosService;
    }

    @GetMapping
    public ResponseEntity<?> getHerosAvailable() {
        return new ResponseEntity<>(new MessageResponse("Action succes !",
                herosService.findAllHerosAvailable()), HttpStatus.OK);
    }

}
