package dnd.character.creator.characterClasses.controller;

import dnd.character.creator.characterClasses.dto.CharacterClassDto;
import dnd.character.creator.characterClasses.dto.CreateCharacterClassCommand;
import dnd.character.creator.characterClasses.dto.UpdateCharacterClassCommand;
import dnd.character.creator.characterClasses.exceptions.CharacterClassNotFoundException;
import dnd.character.creator.characterClasses.service.CharacterClassService;
import dnd.character.creator.races.exceptions.RaceNotFoundException;
import dnd.character.creator.weapons.exceptions.WeaponNotFoundException;
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
@RequestMapping("/api/classes")
@Tag(name = "Operations on classes")
public class CharacterClassController {

    private CharacterClassService service;

    public CharacterClassController(CharacterClassService characterClassService) {
        this.service = characterClassService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a weapon")
    @ApiResponse(responseCode = "201", description = "class has been created")
    public CharacterClassDto createCharacterClass(@Valid @RequestBody
    CreateCharacterClassCommand command) {
        return service.createCharacterClass(command);
    }

    @GetMapping("/{id}")
    @Operation(summary = "find a weapon by id")
    @ApiResponse(responseCode = "200", description = "class has been found")
    public CharacterClassDto findCharacterById(@PathVariable("id") long id) {
        return service.findCharacterClassById(id);
    }

    @GetMapping
    @Operation(summary = "list all weapons")
    @ApiResponse(responseCode = "200", description = "all classes have been listed")
    public List<CharacterClassDto> listCharactersClass(@RequestParam Optional<String> prefix) {
        return service.listCharacterClass(prefix);
    }

    @PutMapping("/{id}")
    @Operation(summary = "updates a weapon")
    @ApiResponse(responseCode = "200", description = "classes has been updated")
    public CharacterClassDto updateCharacterClass(@PathVariable("id") long id, @RequestBody UpdateCharacterClassCommand command) {
        return service.updateCharacterClass(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deletes a class")
    @ApiResponse(responseCode = "204", description = "class has been deleted")
    public void deleteCharacterClass(@PathVariable("id") long id) {
        service.deleteCharacterClass(id);
    }

    @ExceptionHandler(CharacterClassNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(CharacterClassNotFoundException iae) {
        Problem problem = Problem.builder()
                .withType(URI.create("character-class/not-found"))
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
