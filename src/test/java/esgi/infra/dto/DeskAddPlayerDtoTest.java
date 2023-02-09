package esgi.infra.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import esgi.domain.PackHeroTypeDomain;

@ExtendWith(MockitoExtension.class)
public class DeskAddPlayerDtoTest {

    @InjectMocks
    private DeskAddPlayerDto deskAddPlayerDto;

    @Test
    public void testGetPack() {
        deskAddPlayerDto.setPack(PackHeroTypeDomain.argent);
        assertEquals(PackHeroTypeDomain.argent, deskAddPlayerDto.getPack());
    }

    @Test
    public void testGetPlayer() {
        Long playerId = 1L;
        deskAddPlayerDto.setPlayer(playerId);
        assertEquals(playerId, deskAddPlayerDto.getPlayer());
    }
}
