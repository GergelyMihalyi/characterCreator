package dnd.character.creator.characters.dto;

import lombok.Data;

@Data
public class UpdateWithExistingItemCommand {
    private long itemId;
}
