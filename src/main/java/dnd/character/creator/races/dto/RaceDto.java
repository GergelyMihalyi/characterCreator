package dnd.character.creator.races.dto;

import lombok.Data;

@Data
public class RaceDto {
    private Long id;
    private String name;
    private int healthPoint;
    private int attackDamage;
}
