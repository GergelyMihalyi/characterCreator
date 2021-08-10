package dnd.character.creator.controller.item;

import dnd.character.creator.dto.item.CreateItemCommand;
import dnd.character.creator.dto.item.ItemDto;
import dnd.character.creator.dto.item.UpdateItemCommand;
import dnd.character.creator.exception.ItemNotFoundException;
import dnd.character.creator.service.item.ItemService;
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
@RequestMapping("/api/items")
@Tag(name = "Operations on items")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a item")
    @ApiResponse(responseCode = "201", description = "item has been created")
    public ItemDto createItem(@Valid @RequestBody CreateItemCommand command) {
        return itemService.createItem(command);
    }

    @GetMapping("/{id}")
    @Operation(summary = "find a item by id")
    @ApiResponse(responseCode = "200", description = "item has been found")
    public ItemDto findItemById(@PathVariable("id") long id) {
        return itemService.findItemById(id);
    }

    @GetMapping
    @Operation(summary = "list all items")
    @ApiResponse(responseCode = "200", description = "all items have been listed")
    public List<ItemDto> listItems(@RequestParam Optional<String> prefix) {
        return itemService.listItems(prefix);
    }

    @PutMapping("/{id}")
    @Operation(summary = "updates a item")
    @ApiResponse(responseCode = "200", description = "item has been updated")
    public ItemDto updateItem(@PathVariable("id") long id, @RequestBody UpdateItemCommand command) {
        return itemService.updateItem(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deletes a item")
    @ApiResponse(responseCode = "204", description = "item has been deleted")
    public void deleteItem(@PathVariable("id") long id) {
        itemService.deleteItem(id);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(ItemNotFoundException iae) {
        Problem problem = Problem.builder()
                .withType(URI.create("items/not-found"))
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
