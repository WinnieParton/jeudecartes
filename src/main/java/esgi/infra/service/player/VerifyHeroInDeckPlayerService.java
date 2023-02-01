package esgi.infra.service.player;

import esgi.domain.Hero;
import esgi.domain.Player;

public interface VerifyHeroInDeckPlayerService {
    Boolean verifyHeroInDeckPlayerService(Player player, Hero hero);
}
