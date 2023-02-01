package esgi.infra.service.desk;

import java.util.List;

import esgi.domain.Hero;
import esgi.domain.PackHeroType;
import esgi.domain.Player;

public interface OpenPackService {
    List<Hero> openPack(Player player, PackHeroType packType);
}
