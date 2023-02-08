package esgi.infra.service;

import java.util.List;

import esgi.domain.HeroDomain;

public interface FindAllAvailableHeroService {
    List<HeroDomain> findAllHerosAvailable();
}
