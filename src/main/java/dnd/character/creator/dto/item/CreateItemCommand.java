package dnd.character.creator.dto.item;

import dnd.character.creator.validation.character.Name;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemCommand {

    @Schema(description = "name of the item", example = "Test Item")
    @Name(maxLength = 30)
    private String name;
    @Schema(description = "description of the item", example = "Example description")
    private String description;

}
