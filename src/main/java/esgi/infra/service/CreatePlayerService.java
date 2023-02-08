package esgi.infra.service;

import esgi.domain.PlayerDomain;

public interface CreatePlayerService {
    // Création d’un compte player avec solde de jetons et deck en base de données
    PlayerDomain createPlayer(String pseudo);
}
