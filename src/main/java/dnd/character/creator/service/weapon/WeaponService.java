package dnd.character.creator.service.weapon;

import dnd.character.creator.dto.character.CreateDnDCharacterCommand;
import dnd.character.creator.dto.character.DnDCharacterDto;
import dnd.character.creator.dto.character.UpdateCharacterCommand;
import dnd.character.creator.dto.weapon.CreateWeaponCommand;
import dnd.character.creator.dto.weapon.WeaponDto;
import dnd.character.creator.exception.CharacterNotFoundException;
import dnd.character.creator.repository.character.DnDCharacter;
import dnd.character.creator.repository.character.DnDCharactersRepository;
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
public class WeaponService {

    private ModelMapper modelMapper;
    private WeaponRepository repository;

    public WeaponDto createWeapon(CreateWeaponCommand command) {
        Weapon weapon = new Weapon(command.getName(),command.getWeaponType(),command.getDamage(),command.getWeight());
        repository.save(weapon);
        return modelMapper.map(weapon, WeaponDto.class);
    }

}

