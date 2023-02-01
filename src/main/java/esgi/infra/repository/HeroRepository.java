package esgi.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import esgi.domain.Hero;
import esgi.domain.RaretyType;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> { 
    List<Hero> findByStatusTrue();

    List<Hero> findByRarityAndStatusTrue(RaretyType rarity);
}

