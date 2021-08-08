package dnd.character.creator.repository.character;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DnDCharactersRepository extends JpaRepository<DnDCharacter, Long> {
}
