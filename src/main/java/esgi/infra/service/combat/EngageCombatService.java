package esgi.infra.service.combat;

import esgi.domain.Hero;

public interface EngageCombatService {
    String engageCombat(Hero attackerHero, Hero defenderHero);
}
