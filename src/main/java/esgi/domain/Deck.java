package esgi.domain;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Deck {

    private Long id;

    private List<Hero> heros;

    private Date createdAt;

    private Date updatedAt;

    public void addHero(Hero hero) {
        this.heros.add(hero);
    }

    public void addAll(List<Hero> heros) {
        this.heros.addAll(heros);
    }

}
