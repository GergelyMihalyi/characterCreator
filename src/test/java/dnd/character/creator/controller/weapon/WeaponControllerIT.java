package dnd.character.creator.controller.weapon;

import dnd.character.creator.dto.character.CreateDnDCharacterCommand;
import dnd.character.creator.dto.character.DnDCharacterDto;
import dnd.character.creator.dto.weapon.CreateWeaponCommand;
import dnd.character.creator.dto.weapon.WeaponDto;
import dnd.character.creator.repository.weapon.WeaponType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from weapons")
class WeaponControllerIT {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void testCreateCharacters() {
        WeaponDto weaponDto =
                testRestTemplate.postForObject("/api/weapons", new CreateWeaponCommand("Test", WeaponType.CRUSHING, 10, 10), WeaponDto.class);
        assertEquals("Test", weaponDto.getName());
        assertEquals(WeaponType.CRUSHING, weaponDto.getWeaponType());
        assertEquals(10, weaponDto.getDamage());
        assertEquals(10, weaponDto.getWeight());
    }
}