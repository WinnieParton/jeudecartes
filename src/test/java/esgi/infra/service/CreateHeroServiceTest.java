package esgi.infra.service;

import esgi.domain.HeroDomain;
import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import esgi.infra.entity.HeroEntity;
import esgi.infra.repository.HeroRepository;
import esgi.infra.service.impl.HerosServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class CreateHeroServiceTest {
    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private HerosServiceImpl heroService;

    @Test
    public void createHeroTest() {
        HeroEntity heroEntity = new HeroEntity("Hero", SpecialityTypeDomain.MAGE, RaretyTypeDomain.COMMON);
        HeroDomain hero = new HeroDomain(1L, "Hero", 100, 0, 10, 5, SpecialityTypeDomain.MAGE, RaretyTypeDomain.COMMON, 1, true, true, null, null);

        when(heroRepository.save(any(HeroEntity.class))).thenReturn(heroEntity);

        HeroDomain result = heroService.createHero("Hero", SpecialityTypeDomain.MAGE, RaretyTypeDomain.COMMON);

        assertEquals(hero.getName(), result.getName());
        assertEquals(hero.getSpeciality(), result.getSpeciality());
        assertEquals(hero.getRarity(), result.getRarity());
        verify(heroRepository).save(any(HeroEntity.class));
    }

}