package esgi.infra.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PlayerAddDto {
    @NotNull
    @Size(min = 3, max = 20,message = "Invalid pseudo")
    private String pseudo;
}
