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

    private String result;

    private Date createdAt;

    private Date updatedAt;

    public Combat(Hero attackingHero, Hero defendingHero) {
        this.attackingHero = attackingHero;
        this.defendingHero = defendingHero;
    }

}
