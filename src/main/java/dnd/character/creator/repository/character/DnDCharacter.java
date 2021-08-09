package dnd.character.creator.repository.character;

import dnd.character.creator.repository.weapon.Weapon;
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
    @OneToOne
    @JoinColumn(name = "weapon_id", referencedColumnName = "id")
    private Weapon weapon;

    public DnDCharacter(String name, int age, int armorClass, int baseAttack, int healthPoint) {
        this.name = name;
        this.age = age;
        this.armorClass = armorClass;
        this.baseAttack = baseAttack;
        this.healthPoint = healthPoint;
    }

    public DnDCharacter(String name, int healthPoint) {
        this.name = name;
        this.healthPoint = healthPoint;
    }
}
