package esgi.infra.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CombatDto {
    @NotNull
    private Long attackerPlayer;

    @NotNull
    private Long defenderPlayer;

    @NotNull
    private Long attackerHero;

    @NotNull
    private Long defenderHero;
}
