package esgi.infra.service.player;

import esgi.domain.Player;

public interface IsPseudoPlayerExistService {
   Player findByPseudo(String pseudo);
}
