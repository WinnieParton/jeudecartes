package esgi.infra.service.player;

import java.util.Optional;

import esgi.domain.Player;

public interface IsPseudoPlayerExistService {
   Optional<Player> findByPseudo(String pseudo);
}
