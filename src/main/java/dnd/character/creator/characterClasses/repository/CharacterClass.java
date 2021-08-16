package dnd.character.creator.characterClasses.repository;

import dnd.character.creator.characters.repository.DnDCharacter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classes")
public class CharacterClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "health_point")
    private int healthPoint;
    @Column(name = "attack_damage")
    private int attackDamage;
    @OneToMany(mappedBy="characterClass")
    private List<DnDCharacter> characters;

    public CharacterClass(String name, int healthPoint, int attackDamage) {
        this.name = name;
        this.healthPoint = healthPoint;
        this.attackDamage = attackDamage;
    }
}
