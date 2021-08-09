package dnd.character.creator.repository.weapon;

import dnd.character.creator.repository.character.DnDCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponRepository extends JpaRepository<Weapon, Long> {
}
