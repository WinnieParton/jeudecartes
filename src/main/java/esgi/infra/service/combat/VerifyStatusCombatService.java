package esgi.infra.service.combat;

import esgi.domain.Hero;

public interface VerifyStatusCombatService {
    Boolean verifyStatusCombat(Hero attackerHero, Hero defenderHero);
}
