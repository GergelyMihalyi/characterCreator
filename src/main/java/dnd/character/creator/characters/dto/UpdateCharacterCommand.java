package dnd.character.creator.characters.dto;

import dnd.character.creator.characters.validation.Name;
import lombok.Data;

@Data
public class UpdateCharacterCommand {

    @Name
    private String name;
    private int age;
}
