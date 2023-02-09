package esgi.infra.dto;

import esgi.domain.PackHeroTypeDomain;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DeskAddPlayerDto {
    @NotBlank
    private PackHeroTypeDomain pack;

    @NotNull
    private Long player;
}
