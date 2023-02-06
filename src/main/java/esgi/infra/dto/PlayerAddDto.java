package esgi.infra.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerAddDto {
    @NotBlank
    @Size(min = 3, max = 50,message = "Invalid pseudo")
    private String pseudo;
}
