package dnd.character.creator.characters.dto;

import dnd.character.creator.items.dto.ItemDto;
import dnd.character.creator.weapons.dto.WeaponDto;
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
