package esgi.infra.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CombatDto {
    @NotBlank
    private Long attackerPlayer;

    @NotBlank
    private Long defenderPlayer;

    @NotBlank
    private Long attackerHero;

    @NotBlank
    private Long defenderHero;
}
