package dnd.character.creator.dto.character;

import lombok.Data;

@Data
public class UpdateWithExistingWeaponCommand {
    private long weaponId;
}
