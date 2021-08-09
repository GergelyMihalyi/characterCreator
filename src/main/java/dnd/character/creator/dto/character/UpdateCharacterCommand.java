package dnd.character.creator.dto.character;

import dnd.character.creator.validation.character.Name;
import lombok.Data;

@Data
public class UpdateCharacterCommand {

    @Name
    private String name;
    private int age;
    private int armorClass;
    private int baseAttack;
    private int healthPoint;
}
