package dnd.character.creator.service.character;

import dnd.character.creator.dto.character.*;
import dnd.character.creator.dto.item.CreateItemCommand;
import dnd.character.creator.dto.weapon.CreateWeaponCommand;
import dnd.character.creator.exception.CharacterNotFoundException;
import dnd.character.creator.repository.character.DnDCharacter;
import dnd.character.creator.repository.character.DnDCharactersRepository;
import dnd.character.creator.repository.item.Item;
import dnd.character.creator.repository.item.ItemsRepository;
import dnd.character.creator.repository.weapon.Weapon;
import dnd.character.creator.repository.weapon.WeaponRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DnDCharactersService {

    private ModelMapper modelMapper;
    private DnDCharactersRepository repository;
    private ItemsRepository itemsRepository;
    private WeaponRepository weaponRepository;

    public List<DnDCharacterDto> listCharacters(Optional<String> prefix) {
        return repository.findAll().stream()
                .filter(e -> prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                .map(e -> modelMapper.map(e, DnDCharacterDto.class))
                .collect(Collectors.toList());
    }

    public DnDCharacterDto findCharacterById(long id) {
        return modelMapper.map(repository.findById(id)
                        .orElseThrow(() -> new CharacterNotFoundException("character not found")),
                DnDCharacterDto.class);
    }

    public DnDCharacterDto createCharacter(CreateDnDCharacterCommand command) {
        DnDCharacter character = new DnDCharacter(command.getName(), command.getAge(), command.getArmorClass(), command.getBaseAttack(), command.getHealthPoint());
        repository.save(character);
        return modelMapper.map(character, DnDCharacterDto.class);
    }

    @Transactional
    public DnDCharacterDto updateCharacter(long id, UpdateCharacterCommand command) {
        DnDCharacter character = repository.findById(id).orElseThrow(() -> new CharacterNotFoundException("character not found"));
        character.setName(command.getName());
        return modelMapper.map(character, DnDCharacterDto.class);
    }

    public DnDCharacterDto createAndAssignItem(long id, CreateItemCommand command) {
        DnDCharacter character = repository.findById(id).orElseThrow(() -> new CharacterNotFoundException("character not found"));
        Item item = new Item(command.getName(), command.getDescription());
        item.getCharacters().add(character);
        itemsRepository.save(item);
        return modelMapper.map(character, DnDCharacterDto.class);
    }

    public DnDCharacterDto updateCharacterWithExistingItem(long id, UpdateWithExistingItemCommand command) {
        DnDCharacter character = repository.findById(id).orElseThrow(() -> new CharacterNotFoundException("character not found"));
        Item item = itemsRepository.findById(command.getItemId()).orElseThrow(() -> new CharacterNotFoundException("item found"));
        item.getCharacters().add(character);
        itemsRepository.save(item);
        return modelMapper.map(character, DnDCharacterDto.class);
    }

    public void deleteCharacter(long id) {
        repository.deleteById(id);
    }


    public DnDCharacterDto createAndAssignWeapon(long id, CreateWeaponCommand command) {
        DnDCharacter character = repository.findById(id).orElseThrow(() -> new CharacterNotFoundException("character not found"));
        Weapon weapon = new Weapon(command.getName(), command.getWeaponType(),command.getDamage(),command.getWeight());
        character.setWeapon(weapon);
        repository.save(character);
        return modelMapper.map(character, DnDCharacterDto.class);
    }

    public DnDCharacterDto updateCharacterWithExistingWeapon(long id, UpdateWithExistingWeaponCommand command) {
        DnDCharacter character = repository.findById(id).orElseThrow(() -> new CharacterNotFoundException("character not found"));
        Weapon weapon =  weaponRepository.findById(command.getWeaponId()).orElseThrow(() -> new CharacterNotFoundException("item found"));
        character.setWeapon(weapon);
        repository.save(character);
        return modelMapper.map(character, DnDCharacterDto.class);
    }
}

