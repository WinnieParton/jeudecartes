package esgi.infra.service.player;

import esgi.domain.Player;

public interface CreatePlayerService {
    // Création d’un compte player avec solde de jetons et deck en base de données
    Player createPlayer(String pseudo);
}
