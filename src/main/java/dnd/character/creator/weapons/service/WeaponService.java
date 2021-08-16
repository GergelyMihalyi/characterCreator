package dnd.character.creator.weapons.service;

import dnd.character.creator.weapons.dto.CreateWeaponCommand;
import dnd.character.creator.weapons.dto.UpdateWeaponCommand;
import dnd.character.creator.weapons.dto.WeaponDto;
import dnd.character.creator.weapons.exceptions.WeaponNotFoundException;
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
public class WeaponService {

    private ModelMapper modelMapper;
    private WeaponRepository repository;

    public WeaponDto createWeapon(CreateWeaponCommand command) {
        Weapon weapon = new Weapon(command.getName(), command.getWeaponType(), command.getDamage(), command.getWeight());
        repository.save(weapon);
        return modelMapper.map(weapon, WeaponDto.class);
    }

    public List<WeaponDto> listWeapons(Optional<String> prefix) {
        return repository.findAll().stream()
                .filter(e -> prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                .map(e -> modelMapper.map(e, WeaponDto.class))
                .collect(Collectors.toList());
    }

    public WeaponDto findWeaponById(long id) {
        return modelMapper.map(repository.findById(id)
                        .orElseThrow(() -> new WeaponNotFoundException("weapon not found")),
                WeaponDto.class);
    }

    @Transactional
    public WeaponDto updateWeapon(long id, UpdateWeaponCommand command) {
        Weapon weapon = repository.findById(id).orElseThrow(() -> new WeaponNotFoundException("character not found"));
        weapon.setName(command.getName());
        return modelMapper.map(weapon, WeaponDto.class);
    }

    public void deleteWeapon(long id) {
        repository.deleteById(id);
    }

}

