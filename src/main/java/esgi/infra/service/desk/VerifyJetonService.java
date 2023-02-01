package esgi.infra.service.desk;

import esgi.domain.PackHeroType;
import esgi.domain.Player;

public interface VerifyJetonService {
    // Verfie si le jeton du player lui permet d'avoir ce pack
    Boolean verifJetonByPackPlayer(PackHeroType packHero, Player player);
}
