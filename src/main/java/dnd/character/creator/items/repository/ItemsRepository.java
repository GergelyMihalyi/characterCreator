package dnd.character.creator.items.repository;

import dnd.character.creator.items.repository.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Item, Long> {
}
