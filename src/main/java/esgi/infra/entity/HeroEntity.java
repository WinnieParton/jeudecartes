package esgi.infra.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import esgi.domain.RaretyTypeDomain;
import esgi.domain.SpecialityTypeDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "heros")
@NoArgsConstructor
public class HeroEntity implements Serializable {
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
    private SpecialityTypeDomain speciality;

    @Column(nullable = false)
    private RaretyTypeDomain rarity;

    private Integer level = 1;

    private boolean available = true;

    private boolean status = true;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public HeroEntity(@NotBlank @Size(min = 3, max = 50) String name, SpecialityTypeDomain speciality, RaretyTypeDomain rarity) {
        this.name = name;
        this.speciality = speciality;
        this.rarity = rarity;
    }

}
