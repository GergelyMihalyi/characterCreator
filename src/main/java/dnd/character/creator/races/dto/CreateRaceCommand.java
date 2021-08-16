package dnd.character.creator.races.dto;

import dnd.character.creator.characters.validation.Name;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRaceCommand {

    @Schema(description = "name of the weapon", example = "Orc Axe")
    @Name(maxLength = 30)
    private String name;
    @Schema(description = "health point of the weapon", example = "10")
    @Min(value=1, message="positive number, min 1 is required")
    @Max(value=150, message="positive number, max 12")
    private int healthPoint;
    @Schema(description = "attack damage of the class", example = "10")
    @Min(value=10, message="positive number, min 10 is required")
    @Max(value=20, message="positive number, max 20")
    private int attackDamage;
}
