package esgi.infra.service.heros;

import java.util.Optional;

import esgi.domain.Hero;

public interface GetByIdHeroServiceService {
    Optional<Hero> getById(Long id);
}
