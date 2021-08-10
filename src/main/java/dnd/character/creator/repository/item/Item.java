package dnd.character.creator.repository.item;

import dnd.character.creator.repository.character.DnDCharacter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(name = "character_item",
            joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "character_id", referencedColumnName = "id"))
    private List<DnDCharacter> characters = new ArrayList<>();

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Item(String name) {
        this.name = name;
    }
}
