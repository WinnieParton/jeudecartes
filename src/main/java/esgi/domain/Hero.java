package esgi.domain;

import java.io.Serializable;
import java.sql.Date;
public class Hero implements Serializable {
   
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    private String name;

   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int nbLifePoints;

  
    private Integer experience = 0;

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    private int power;

 
    private int armor;

  
    private SpecialityType speciality;


    private RaretyType rarity;

    private Integer level = 1;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    private boolean available = true;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    private boolean status = true;

  
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public Hero() {

        //A REVOIR

        //this.name = name;
        //this.speciality = speciality;
        //this.rarity = rarity;
        calculateBaseStatistics();
    }
    
    public final int min = 3;
    public final int max = 50;
  
    
    public void Name(String name, SpecialityType speciality, RaretyType rarity, int min, int max) {
       
        
        this.name = name;
        this.speciality = speciality;
        this.rarity = rarity;
        calculateBaseStatistics();
    }  


    public void calculateBaseStatistics() {
        // initial base statistics for each speciality
        if (this.speciality.equals(SpecialityType.Tank)) {
            this.nbLifePoints = 1000;
            this.power = 100;
            this.armor = 20;
        } else if (this.speciality.equals(SpecialityType.Assassin)) {
            this.nbLifePoints = 800;
            this.power = 200;
            this.armor = 5;
        } else if (this.speciality.equals(SpecialityType.Mage)) {
            this.nbLifePoints = 700;
            this.power = 150;
            this.armor = 10;
        }

        // apply rarity bonuses
        if (this.rarity.equals(RaretyType.commun)) {
            // no bonus
        } else if (this.rarity.equals(RaretyType.rare)) {
            this.nbLifePoints += this.nbLifePoints * 0.1;
            this.power += this.power * 0.1;
            this.armor += this.armor * 0.1;
        } else if (this.rarity.equals(RaretyType.legendary)) {
            this.nbLifePoints += this.nbLifePoints * 0.2;
            this.power += this.power * 0.2;
            this.armor += this.armor * 0.2;
        }
    }

}
