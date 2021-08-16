package dnd.character.creator.races.controller;

import dnd.character.creator.races.dto.CreateRaceCommand;
import dnd.character.creator.races.dto.RaceDto;
import dnd.character.creator.races.dto.UpdateRaceCommand;
import dnd.character.creator.races.exceptions.RaceNotFoundException;
import dnd.character.creator.races.service.RaceService;
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
@RequestMapping("/api/races")
@Tag(name = "Operations on races")
public class RaceController {

    private RaceService service;

    public RaceController(RaceService raceService) {
        this.service = raceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a race")
    @ApiResponse(responseCode = "201", description = "race has been created")
    public RaceDto createRace(@Valid @RequestBody
                                                          CreateRaceCommand command) {
        return service.createRace(command);
    }

    @GetMapping("/{id}")
    @Operation(summary = "find a race by id")
    @ApiResponse(responseCode = "200", description = "race has been found")
    public RaceDto findCharacterById(@PathVariable("id") long id) {
        return service.findRaceById(id);
    }

    @GetMapping
    @Operation(summary = "list all weapons")
    @ApiResponse(responseCode = "200", description = "all races have been listed")
    public List<RaceDto> listRace(@RequestParam Optional<String> prefix) {
        return service.listRaces(prefix);
    }

    @PutMapping("/{id}")
    @Operation(summary = "updates a weapon")
    @ApiResponse(responseCode = "200", description = "races has been updated")
    public RaceDto updateRace(@PathVariable("id") long id, @RequestBody UpdateRaceCommand command) {
        return service.updateRace(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deletes a class")
    @ApiResponse(responseCode = "204", description = "race has been deleted")
    public void deleteRace(@PathVariable("id") long id) {
        service.deleteRace(id);
    }

    @ExceptionHandler(RaceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(RaceNotFoundException iae) {
        Problem problem = Problem.builder()
                .withType(URI.create("race/not-found"))
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
