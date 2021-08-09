package dnd.character.creator.dto.weapon;

import dnd.character.creator.repository.weapon.WeaponType;
import lombok.Data;

@Data
public class WeaponDto {
    private Long id;
    private String name;
    private WeaponType weaponType;
    private int damage;
    private int weight;
}
