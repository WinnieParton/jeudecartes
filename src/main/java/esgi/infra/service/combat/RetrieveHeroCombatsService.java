package esgi.infra.service.combat;

import java.util.List;

import esgi.domain.Combat;
import esgi.domain.Hero;

public interface RetrieveHeroCombatsService {
    List<Combat> retrieveHeroCombats(Hero hero);
}
