package esgi.infra.service;

import esgi.domain.HeroDomain;

public interface EngageCombatService {
    String engageCombat(HeroDomain attackerHero, HeroDomain defenderHero);
}
