package esgi.infra.service.combat;

import esgi.domain.Combat;
import esgi.domain.Hero;

public interface EngageCombatService {
    Combat engageCombat(Hero attackerHero, Hero defenderHero);
}
