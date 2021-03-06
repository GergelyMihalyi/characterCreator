package dnd.character.creator.weapons.controller;

import dnd.character.creator.weapons.dto.CreateWeaponCommand;
import dnd.character.creator.weapons.dto.UpdateWeaponCommand;
import dnd.character.creator.weapons.dto.WeaponDto;
import dnd.character.creator.weapons.exceptions.WeaponNotFoundException;
import dnd.character.creator.weapons.service.WeaponService;
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
@Tag(name = "Operations on weapons")
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

    @PutMapping("/{id}")
    @Operation(summary = "updates a weapon")
    @ApiResponse(responseCode = "200", description = "weapon has been updated")
    public WeaponDto updateWeapon(@PathVariable("id") long id, @RequestBody UpdateWeaponCommand command) {
        return weaponService.updateWeapon(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deletes a weapon")
    @ApiResponse(responseCode = "204", description = "weapon has been deleted")
    public void deleteWeapon(@PathVariable("id") long id) {
        weaponService.deleteWeapon(id);
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
