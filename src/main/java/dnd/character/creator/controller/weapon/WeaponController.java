package dnd.character.creator.controller.weapon;


import dnd.character.creator.dto.weapon.CreateWeaponCommand;
import dnd.character.creator.dto.weapon.WeaponDto;
import dnd.character.creator.service.weapon.WeaponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weapons")
@Tag(name = "Operations on characters")
public class WeaponController {

    private WeaponService weaponService;

    public WeaponController(WeaponService weaponService) {
        this.weaponService = weaponService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a weapon")
    @ApiResponse(responseCode = "201", description = "weapon has been created")
    public WeaponDto createWeapon(@Valid @RequestBody CreateWeaponCommand command) {
        return weaponService.createWeapon(command);
    }

    @GetMapping
    @Operation(summary = "list all weapons")
    @ApiResponse(responseCode = "200", description = "all weapons have been listed")
    public List<WeaponDto> listCharacters(@RequestParam Optional<String> prefix) {
        return weaponService.listWeapons(prefix);
    }


}
