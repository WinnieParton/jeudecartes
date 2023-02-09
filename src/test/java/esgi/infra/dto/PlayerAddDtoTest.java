package esgi.infra.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlayerAddDtoTest {
    @Mock
    private PlayerAddDto playerAddDto;

    @Test
    public void testPlayerAdd() {
        playerAddDto.setPseudo("winnie");
        assertEquals("winnie", playerAddDto.getPseudo());

    }

    @Test
    public void testPlayerAdd_InvalidPseudo() {
       
    }

}
