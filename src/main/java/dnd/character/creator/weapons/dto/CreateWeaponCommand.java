package dnd.character.creator.weapons.dto;

import dnd.character.creator.weapons.repository.WeaponType;
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
public class CreateWeaponCommand {

    @Schema(description = "name of the weapon", example = "Orc Axe")
    @Name(maxLength = 30)
    private String name;
    @Schema(description = "type of the weapon", example = "CRUSHING")
    private WeaponType weaponType;
    @Schema(description = "damage of the weapon", example = "10")
    @Min(value=1, message="positive number, min 1 is required")
    @Max(value=12, message="positive number, max 12")
    private int damage;
    @Schema(description = "weight of the weapon", example = "10")
    @Min(value=10, message="positive number, min 10 is required")
    @Max(value=20, message="positive number, max 20")
    private int weight;
}
