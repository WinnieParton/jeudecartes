package esgi.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import esgi.domain.Deck;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> { }
