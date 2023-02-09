package esgi.infra.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
public class FindAllAvailableHeroServiceTest {
    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private HerosServiceImpl herosService;

    

    @Test
    public void testFindAllHerosAvailable() {
        // Given
        List<HeroEntity> heroEntities = new ArrayList<>();
        heroEntities.add(new HeroEntity("hero1", SpecialityTypeDomain.ASSASSIN, RaretyTypeDomain.RARE));
        heroEntities.add(new HeroEntity("hero2", SpecialityTypeDomain.MAGE, RaretyTypeDomain.LEGENDARY));
        heroEntities.add(new HeroEntity("hero3", SpecialityTypeDomain.TANK, RaretyTypeDomain.COMMON));

        when(heroRepository.findByStatusTrue()).thenReturn(heroEntities);

        // When
        List<HeroDomain> result = herosService.findAllHerosAvailable();

        // Then
        assertEquals(3, result.size());
        verify(heroRepository, times(1)).findByStatusTrue();
    }
}
