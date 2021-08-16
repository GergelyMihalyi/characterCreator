package dnd.character.creator.weapons.dto;

import dnd.character.creator.weapons.repository.WeaponType;
import lombok.Data;

@Data
public class UpdateWeaponCommand {

    private String name;
    private WeaponType weaponType;
    private int damage;
    private int weight;
}
