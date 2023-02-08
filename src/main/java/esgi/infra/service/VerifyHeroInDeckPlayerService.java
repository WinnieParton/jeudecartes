package esgi.infra.service;

import esgi.domain.HeroDomain;
import esgi.domain.PlayerDomain;

public interface VerifyHeroInDeckPlayerService {
    Boolean verifyHeroInDeckPlayerService(PlayerDomain player, HeroDomain hero);
}
