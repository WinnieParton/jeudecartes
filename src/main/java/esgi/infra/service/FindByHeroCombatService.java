package esgi.infra.service;

import java.util.Optional;

import esgi.domain.CombatDomain;
import esgi.domain.HeroDomain;

public interface FindByHeroCombatService {
    Optional<CombatDomain> findByHeroCombat(HeroDomain attackerHero, HeroDomain defenderHero);
}
