package dnd.character.creator.characters.controller;

import dnd.character.creator.characterClasses.dto.CreateCharacterClassCommand;
import dnd.character.creator.characters.dto.*;
import dnd.character.creator.items.dto.CreateItemCommand;
import dnd.character.creator.races.dto.CreateRaceCommand;
import dnd.character.creator.weapons.dto.CreateWeaponCommand;
import dnd.character.creator.characters.validation.Violation;
import dnd.character.creator.characters.exception.CharacterNotFoundException;
import dnd.character.creator.characters.service.DnDCharactersService;
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
@RequestMapping("/api/characters")
@Tag(name = "Operations on characters")
public class DnDCharactersController {


    private DnDCharactersService characterService;

    public DnDCharactersController(DnDCharactersService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    @Operation(summary = "list all characters")
    @ApiResponse(responseCode = "200", description = "all characters have been listed")
    public List<DnDCharacterDto> listCharacters(@RequestParam Optional<String> prefix) {
        return characterService.listCharacters(prefix);
    }

    @GetMapping("/{id}")
    @Operation(summary = "find a character by id")
    @ApiResponse(responseCode = "200", description = "character has been found")
    public DnDCharacterDto findCharacterById(@PathVariable("id") long id) {
        return characterService.findCharacterById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a character")
    @ApiResponse(responseCode = "201", description = "character has been created")
    public DnDCharacterDto createCharacter(@Valid @RequestBody CreateDnDCharacterCommand command) {
        return characterService.createCharacter(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "updates a character")
    @ApiResponse(responseCode = "200", description = "character has been updated")
    public DnDCharacterDto updateEmployee(@PathVariable("id") long id, @RequestBody UpdateCharacterCommand command) {
        return characterService.updateCharacter(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deletes a character")
    @ApiResponse(responseCode = "204", description = "character has been deleted")
    public void deleteCharacter(@PathVariable("id") long id) {
        characterService.deleteCharacter(id);
    }

    @PostMapping("/{id}/items")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a item and assigned to the character")
    @ApiResponse(responseCode = "201", description = "item has been created and assigned to character")
    public DnDCharacterDto createItemToCharacter(@PathVariable("id") long id,@Valid @RequestBody CreateItemCommand command){
        return characterService.createAndAssignItem(id,command);
    }

    @PutMapping("/{id}/items")
    @Operation(summary = "assign a item to the character")
    @ApiResponse(responseCode = "200", description = "assigned a item to the character")
    public DnDCharacterDto updateCharacterWithExistingItem(@PathVariable("id") long id, @RequestBody UpdateWithExistingItemCommand command) {
        return characterService.updateCharacterWithExistingItem(id, command);
    }

    @PostMapping("/{id}/weapons")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a weapon and assigned to the character")
    @ApiResponse(responseCode = "201", description = "item has been created and assigned to character")
    public DnDCharacterDto createWeaponToCharacter(@PathVariable("id") long id,@Valid @RequestBody CreateWeaponCommand command){
        return characterService.createAndAssignWeapon(id,command);
    }

    @PutMapping("/{id}/weapons")
    @Operation(summary = "assign a weapon to the character")
    @ApiResponse(responseCode = "200", description = "assigned a weapon to the character")
    public DnDCharacterDto updateCharacterWithExistingWeapon(@PathVariable("id") long id, @RequestBody UpdateWithExistingWeaponCommand command) {
        return characterService.updateCharacterWithExistingWeapon(id, command);
    }

    @PostMapping("/{id}/classes")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a class and assigned to the character")
    @ApiResponse(responseCode = "201", description = "class has been created and assigned to character")
    public DnDCharacterDto createCharacterClassToCharacter(@PathVariable("id") long id, @Valid @RequestBody CreateCharacterClassCommand command){
        return characterService.createAndAssignClass(id,command);
    }

    @PutMapping("/{id}/classes")
    @Operation(summary = "assign a race to the character")
    @ApiResponse(responseCode = "200", description = "assigned a class to the character")
    public DnDCharacterDto updateCharacterWithExistingCharacterClass(@PathVariable("id") long id, @RequestBody UpdateWithExistingClassCommand command) {
        return characterService.updateCharacterWithExistingClass(id, command);
    }

    @PostMapping("/{id}/races")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a class and assigned to the character")
    @ApiResponse(responseCode = "201", description = "races has been created and assigned to character")
    public DnDCharacterDto createRaceToCharacter(@PathVariable("id") long id, @Valid @RequestBody CreateRaceCommand command){
        return characterService.createAndAssignRace(id,command);
    }

    @PutMapping("/{id}/races")
    @Operation(summary = "assign a race to the character")
    @ApiResponse(responseCode = "200", description = "assigned a race to the character")
    public DnDCharacterDto updateCharacterWithExistingRace(@PathVariable("id") long id, @RequestBody UpdateWithExistingRaceCommand command) {
        return characterService.updateCharacterWithExistingRace(id, command);
    }

    @PostMapping("/{id}/level")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "character level up ( +10dmg, +10hp)")
    @ApiResponse(responseCode = "201", description = "character level up ( +10dmg, +10hp)")
    public DnDCharacterDto characterLevelUp(@PathVariable("id") long id){
        return characterService.levelUp(id);
    }

    @ExceptionHandler(CharacterNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(CharacterNotFoundException iae) {
        Problem problem = Problem.builder()
                .withType(URI.create("characters/not-found"))
                .withTitle("not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(iae.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleValidException(MethodArgumentNotValidException exception) {
        List<Violation> violations =
                exception.getBindingResult().getFieldErrors().stream()
                        .map(fe -> new Violation(fe.getField(), fe.getDefaultMessage()))
                        .collect(Collectors.toList());
        Problem problem =
                Problem.builder()
                        .withType(URI.create("character/not-valid"))
                        .withTitle("Validation error")
                        .withStatus(Status.BAD_REQUEST)
                        .withDetail(exception.getMessage())
                        .with("violations", violations)
                        .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);

    }

}
