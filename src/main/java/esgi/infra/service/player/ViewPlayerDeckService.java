package esgi.infra.service.player;

import java.util.List;

import esgi.domain.Hero;
import esgi.domain.Player;

public interface ViewPlayerDeckService {
    List<Hero> viewPlayerDeck(Player player);
}
