package esgi.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Player {
    private Long id;

    private String pseudo;

    private Integer jeton = 4;

    private Deck deck;

    private Integer nbrTirage = 0;

    private Integer nbrTiragePackArgent = 0;

    private Integer nbrTiragePackDiament = 0;

    private Date createdAt;

    private Date updatedAt;

    public Player(String pseudo) {
        this.pseudo = pseudo;
    }

    public Player(Long id, String pseudo, Date createdAt, Date updatedAt) {
        this.id = id;
        this.pseudo = pseudo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
