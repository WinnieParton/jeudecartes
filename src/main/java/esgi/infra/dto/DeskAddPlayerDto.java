package esgi.infra.dto;

import javax.validation.constraints.NotBlank;

import esgi.domain.PackHeroTypeDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeskAddPlayerDto {
    @NotBlank
    private PackHeroTypeDomain pack;

    @NotBlank
    private Long player;
}
