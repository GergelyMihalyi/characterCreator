package dnd.character.creator.characterClasses.dto;

import lombok.Data;

@Data
public class UpdateCharacterClassCommand {

    private String name;
    private int healthPoint;
    private int attackDamage;
}
