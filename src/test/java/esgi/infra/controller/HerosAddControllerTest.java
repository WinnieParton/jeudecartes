package esgi.infra.controller;

import esgi.domain.HeroDomain;
import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import esgi.infra.dto.HerosAddDto;
import esgi.infra.response.MessageResponse;
import esgi.infra.service.CreateHeroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HerosAddControllerTest {
    @Mock
    private CreateHeroService createHeroService;

    @InjectMocks
    private HerosAddController herosAddController;

    @Test
    public void createHero_validData_returnsSuccess() {
        // Arrange
        HerosAddDto herosAddDto = new HerosAddDto();
        herosAddDto.setName("Superman");
        herosAddDto.setSpeciality(SpecialityTypeDomain.TANK);
        herosAddDto.setRarety(RaretyTypeDomain.LEGENDARY);

        HeroDomain createdHero = new HeroDomain();
        createdHero.setName("Superman");
        createdHero.setSpeciality(SpecialityTypeDomain.TANK);
        createdHero.setRarity(RaretyTypeDomain.LEGENDARY);

        // Configure mock
        when(createHeroService.createHero(herosAddDto.getName(), herosAddDto.getSpeciality(), herosAddDto.getRarety()))
                .thenReturn(createdHero);

        // Act
        ResponseEntity<?> result = herosAddController.createHero(herosAddDto);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Action succes !", ((MessageResponse) result.getBody()).getMap().get("message"));
        assertEquals(createdHero, ((MessageResponse) result.getBody()).getMap().get("data"));
        verify(createHeroService, times(1)).createHero(herosAddDto.getName(), herosAddDto.getSpeciality(),
                herosAddDto.getRarety());
    }


}
