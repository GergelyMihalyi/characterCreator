package dnd.character.creator.dto.character;

import lombok.Data;

@Data
public class UpdateWithExistingItemCommand {
    private long itemId;
}
