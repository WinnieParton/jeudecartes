package esgi.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

    public HeroDomain(Long id, String name, int nbLifePoints, Integer experience, int power, int armor,
            SpecialityTypeDomain speciality, RaretyTypeDomain rarity, Integer level, boolean available, boolean status,
            Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.nbLifePoints = nbLifePoints;
        this.experience = experience;
        this.power = power;
        this.armor = armor;
        this.speciality = speciality;
        this.rarity = rarity;
        this.level = level;
        this.available = available;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    
}
