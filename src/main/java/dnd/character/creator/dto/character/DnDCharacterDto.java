package dnd.character.creator.dto.character;

import dnd.character.creator.dto.item.ItemDto;
import dnd.character.creator.dto.weapon.WeaponDto;
import lombok.Data;

import java.util.List;

@Data
public class DnDCharacterDto {
    private Long id;
    private String name;
    private int age;
    private int armorClass;
    private int baseAttack;
    private int healthPoint;
    private WeaponDto weapon;
    private List<ItemDto> items;
}
