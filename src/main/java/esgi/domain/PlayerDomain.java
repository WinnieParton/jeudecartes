package esgi.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDomain {
    private Long id;

    private String pseudo;

    private Integer jeton = 4;

    private DeckDomain deck;

    private Integer nbrTirage = 0;

    private Integer nbrTiragePackArgent = 0;

    private Integer nbrTiragePackDiament = 0;

    private Date createdAt;

    private Date updatedAt;

    public PlayerDomain(Long id, String pseudo, Date createdAt, Date updatedAt) {
        this.id = id;
        this.pseudo = pseudo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
