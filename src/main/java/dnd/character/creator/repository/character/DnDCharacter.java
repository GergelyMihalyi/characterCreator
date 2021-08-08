package dnd.character.creator.repository.character;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "characters")
public class DnDCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    @Column(name = "armor_class")
    private int armorClass;
    @Column(name = "base_attack")
    private int baseAttack;
    @Column(name = "health_point")
    private int healthPoint;

    public DnDCharacter(String name) {
        this.name = name;
    }

    public DnDCharacter(String name, int age, int armorClass, int baseAttack, int healthPoint) {
        this.name = name;
        this.age = age;
        this.armorClass = armorClass;
        this.baseAttack = baseAttack;
        this.healthPoint = healthPoint;
    }
}