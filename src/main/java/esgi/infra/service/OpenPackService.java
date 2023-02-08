package esgi.infra.service;

import java.util.List;

import esgi.domain.HeroDomain;
import esgi.domain.PackHeroTypeDomain;
import esgi.domain.PlayerDomain;

public interface OpenPackService {
    List<HeroDomain> openPack(PlayerDomain player, PackHeroTypeDomain packType);
}
