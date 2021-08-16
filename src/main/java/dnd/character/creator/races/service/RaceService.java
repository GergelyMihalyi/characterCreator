package dnd.character.creator.races.service;

import dnd.character.creator.characterClasses.exceptions.CharacterClassNotFoundException;
import dnd.character.creator.races.dto.CreateRaceCommand;
import dnd.character.creator.races.dto.RaceDto;
import dnd.character.creator.races.dto.UpdateRaceCommand;
import dnd.character.creator.races.exceptions.RaceNotFoundException;
import dnd.character.creator.races.repository.Race;
import dnd.character.creator.races.repository.RaceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RaceService {

    private ModelMapper modelMapper;
    private RaceRepository repository;

    public RaceDto createRace(CreateRaceCommand command) {
        Race race = new Race(command.getName(), command.getHealthPoint(),command.getAttackDamage());
        repository.save(race);
        return modelMapper.map(race, RaceDto.class);
    }

    public List<RaceDto> listRaces(Optional<String> prefix) {
        return repository.findAll().stream()
                .filter(e -> prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                .map(e -> modelMapper.map(e, RaceDto.class))
                .collect(Collectors.toList());
    }

    public RaceDto findRaceById(long id) {
        return modelMapper.map(repository.findById(id)
                        .orElseThrow(() -> new RaceNotFoundException("race not found")),
                RaceDto.class);
    }

    @Transactional
    public RaceDto updateRace(long id, UpdateRaceCommand command) {
        Race race = repository.findById(id).orElseThrow(() -> new RaceNotFoundException("race not found"));
        race.setName(command.getName());
        return modelMapper.map(race, RaceDto.class);
    }

    public void deleteRace(long id) {
        repository.deleteById(id);
    }

}

