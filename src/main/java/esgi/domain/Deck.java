package esgi.domain;

import java.sql.Date;
import java.util.List;


public class Deck {
  
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private List<Hero> heros;

    
    private Date createdAt;

  
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    private Date updatedAt;

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void addHero(Hero hero) {
        this.heros.add(hero);
    }

    public void addAll(List<Hero> heros) {
        this.heros.addAll(heros);
    }
}
