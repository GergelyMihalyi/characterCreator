package dnd.character.creator.characters.service;

import dnd.character.creator.characterClasses.dto.CreateCharacterClassCommand;
import dnd.character.creator.characterClasses.repository.CharacterClass;
import dnd.character.creator.characterClasses.repository.CharacterClassRepository;
import dnd.character.creator.characters.dto.*;
import dnd.character.creator.items.dto.CreateItemCommand;
import dnd.character.creator.races.dto.CreateRaceCommand;
import dnd.character.creator.races.repository.Race;
import dnd.character.creator.races.repository.RaceRepository;
import dnd.character.creator.weapons.dto.CreateWeaponCommand;
import dnd.character.creator.characters.exception.CharacterNotFoundException;
import dnd.character.creator.characters.repository.DnDCharacter;
import dnd.character.creator.characters.repository.DnDCharactersRepository;
import dnd.character.creator.items.repository.Item;
import dnd.character.creator.items.repository.ItemsRepository;
import dnd.character.creator.weapons.repository.Weapon;
import dnd.character.creator.weapons.repository.WeaponRepository;
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
    private CharacterClassRepository characterClassRepository;
    private RaceRepository raceRepository;

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
        DnDCharacter character = new DnDCharacter(command.getName(), command.getAge(), command.getBaseAttackDamage(), command.getBaseHealthPoint());
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

    private DnDCharacter searchCharacter(long id){
        DnDCharacter character = repository.findById(id).orElseThrow(() -> new CharacterNotFoundException("character not found"));
        return character;
    }

    public DnDCharacterDto createAndAssignWeapon(long id, CreateWeaponCommand command) {
        DnDCharacter character = searchCharacter(id);
        Weapon weapon = new Weapon(command.getName(), command.getWeaponType(),command.getDamage(),command.getWeight());
        weaponRepository.save(weapon);
        character.setWeapon(weapon);
        repository.save(character);
        return modelMapper.map(character, DnDCharacterDto.class);
    }

    public DnDCharacterDto updateCharacterWithExistingWeapon(long id, UpdateWithExistingWeaponCommand command) {
        DnDCharacter character = searchCharacter(id);
        Weapon weapon =  weaponRepository.findById(command.getWeaponId()).orElseThrow(() -> new CharacterNotFoundException("item found"));
        character.setWeapon(weapon);
        repository.save(character);
        return modelMapper.map(character, DnDCharacterDto.class);
    }

    public DnDCharacterDto createAndAssignClass(long id, CreateCharacterClassCommand command) {
        DnDCharacter character = searchCharacter(id);
        CharacterClass characterClass = new CharacterClass(command.getName(),command.getAttackDamage(),command.getHealthPoint());
        characterClassRepository.save(characterClass);
        character.setCharacterClass(characterClass);
        repository.save(character);
        return modelMapper.map(character, DnDCharacterDto.class);
    }

    public DnDCharacterDto updateCharacterWithExistingClass(long id, UpdateWithExistingClassCommand command) {
        DnDCharacter character =  searchCharacter(id);
        CharacterClass characterClass =  characterClassRepository.findById(command.getClassId()).orElseThrow(() -> new CharacterNotFoundException("item found"));
        character.setCharacterClass(characterClass);
        repository.save(character);
        return modelMapper.map(character, DnDCharacterDto.class);
    }

    public DnDCharacterDto createAndAssignRace(long id, CreateRaceCommand command) {
        DnDCharacter character = searchCharacter(id);
        Race race = new Race(command.getName(),command.getAttackDamage(),command.getHealthPoint());
        raceRepository.save(race);
        character.setRace(race);
        repository.save(character);
        return modelMapper.map(character, DnDCharacterDto.class);
    }

    public DnDCharacterDto updateCharacterWithExistingRace(long id, UpdateWithExistingRaceCommand command) {
        DnDCharacter character =  searchCharacter(id);
        Race race =  raceRepository.findById(command.getRaceId()).orElseThrow(() -> new CharacterNotFoundException("item found"));
        character.setRace(race);
        repository.save(character);
        return modelMapper.map(character, DnDCharacterDto.class);
    }

    public DnDCharacterDto levelUp(long id) {
        DnDCharacter character = searchCharacter(id);
        character.levelUp();
        repository.save(character);
        return modelMapper.map(character, DnDCharacterDto.class);
    }


}

