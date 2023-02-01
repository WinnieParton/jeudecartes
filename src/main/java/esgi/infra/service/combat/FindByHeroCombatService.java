package esgi.infra.service.combat;

import java.util.Optional;

import esgi.domain.Combat;
import esgi.domain.Hero;

public interface FindByHeroCombatService {
    Optional<Combat> findByHeroCombat(Hero attackerHero, Hero defenderHero);
}
