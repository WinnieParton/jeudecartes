package esgi.infra.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "combats")
public class CombatEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private HeroEntity attackingHero;

    @OneToOne
    private HeroEntity defendingHero;

    private String result;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CombatHistoryEntity> combatHistories = new ArrayList<CombatHistoryEntity>();

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public CombatEntity(HeroEntity attackingHero, HeroEntity defendingHero) {
        this.attackingHero = attackingHero;
        this.defendingHero = defendingHero;
    }

    public void addCombatHistory(CombatHistoryEntity combatHistory) {
        this.combatHistories.add(combatHistory);
    }

}
