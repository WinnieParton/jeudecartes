package esgi.infra.service;

import esgi.domain.HeroDomain;

public interface VerifyStatusCombatService {
    Boolean verifyStatusCombat(HeroDomain attackerHero, HeroDomain defenderHero);
}
