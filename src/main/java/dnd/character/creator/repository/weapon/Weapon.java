package dnd.character.creator.repository.weapon;

import dnd.character.creator.repository.character.DnDCharacter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "weapons")
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "weapon_type")
    @Enumerated(EnumType.STRING)
    private WeaponType weaponType;
    private int damage;
    private int weight;
    @OneToMany(mappedBy="weapon")
    private List<DnDCharacter> characters;

    public Weapon(String name, WeaponType weaponType, int damage, int weight) {
        this.name = name;
        this.weaponType = weaponType;
        this.damage = damage;
        this.weight = weight;
    }
}
