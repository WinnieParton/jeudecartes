package esgi.domain;

import java.sql.Date;

public class Combat {
   
    private Long id;

   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Hero attackingHero;

  
    public Hero getAttackingHero() {
        return attackingHero;
    }

    public void setAttackingHero(Hero attackingHero) {
        this.attackingHero = attackingHero;
    }

    private Hero defendingHero;

    public Hero getDefendingHero() {
        return defendingHero;
    }

    public void setDefendingHero(Hero defendingHero) {
        this.defendingHero = defendingHero;
    }

    private int damageAttackerHero;

    public int getDamageAttackerHero() {
        return damageAttackerHero;
    }

    public void setDamageAttackerHero(int damageAttackerHero) {
        this.damageAttackerHero = damageAttackerHero;
    }

    private int damageDefenderHero;

    public int getDamageDefenderHero() {
        return damageDefenderHero;
    }

    public void setDamageDefenderHero(int damageDefenderHero) {
        this.damageDefenderHero = damageDefenderHero;
    }

    private int newLifePointsAttacker;

    public int getNewLifePointsAttacker() {
        return newLifePointsAttacker;
    }

    public void setNewLifePointsAttacker(int newLifePointsAttacker) {
        this.newLifePointsAttacker = newLifePointsAttacker;
    }

    private int newLifePointsDefender;

    public int getNewLifePointsDefender() {
        return newLifePointsDefender;
    }

    public void setNewLifePointsDefender(int newLifePointsDefender) {
        this.newLifePointsDefender = newLifePointsDefender;
    }

    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
