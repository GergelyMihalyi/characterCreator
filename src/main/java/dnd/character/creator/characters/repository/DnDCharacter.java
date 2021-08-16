package dnd.character.creator.characters.repository;

import dnd.character.creator.items.repository.Item;
import dnd.character.creator.weapons.repository.Weapon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "base_attack_damage")
    private int baseAttackDamage;
    @Column(name = "base_health_point")
    private int baseHealthPoint;
    @Column(name = "actual_health_point")
    private int actualHealthPoint;
    @Column(name = "experience")
    private int experience;
    @Column(name = "level")
    private int level;
    @ManyToOne
    @JoinColumn(name = "weapon_id", referencedColumnName = "id")
    private Weapon weapon;
    @ManyToMany(mappedBy = "characters")
    private List<Item> items = new ArrayList<>();

    public DnDCharacter(String name, int age, int baseAttackDamage, int baseHealthPoint) {
        this.name = name;
        this.age = age;
        this.baseAttackDamage = baseAttackDamage;
        this.baseHealthPoint = baseHealthPoint;
        this.actualHealthPoint = baseHealthPoint;
        this.experience = 0;
        this.level = 1;
    }

    public DnDCharacter(String name, int baseHealthPoint) {
        this.name = name;
        this.baseHealthPoint = baseHealthPoint;
        this.experience = 0;
        this.level = 1;
    }
}
