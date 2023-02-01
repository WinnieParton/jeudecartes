package esgi.infra.entity;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "combats")
public class Combat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Hero attackingHero;

    @OneToOne
    private Hero defendingHero;

    private int damageAttackerHero;

    private int damageDefenderHero;

    private int newLifePointsAttacker;

    private int newLifePointsDefender;

    private String result;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public Combat(Hero attackingHero, Hero defendingHero, int damageAttackerHero, int damageDefenderHero,
            int newLifePointsAttacker, int newLifePointsDefender, String result) {
        this.attackingHero = attackingHero;
        this.defendingHero = defendingHero;
        this.damageAttackerHero = damageAttackerHero;
        this.damageDefenderHero = damageDefenderHero;
        this.newLifePointsAttacker = newLifePointsAttacker;
        this.newLifePointsDefender = newLifePointsDefender;
        this.result = result;
    }

}
