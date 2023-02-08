package esgi.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import esgi.domain.RaretyTypeDomain;
import esgi.infra.entity.HeroEntity;

@Repository
public interface HeroRepository extends JpaRepository<HeroEntity, Long> {
    List<HeroEntity> findByStatusTrue();

    List<HeroEntity> findByRarityAndStatusTrue(RaretyTypeDomain rarity);

    Optional<HeroEntity> findByIdAndAvailableTrue(Long id);
}
