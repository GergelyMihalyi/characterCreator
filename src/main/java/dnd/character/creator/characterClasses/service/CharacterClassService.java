package dnd.character.creator.characterClasses.service;

import dnd.character.creator.characterClasses.dto.CharacterClassDto;
import dnd.character.creator.characterClasses.dto.CreateCharacterClassCommand;
import dnd.character.creator.characterClasses.dto.UpdateCharacterClassCommand;
import dnd.character.creator.characterClasses.exceptions.CharacterClassNotFoundException;
import dnd.character.creator.characterClasses.repository.CharacterClass;
import dnd.character.creator.characterClasses.repository.CharacterClassRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CharacterClassService {

    private ModelMapper modelMapper;
    private CharacterClassRepository repository;

    public CharacterClassDto createCharacterClass(CreateCharacterClassCommand command) {
        CharacterClass characterClass = new CharacterClass(command.getName(), command.getHealthPoint(), command.getAttackDamage());
        repository.save(characterClass);
        return modelMapper.map(characterClass, CharacterClassDto.class);
    }

    public List<CharacterClassDto> listCharacterClass(Optional<String> prefix) {
        return repository.findAll().stream()
                .filter(e -> prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                .map(e -> modelMapper.map(e, CharacterClassDto.class))
                .collect(Collectors.toList());
    }

    public CharacterClassDto findCharacterClassById(long id) {
        return modelMapper.map(repository.findById(id)
                        .orElseThrow(() -> new CharacterClassNotFoundException("Class not found")),
                CharacterClassDto.class);
    }

    @Transactional
    public CharacterClassDto updateCharacterClass(long id, UpdateCharacterClassCommand command) {
        CharacterClass characterClass = repository.findById(id).orElseThrow(() -> new CharacterClassNotFoundException("Class not found"));
        characterClass.setName(command.getName());
        return modelMapper.map(characterClass, CharacterClassDto.class);
    }

    public void deleteCharacterClass(long id) {
        repository.deleteById(id);
    }

}

