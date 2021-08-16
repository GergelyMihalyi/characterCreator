package dnd.character.creator.characters.dto;

import dnd.character.creator.characterClasses.dto.CharacterClassDto;
import dnd.character.creator.items.dto.ItemDto;
import dnd.character.creator.races.dto.RaceDto;
import dnd.character.creator.weapons.dto.WeaponDto;
import lombok.Data;

import java.util.List;

@Data
public class DnDCharacterDto {
    private Long id;
    private String name;
    private int age;
    private int baseAttackDamage;
    private int baseHealthPoint;
    private int actual_health_point;
    private int level;
    private WeaponDto weapon;
    private CharacterClassDto characterClass;
    private RaceDto race;
    private List<ItemDto> items;
}
