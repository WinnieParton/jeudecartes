package esgi.infra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.HeroDomain;
import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import esgi.infra.entity.HeroEntity;
import esgi.infra.repository.HeroRepository;
import esgi.infra.service.impl.HerosServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CreateHeroServiceTest {
    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private HerosServiceImpl heroService;

    @Test
    public void createHero_ShouldCreateHero() {
        String name = "Superman";
        SpecialityTypeDomain speciality = SpecialityTypeDomain.TANK;
        RaretyTypeDomain rarity = RaretyTypeDomain.LEGENDARY;

        HeroEntity heroEntity = new HeroEntity(name, speciality, rarity);
        heroEntity.setNbLifePoints(1200);
        heroEntity.setPower(120);
        heroEntity.setArmor(24);
        when(heroRepository.save(heroEntity)).thenReturn(heroEntity);

        HeroDomain hero = heroService.createHero(name, speciality, rarity);

        verify(heroRepository).save(heroEntity);
        assertEquals(hero.getName(), name);
        assertEquals(hero.getSpeciality(), speciality);
        assertEquals(hero.getRarity(), rarity);
        assertEquals(hero.getNbLifePoints(), 1200, 0.01);
        assertEquals(hero.getPower(), 120, 0.01);
        assertEquals(hero.getArmor(), 24, 0.01);
    }
}