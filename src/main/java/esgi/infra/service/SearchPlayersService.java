package esgi.infra.service;

import java.util.List;

import esgi.domain.PlayerDomain;

public interface SearchPlayersService {
    List<PlayerDomain> searchPlayers();
}
