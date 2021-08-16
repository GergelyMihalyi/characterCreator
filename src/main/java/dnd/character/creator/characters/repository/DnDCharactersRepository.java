package dnd.character.creator.characters.repository;

import dnd.character.creator.characters.repository.DnDCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DnDCharactersRepository extends JpaRepository<DnDCharacter, Long> {
}
