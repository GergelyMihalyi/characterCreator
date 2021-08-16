package dnd.character.creator.races.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "races")
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "health_point")
    private int healthPoint;
    @Column(name = "attack_damage")
    private int attackDamage;

    public Race(String name, int healthPoint, int attackDamage) {
        this.name = name;
        this.healthPoint = healthPoint;
        this.attackDamage = attackDamage;
    }
}
