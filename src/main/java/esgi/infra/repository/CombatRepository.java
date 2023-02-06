package esgi.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import esgi.infra.entity.CombatEntity;
import esgi.infra.entity.HeroEntity;

@Repository
public interface CombatRepository extends JpaRepository<CombatEntity, Long> {

    List<CombatEntity> findByAttackingHeroOrDefendingHero(HeroEntity attackingHero, HeroEntity defendingHero);

    Optional<CombatEntity> findTopByAttackingHeroAndDefendingHeroOrderByIdDesc(HeroEntity attackingHero,
            HeroEntity defendingHero);
}
