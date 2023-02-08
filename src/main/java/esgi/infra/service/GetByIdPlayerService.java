package esgi.infra.service;

import esgi.domain.PlayerDomain;

public interface GetByIdPlayerService {
    PlayerDomain getById(Long id);
}
