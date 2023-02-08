package esgi.infra.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hero implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String name;

    @Min(0)
    @Column(nullable = false)
    private int nbLifePoints;

    @Column(nullable = false)
    @Min(0)
    private Integer experience = 0;

    @Column(nullable = false)
    @Min(1)
    private int power;

    @Min(0)
    @Column(nullable = false)
    private int armor;

    @Column(nullable = false)
    private SpecialityType speciality;

    @Column(nullable = false)
    private RaretyType rarity;

    private Integer level = 1;

    private boolean available = true;

    private boolean status = true;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public Hero(@NotBlank @Size(min = 3, max = 50) String name, SpecialityType speciality, RaretyType rarity) {
        this.name = name;
        this.speciality = speciality;
        this.rarity = rarity;
        calculateBaseStatistics();
    }

    public void calculateBaseStatistics() {
        // initial base statistics for each speciality
        if (this.speciality.equals(SpecialityType.TANK)) {
            this.nbLifePoints = 1000;
            this.power = 100;
            this.armor = 20;
        } else if (this.speciality.equals(SpecialityType.ASSASSIN)) {
            this.nbLifePoints = 800;
            this.power = 200;
            this.armor = 5;
        } else if (this.speciality.equals(SpecialityType.MAGE)) {
            this.nbLifePoints = 700;
            this.power = 150;
            this.armor = 10;
        }

        // apply rarity bonuses
        if (this.rarity.equals(RaretyType.COMMON)) {
            // no bonus
        } else if (this.rarity.equals(RaretyType.RARE)) {
            this.nbLifePoints += this.nbLifePoints * 0.1;
            this.power += this.power * 0.1;
            this.armor += this.armor * 0.1;
        } else if (this.rarity.equals(RaretyType.LEGENDARY)) {
            this.nbLifePoints += this.nbLifePoints * 0.2;
            this.power += this.power * 0.2;
            this.armor += this.armor * 0.2;
        }
    }

}
