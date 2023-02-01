package esgi.infra.service.player;

import java.util.Optional;

import esgi.domain.Player;

public interface GetByIdPlayerService {
    Optional<Player> getById(Long id);
}
