package esgi.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import esgi.infra.entity.CombatHistoryEntity;

@Repository
public interface CombatHistoryRepository extends JpaRepository<CombatHistoryEntity, Long> {
}
