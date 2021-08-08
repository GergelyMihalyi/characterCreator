package dnd.character.creator.service.character;

import dnd.character.creator.dto.character.CreateDnDCharacterCommand;
import dnd.character.creator.dto.character.UpdateCharacterCommand;
import dnd.character.creator.exception.CharacterNotFoundException;
import dnd.character.creator.repository.character.DnDCharacter;
import dnd.character.creator.dto.character.DnDCharacterDto;
import dnd.character.creator.repository.character.DnDCharactersRepository;
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

    public void deleteCharacter(long id) {
        repository.deleteById(id);
    }


}

