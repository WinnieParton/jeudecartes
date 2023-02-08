package esgi.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HeroDomain {
    private Long id;

    private String name;

    private int nbLifePoints;

    private Integer experience = 0;

    private int power;

    private int armor;

    private SpecialityTypeDomain speciality;

    private RaretyTypeDomain rarity;

    private Integer level = 1;

    private boolean available = true;

    private boolean status = true;

    private Date createdAt;

    private Date updatedAt;

    
}
