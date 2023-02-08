package esgi.infra.service;

import java.util.Optional;

import esgi.domain.PlayerDomain;

public interface IsPseudoPlayerExistService {
   Optional<PlayerDomain> findByPseudo(String pseudo);
}
