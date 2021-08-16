package dnd.character.creator.races.dto;

import lombok.Data;

@Data
public class UpdateRaceCommand {

    private String name;
    private int healthPoint;
    private int attackDamage;
}
