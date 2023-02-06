package esgi.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Combat {

    private Long id;

    private Hero attackingHero;

    private Hero defendingHero;

    private int damageAttackerHero;

    private int damageDefenderHero;

    private int newLifePointsAttacker;

    private int newLifePointsDefender;

    private String result;

    private Date createdAt;

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
