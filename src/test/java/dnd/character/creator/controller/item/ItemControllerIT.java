package dnd.character.creator.controller.item;

import dnd.character.creator.items.dto.CreateItemCommand;
import dnd.character.creator.items.dto.ItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from character_item", "delete from items"})
class ItemControllerIT {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void testCreateItem() {
        ItemDto itemDto =
                testRestTemplate.postForObject("/api/items", new CreateItemCommand("Test Item", "Test Description"), ItemDto.class);
        assertEquals("Test Item", itemDto.getName());
        assertEquals("Test Description", itemDto.getDescription());

    }

    @Test
    void testListItems() {
        testRestTemplate.postForObject("/api/items", new CreateItemCommand("Test Item 1", "Test Description"), ItemDto.class);
        testRestTemplate.postForObject("/api/items", new CreateItemCommand("Test Item 2", "Test Description"), ItemDto.class);

        List<ItemDto> items = testRestTemplate.exchange("/api/items",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ItemDto>>() {
                }).getBody();

        assertThat(items)
                .extracting(ItemDto::getName)
                .containsExactly("Test Item 1", "Test Item 2");
    }

    @Test
    void testUpdateItem() {
        ItemDto itemDto = testRestTemplate.postForObject("/api/items", new CreateItemCommand("Test Item 1", "Test Description"), ItemDto.class);
        long id = itemDto.getId();
        ItemDto itemDto2 = testRestTemplate.getForObject("/api/items/" + id, ItemDto.class);
        assertEquals("Test Item 1", itemDto2.getName());
        Map<String, String> param = new HashMap<String, String>();
        param.put("name", "Test Item 2");
        param.put("description", "Test Item 2");
        testRestTemplate.put("/api/items/" + id, param);
        ItemDto itemDto3 = testRestTemplate.getForObject("/api/items/" + id, ItemDto.class);
        assertEquals("Test Item 2", itemDto3.getName());
        assertEquals("Test Item 2", itemDto3.getDescription());
    }

    @Test
    void testDeleteItem() {
        ItemDto itemDto = testRestTemplate.postForObject("/api/items", new CreateItemCommand("Test Item 1", "Test Description"), ItemDto.class);
        long id = itemDto.getId();
        ItemDto itemDto2 = testRestTemplate.getForObject("/api/items/" + id, ItemDto.class);
        assertEquals("Test Item 1", itemDto2.getName());
        testRestTemplate.delete("/api/items/" + id);
        ResponseEntity<String> response = testRestTemplate.
                getForEntity("/api/items/" + id, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}