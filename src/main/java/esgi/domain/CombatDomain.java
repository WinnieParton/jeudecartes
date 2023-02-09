package esgi.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CombatDomain {

    private Long id;

    private HeroDomain attackingHero;

    private HeroDomain defendingHero;

    private String result;

    private Date createdAt;

    private Date updatedAt;

}
