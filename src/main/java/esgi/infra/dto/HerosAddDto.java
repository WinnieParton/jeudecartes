package esgi.infra.dto;

import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class HerosAddDto {
    @NotNull(message = "Name can not null")
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    private SpecialityTypeDomain speciality;

    @NotBlank
    private RaretyTypeDomain rarety;
}
