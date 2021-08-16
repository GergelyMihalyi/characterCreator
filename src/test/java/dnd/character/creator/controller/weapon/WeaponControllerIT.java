package dnd.character.creator.controller.weapon;

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
@Sql(statements = "delete from weapons")
class WeaponControllerIT {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void testCreateWeapon() {
        WeaponDto weaponDto =
                testRestTemplate.postForObject("/api/weapons", new CreateWeaponCommand("Test", WeaponType.CRUSHING, 10, 10), WeaponDto.class);
        assertEquals("Test", weaponDto.getName());
        assertEquals(WeaponType.CRUSHING, weaponDto.getWeaponType());
        assertEquals(10, weaponDto.getDamage());
        assertEquals(10, weaponDto.getWeight());
    }

    @Test
    void testListWeapons() {
        testRestTemplate.postForObject("/api/weapons", new CreateWeaponCommand("Test Weapon 1", WeaponType.CRUSHING, 10, 10), WeaponDto.class);
        testRestTemplate.postForObject("/api/weapons", new CreateWeaponCommand("Test Weapon 2", WeaponType.CUTTING, 10, 10), WeaponDto.class);

        List<WeaponDto> employees = testRestTemplate.exchange("/api/weapons",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WeaponDto>>() {
                }).getBody();

        assertThat(employees)
                .extracting(WeaponDto::getName)
                .containsExactly("Test Weapon 1", "Test Weapon 2");
    }

    @Test
    void testUpdateWeapon() {
        WeaponDto weaponDto = testRestTemplate.postForObject("/api/weapons", new CreateWeaponCommand("Test Weapon 1", WeaponType.CRUSHING, 10, 10), WeaponDto.class);
        long id = weaponDto.getId();
        WeaponDto weaponDto2 = testRestTemplate.getForObject("/api/weapons/" + id, WeaponDto.class);
        assertEquals("Test Weapon 1", weaponDto2.getName());
        Map<String, String> param = new HashMap<>();
        param.put("name", "Test Weapon 2");
        testRestTemplate.put("/api/weapons/" + id, param);
        WeaponDto weaponDto3 = testRestTemplate.getForObject("/api/weapons/" + id, WeaponDto.class);
        assertEquals("Test Weapon 2", weaponDto3.getName());
    }

    @Test
    void testDeleteWeapon() {
        WeaponDto weaponDto = testRestTemplate.postForObject("/api/weapons", new CreateWeaponCommand("Test Weapon 1", WeaponType.CRUSHING, 10, 10), WeaponDto.class);
        long id = weaponDto.getId();
        WeaponDto weaponDto2 = testRestTemplate.getForObject("/api/weapons/" + id, WeaponDto.class);
        assertEquals("Test Weapon 1", weaponDto2.getName());
        testRestTemplate.delete("/api/weapons/" + id);
        ResponseEntity<String> response = testRestTemplate.
                getForEntity("/api/weapons/" + id, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}