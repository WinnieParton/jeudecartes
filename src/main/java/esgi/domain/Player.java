package esgi.domain;

import java.sql.Date;


public class Player {
 
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String pseudo;


    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    private Integer jeton = 4;


    public Integer getJeton() {
        return jeton;
    }

    public void setJeton(Integer jeton) {
        this.jeton = jeton;
    }

    private Deck deck;

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    private Integer nbrTirage = 0;

    public Integer getNbrTirage() {
        return nbrTirage;
    }

    public void setNbrTirage(Integer nbrTirage) {
        this.nbrTirage = nbrTirage;
    }

    private Integer nbrTiragePackArgent = 0;

    public Integer getNbrTiragePackArgent() {
        return nbrTiragePackArgent;
    }

    public void setNbrTiragePackArgent(Integer nbrTiragePackArgent) {
        this.nbrTiragePackArgent = nbrTiragePackArgent;
    }

    private Integer nbrTiragePackDiament = 0;

    public Integer getNbrTiragePackDiament() {
        return nbrTiragePackDiament;
    }

    public void setNbrTiragePackDiament(Integer nbrTiragePackDiament) {
        this.nbrTiragePackDiament = nbrTiragePackDiament;
    }

  
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

    int min = 3;
    public int max = 50 ; 

   
    

    public void Pseudo(String pseudo) {
        this.pseudo = pseudo;
    }  

}
