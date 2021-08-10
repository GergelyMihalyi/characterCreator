package dnd.character.creator.dto.character;

import dnd.character.creator.dto.item.ItemDto;
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
    private List<ItemDto> items;
}
