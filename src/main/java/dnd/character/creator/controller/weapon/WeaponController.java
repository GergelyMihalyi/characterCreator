package dnd.character.creator.controller.weapon;


import dnd.character.creator.dto.character.DnDCharacterDto;
import dnd.character.creator.dto.weapon.CreateWeaponCommand;
import dnd.character.creator.dto.weapon.WeaponDto;
import dnd.character.creator.exception.CharacterNotFoundException;
import dnd.character.creator.exception.WeaponNotFoundException;
import dnd.character.creator.service.weapon.WeaponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
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

    @GetMapping("/{id}")
    @Operation(summary = "find a weapon by id")
    @ApiResponse(responseCode = "200", description = "weapon has been found")
    public WeaponDto findCharacterById(@PathVariable("id") long id) {
        return weaponService.findWeaponById(id);
    }

    @GetMapping
    @Operation(summary = "list all weapons")
    @ApiResponse(responseCode = "200", description = "all weapons have been listed")
    public List<WeaponDto> listCharacters(@RequestParam Optional<String> prefix) {
        return weaponService.listWeapons(prefix);
    }

    @ExceptionHandler(WeaponNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(WeaponNotFoundException iae) {
        Problem problem = Problem.builder()
                .withType(URI.create("weapons/not-found"))
                .withTitle("not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(iae.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }



}
