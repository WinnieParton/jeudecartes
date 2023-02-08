package esgi.infra.service;

import java.util.List;

import esgi.domain.HeroDomain;
import esgi.domain.PlayerDomain;

public interface ViewPlayerDeckService {
    List<HeroDomain> viewPlayerDeck(PlayerDomain player);
}
