package esgi.infra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import esgi.infra.response.MessageResponse;
import esgi.infra.service.SearchPlayersService;

@Controller
@RequestMapping("/api/player")
public class SearchPlayerController {
    private final SearchPlayersService searchPlayersService;

    public SearchPlayerController(SearchPlayersService searchPlayersService) {
        this.searchPlayersService = searchPlayersService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchPlayers() {
        return new ResponseEntity<>(new MessageResponse("Action succes !",
                searchPlayersService.searchPlayers()),
                HttpStatus.OK);
    }

}
