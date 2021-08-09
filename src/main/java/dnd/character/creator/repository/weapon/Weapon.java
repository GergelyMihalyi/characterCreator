package dnd.character.creator.repository.weapon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private WeaponType weaponType;
    private int damage;
    private int weight;

    public Weapon(String name, WeaponType weaponType, int damage, int weight) {
        this.name = name;
        this.weaponType = weaponType;
        this.damage = damage;
        this.weight = weight;
    }
}
