package esgi.infra.service.heros;

import esgi.domain.Hero;
import esgi.domain.RaretyType;
import esgi.domain.SpecialityType;

public interface CreateHeroService {
    Hero createHero(String name, SpecialityType speciality, RaretyType rarity);
}
