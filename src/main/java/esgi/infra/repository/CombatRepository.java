package esgi.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import esgi.domain.Combat;
import esgi.domain.Hero;

@Repository
public interface CombatRepository extends JpaRepository<Combat, Long> {

    List<Combat> findByAttackingHeroOrDefendingHero(Hero attackingHero, Hero defendingHero);

    Optional<Combat> findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(Hero attackingHero, Hero defendingHero);
}
