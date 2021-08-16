package dnd.character.creator.characterClasses.controller;

import dnd.character.creator.characterClasses.dto.CharacterClassDto;
import dnd.character.creator.characterClasses.dto.CreateCharacterClassCommand;
import dnd.character.creator.weapons.dto.CreateWeaponCommand;
import dnd.character.creator.weapons.dto.WeaponDto;
import dnd.character.creator.weapons.repository.WeaponType;
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
@Sql(statements = "delete from classes")
class CharacterClassControllerIT {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void testCreateCharacterClass() {
        CharacterClassDto characterClassDto =
                testRestTemplate.postForObject("/api/classes", new CreateCharacterClassCommand("Test",  10, 10), CharacterClassDto.class);
        assertEquals("Test", characterClassDto.getName());
    }
    @Test
    void testListCharacterClass() {
        testRestTemplate.postForObject("/api/classes", new CreateCharacterClassCommand("Test Class 1", 10, 10), CharacterClassDto.class);
        testRestTemplate.postForObject("/api/classes", new CreateCharacterClassCommand("Test Class 2", 10, 10), CharacterClassDto.class);

        List<CharacterClassDto> characterClasses = testRestTemplate.exchange("/api/classes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CharacterClassDto>>() {
                }).getBody();

        assertThat(characterClasses)
                .extracting(CharacterClassDto::getName)
                .containsExactly("Test Class 1", "Test Class 2");
    }

    @Test
    void testUpdateCharacterClass() {
        CharacterClassDto characterClassDto = testRestTemplate.postForObject("/api/classes", new CreateCharacterClassCommand("Test Class 1", 10, 10), CharacterClassDto.class);
        long id = characterClassDto.getId();
        CharacterClassDto characterClassDto2 = testRestTemplate.getForObject("/api/classes/" + id, CharacterClassDto.class);
        assertEquals("Test Class 1", characterClassDto2.getName());
        Map<String, String> param = new HashMap<>();
        param.put("name", "Test Class 2");
        testRestTemplate.put("/api/classes/" + id, param);
        CharacterClassDto characterClassDto3 = testRestTemplate.getForObject("/api/classes/" + id, CharacterClassDto.class);
        assertEquals("Test Class 2", characterClassDto3.getName());
    }

    @Test
    void testDeleteCharacterClass() {
        CharacterClassDto characterClassDto = testRestTemplate.postForObject("/api/classes", new CreateCharacterClassCommand("Test Class 1", 10, 10), CharacterClassDto.class);
        long id = characterClassDto.getId();
        CharacterClassDto characterClassDto2 = testRestTemplate.getForObject("/api/classes/" + id, CharacterClassDto.class);
        assertEquals("Test Class 1", characterClassDto2.getName());
        testRestTemplate.delete("/api/classes/" + id);
        ResponseEntity<String> response = testRestTemplate.
                getForEntity("/api/classes/" + id, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}