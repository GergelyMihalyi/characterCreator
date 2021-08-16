package dnd.character.creator.weapons.exceptions;


public class WeaponNotFoundException extends RuntimeException {
    public WeaponNotFoundException(String message) {
        super(message);
    }
}
