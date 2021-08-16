package dnd.character.creator.items.service;

import dnd.character.creator.items.dto.CreateItemCommand;
import dnd.character.creator.items.dto.ItemDto;
import dnd.character.creator.items.dto.UpdateItemCommand;
import dnd.character.creator.items.exceptions.ItemNotFoundException;
import dnd.character.creator.items.repository.Item;
import dnd.character.creator.items.repository.ItemsRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemService {

    private ModelMapper modelMapper;
    private ItemsRepository repository;

    public List<ItemDto> listItems(Optional<String> prefix) {
        return repository.findAll().stream()
                .filter(e -> prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                .map(e -> modelMapper.map(e, ItemDto.class))
                .collect(Collectors.toList());
    }

    public ItemDto findItemById(long id) {
        return modelMapper.map(repository.findById(id)
                        .orElseThrow(() -> new ItemNotFoundException("item not found")),
                ItemDto.class);
    }

    public ItemDto createItem(CreateItemCommand command) {
        Item item = new Item(command.getName(), command.getDescription());
        repository.save(item);
        return modelMapper.map(item, ItemDto.class);
    }

    @Transactional
    public ItemDto updateItem(long id, UpdateItemCommand command) {
        Item item = repository.findById(id).orElseThrow(() -> new ItemNotFoundException("item not found"));
        item.setName(command.getName());
        return modelMapper.map(item, ItemDto.class);
    }

    public void deleteItem(long id) {
        repository.deleteById(id);
    }


}

