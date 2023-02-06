package esgi.infra.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import esgi.domain.RaretyType;
import esgi.domain.SpecialityType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HerosAddDto {
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    private SpecialityType speciality;

    @NotBlank
    private RaretyType rarety;
}
