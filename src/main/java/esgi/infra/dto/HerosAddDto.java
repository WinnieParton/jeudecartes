package esgi.infra.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HerosAddDto {
    @NotBlank(message = "Name can not blank")
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    private SpecialityTypeDomain speciality;

    @NotBlank
    private RaretyTypeDomain rarety;
}
