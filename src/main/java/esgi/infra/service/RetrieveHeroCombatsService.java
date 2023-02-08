package esgi.infra.service;

import java.util.List;

import esgi.domain.CombatDomain;
import esgi.domain.HeroDomain;

public interface RetrieveHeroCombatsService {
    List<CombatDomain> retrieveHeroCombats(HeroDomain hero);
}
