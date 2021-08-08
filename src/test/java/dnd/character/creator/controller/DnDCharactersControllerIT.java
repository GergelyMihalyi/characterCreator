package dnd.character.creator.controller;

import dnd.character.creator.dto.character.CreateDnDCharacterCommand;
import dnd.character.creator.dto.character.DnDCharacterDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from characters")
class DnDCharactersControllerIT {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void testListCharacters() {
        DnDCharacterDto dnDCharacterDto = testRestTemplate.postForObject("/api/characters", new CreateDnDCharacterCommand("Test1", 10, 10, 10, 10), DnDCharacterDto.class);
        assertEquals("Test1",dnDCharacterDto.getName());

        testRestTemplate.postForObject("/api/characters", new CreateDnDCharacterCommand("Test2", 10, 10, 10, 10), DnDCharacterDto.class);

        List<DnDCharacterDto> characters = testRestTemplate.exchange("/api/characters",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DnDCharacterDto>>() {
                }).getBody();

        assertThat(characters)
                .extracting(DnDCharacterDto::getName)
                .containsExactly("Test1","Test2");

    }

}