package dnd.character.creator.controller.character;

import dnd.character.creator.validation.character.Violation;
import dnd.character.creator.exception.CharacterNotFoundException;
import dnd.character.creator.service.character.DnDCharactersService;
import dnd.character.creator.dto.character.DnDCharacterDto;
import dnd.character.creator.dto.character.CreateDnDCharacterCommand;
import dnd.character.creator.dto.character.UpdateCharacterCommand;
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
