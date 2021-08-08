package dnd.character.creator.dto.character;

import dnd.character.creator.validation.character.Name;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDnDCharacterCommand {

    @Schema(description = "name of the character", example = "Test Character")
    @Name
    private String name;
    @Schema(description = "age of the character", example = "30")
    private int age;
    @Schema(description = "armor class of the character", example = "10")
    private int armorClass;
    @Schema(description = "base attack modifier of the character", example = "10")
    private int baseAttack;
    @Schema(description = "max health point of the character", example = "100")
    private int healthPoint;
}