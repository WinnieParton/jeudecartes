package esgi.infra.dto;

import javax.validation.constraints.NotBlank;

import esgi.domain.PackHeroType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeskAddPlayerDto {
    @NotBlank
    private PackHeroType pack;

    @NotBlank
    private Long player;
}
