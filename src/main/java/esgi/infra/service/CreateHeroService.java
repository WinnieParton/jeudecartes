package esgi.infra.service;

import esgi.domain.HeroDomain;
import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;

public interface CreateHeroService {
    HeroDomain createHero(String name, SpecialityTypeDomain speciality, RaretyTypeDomain rarity);
}
