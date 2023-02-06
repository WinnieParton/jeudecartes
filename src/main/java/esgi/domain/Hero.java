package esgi.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Hero {
    private Long id;

    private String name;

    private int nbLifePoints;

    private Integer experience = 0;

    private int power;

    private int armor;

    private SpecialityType speciality;

    private RaretyType rarity;

    private Integer level = 1;

    private boolean available = true;

    private boolean status = true;

    private Date createdAt;

    private Date updatedAt;

    public Hero(String name, SpecialityType speciality, RaretyType rarity) {
        this.name = name;
        this.speciality = speciality;
        this.rarity = rarity;
    }

    
}
