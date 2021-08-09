package dnd.character.creator.dto.character;

import dnd.character.creator.validation.character.Name;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDnDCharacterCommand {

    @Schema(description = "name of the character", example = "Test Character")
    @Name
    private String name;
    @Schema(description = "age of the character", example = "30")
    @Min(value=10, message="positive number, min 10 is required")
    @Max(value=150, message="positive number, max 150")
    private int age;
    @Schema(description = "armor class of the character", example = "10")
    @Min(value=10, message="positive number, min 10 is required")
    @Max(value=20, message="positive number, max 20")
    private int armorClass;
    @Schema(description = "base attack modifier of the character", example = "10")
    @Min(value=10, message="positive number, min 10 is required")
    @Max(value=20, message="positive number, max 20")
    private int baseAttack;
    @Schema(description = "max health point of the character", example = "100")
    @Min(value=50, message="positive number, min 50 is required")
    @Max(value=100, message="positive number, max 100")
    private int healthPoint;
}
