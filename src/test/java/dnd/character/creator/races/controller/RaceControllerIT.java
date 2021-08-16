package dnd.character.creator.races.controller;

import dnd.character.creator.characterClasses.dto.CharacterClassDto;
import dnd.character.creator.characterClasses.dto.CreateCharacterClassCommand;
import dnd.character.creator.races.dto.CreateRaceCommand;
import dnd.character.creator.races.dto.RaceDto;
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
@Sql(statements = "delete from races")
class RaceControllerIT {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void testCreateRace() {
        RaceDto characterClassDto =
                testRestTemplate.postForObject("/api/races", new CreateRaceCommand("Test",  10, 10), RaceDto.class);
        assertEquals("Test", characterClassDto.getName());
    }
    @Test
    void testListRace() {
        testRestTemplate.postForObject("/api/races", new CreateRaceCommand("Test Class 1", 10, 10), RaceDto.class);
        testRestTemplate.postForObject("/api/races", new CreateRaceCommand("Test Class 2", 10, 10), RaceDto.class);

        List<RaceDto> characterClasses = testRestTemplate.exchange("/api/races",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RaceDto>>() {
                }).getBody();

        assertThat(characterClasses)
                .extracting(RaceDto::getName)
                .containsExactly("Test Class 1", "Test Class 2");
    }

    @Test
    void testUpdateRace() {
        RaceDto characterClassDto = testRestTemplate.postForObject("/api/races", new CreateRaceCommand("Test Race 1", 10, 10), RaceDto.class);
        long id = characterClassDto.getId();
        RaceDto characterClassDto2 = testRestTemplate.getForObject("/api/races/" + id, RaceDto.class);
        assertEquals("Test Race 1", characterClassDto2.getName());
        Map<String, String> param = new HashMap<>();
        param.put("name", "Test Race 2");
        testRestTemplate.put("/api/races/" + id, param);
        RaceDto characterClassDto3 = testRestTemplate.getForObject("/api/races/" + id, RaceDto.class);
        assertEquals("Test Race 2", characterClassDto3.getName());
    }

    @Test
    void testDeleteRace() {
        RaceDto raceDto = testRestTemplate.postForObject("/api/races", new CreateRaceCommand("Test Race 1", 10, 10), RaceDto.class);
        long id = raceDto.getId();
        RaceDto raceDto2 = testRestTemplate.getForObject("/api/races/" + id, RaceDto.class);
        assertEquals("Test Race 1", raceDto2.getName());
        testRestTemplate.delete("/api/races/" + id);
        ResponseEntity<String> response = testRestTemplate.
                getForEntity("/api/races/" + id, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}