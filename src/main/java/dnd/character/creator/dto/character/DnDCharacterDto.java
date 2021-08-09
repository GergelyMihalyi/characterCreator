package dnd.character.creator.dto.character;

import lombok.Data;

@Data
public class DnDCharacterDto {
    private Long id;
    private String name;
    private int age;
    private int armorClass;
    private int baseAttack;
    private int healthPoint;
}
