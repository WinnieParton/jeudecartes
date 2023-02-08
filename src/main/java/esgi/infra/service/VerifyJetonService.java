package esgi.infra.service;

import esgi.domain.PackHeroTypeDomain;
import esgi.domain.PlayerDomain;

public interface VerifyJetonService {
    // Verfie si le jeton du player lui permet d'avoir ce pack
    Boolean verifJetonByPackPlayer(PackHeroTypeDomain packHero, PlayerDomain player);
}
