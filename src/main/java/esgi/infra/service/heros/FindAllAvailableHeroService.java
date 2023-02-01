package esgi.infra.service.heros;

import java.util.List;

import esgi.domain.Hero;

public interface FindAllAvailableHeroService {
    List<Hero> findAllHerosAvailable();
}
