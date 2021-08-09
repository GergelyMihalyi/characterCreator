package dnd.character.creator.controller.weapon;

import dnd.character.creator.dto.character.CreateDnDCharacterCommand;
import dnd.character.creator.dto.character.DnDCharacterDto;
import dnd.character.creator.dto.character.UpdateCharacterCommand;
import dnd.character.creator.dto.weapon.CreateWeaponCommand;
import dnd.character.creator.dto.weapon.WeaponDto;
import dnd.character.creator.exception.CharacterNotFoundException;
import dnd.character.creator.service.character.DnDCharactersService;
import dnd.character.creator.service.weapon.WeaponService;
import dnd.character.creator.validation.Violation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

}
