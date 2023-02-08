package esgi.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CombatHistory {

    private Long id;

    private int damageAttackerHero;

    private int damageDefenderHero;

    private int newLifePointsAttacker;

    private int newLifePointsDefender;

    private String result;

    private Date createdAt;

    private Date updatedAt;
}
