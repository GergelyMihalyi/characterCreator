package dnd.character.creator.characterClasses.dto;

import lombok.Data;

@Data
public class CharacterClassDto {
    private Long id;
    private String name;
    private int healthPoint;
    private int attackDamage;
}
