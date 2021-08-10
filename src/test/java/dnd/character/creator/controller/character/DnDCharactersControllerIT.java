package dnd.character.creator.controller.character;

import dnd.character.creator.dto.character.CreateDnDCharacterCommand;
import dnd.character.creator.dto.character.DnDCharacterDto;
import dnd.character.creator.dto.item.CreateItemCommand;
import dnd.character.creator.dto.item.ItemDto;
import dnd.character.creator.dto.weapon.CreateWeaponCommand;
import dnd.character.creator.dto.weapon.WeaponDto;
import dnd.character.creator.repository.weapon.WeaponType;
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
@Sql(statements = {"delete from character_item","delete from characters","delete from weapons","delete from items"})
class DnDCharactersControllerIT {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void testCreateCharacters() {
        DnDCharacterDto dnDCharacterDto =
                testRestTemplate.postForObject("/api/characters", new CreateDnDCharacterCommand("Test", 10, 10, 10, 60), DnDCharacterDto.class);
        assertEquals("Test", dnDCharacterDto.getName());
        assertEquals(10, dnDCharacterDto.getAge());
        assertEquals(10, dnDCharacterDto.getArmorClass());
        assertEquals(10, dnDCharacterDto.getBaseAttack());
        assertEquals(60, dnDCharacterDto.getHealthPoint());
    }

    @Test
    void testListCharacters() {
        testRestTemplate.postForObject("/api/characters", new CreateDnDCharacterCommand("Test", 10, 10, 10, 60), DnDCharacterDto.class);
        testRestTemplate.postForObject("/api/characters", new CreateDnDCharacterCommand("Test 2", 10, 10, 10, 60), DnDCharacterDto.class);

        List<DnDCharacterDto> employees = testRestTemplate.exchange("/api/characters",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DnDCharacterDto>>() {
                }).getBody();

        assertThat(employees)
                .extracting(DnDCharacterDto::getName)
                .containsExactly("Test", "Test 2");
    }

    @Test
    void testUpdateCharacter() {
        DnDCharacterDto dnDCharacterDto = testRestTemplate.postForObject("/api/characters", new CreateDnDCharacterCommand("Test", 10, 10, 10, 60), DnDCharacterDto.class);
        long id = dnDCharacterDto.getId();
        DnDCharacterDto dnDCharacterDto2 = testRestTemplate.getForObject("/api/characters/" + id, DnDCharacterDto.class);
        assertEquals("Test", dnDCharacterDto2.getName());
        Map<String, String> param = new HashMap<>();
        param.put("name", "Test 2");
        testRestTemplate.put("/api/characters/" + id, param);
        DnDCharacterDto dnDCharacterDto3 = testRestTemplate.getForObject("/api/characters/" + id, DnDCharacterDto.class);
        assertEquals("Test 2", dnDCharacterDto3.getName());
    }

    @Test
    void testDeleteCharacter() {
        DnDCharacterDto dnDCharacterDto = testRestTemplate.postForObject("/api/characters", new CreateDnDCharacterCommand("Test", 10, 10, 10, 60), DnDCharacterDto.class);
        long id = dnDCharacterDto.getId();
        DnDCharacterDto dnDCharacterDto2 = testRestTemplate.getForObject("/api/characters/" + id, DnDCharacterDto.class);
        assertEquals("Test", dnDCharacterDto2.getName());
        testRestTemplate.delete("/api/characters/" + id);
        ResponseEntity<String> response = testRestTemplate.
                getForEntity("/api/characters/" + id, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateAndAssignItem() {
        DnDCharacterDto dnDCharacterDto1 = testRestTemplate.postForObject("/api/characters", new CreateDnDCharacterCommand("Test", 10, 10, 10, 60), DnDCharacterDto.class);
        long id1 = dnDCharacterDto1.getId();
        DnDCharacterDto dnDCharacterDto2 = testRestTemplate.postForObject("/api/characters/" + id1 + "/items", new CreateItemCommand("Test Item", "Test Description"), DnDCharacterDto.class);
        List<ItemDto> items = dnDCharacterDto2.getItems();
        assertEquals("Test Item", items.get(0).getName());
    }

    @Test
    void testCreateAndAssignWeapon() {
        DnDCharacterDto dnDCharacterDto1 = testRestTemplate.postForObject("/api/characters", new CreateDnDCharacterCommand("Test", 10, 10, 10, 60), DnDCharacterDto.class);
        long id1 = dnDCharacterDto1.getId();
        DnDCharacterDto dnDCharacterDto2 = testRestTemplate.postForObject("/api/characters/" + id1 + "/weapons", new CreateWeaponCommand("Test Weapon 1", WeaponType.CRUSHING, 10, 10), DnDCharacterDto.class);
        WeaponDto weaponDto = dnDCharacterDto2.getWeapon();
        assertEquals("Test Weapon 1", weaponDto.getName());
    }


}