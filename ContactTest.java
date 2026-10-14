package ContactService;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Modifier;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the Contact class against its five requirements:
 *   REQ 1 - contactId:  required, non-null, max 10 chars, not updatable
 *   REQ 2 - firstName:  required, non-null, max 10 chars
 *   REQ 3 - lastName:   required, non-null, max 10 chars
 *   REQ 4 - phone:      required, non-null, exactly 10 digits
 *   REQ 5 - address:    required, non-null, max 30 chars
 */
class ContactTest {

    // Guarantees an exact length, so boundary tests can't be off by a character.
    private String text(int length) {
        return "a".repeat(length);
    }

    private String digits(int length) {
        return "1".repeat(length);
    }

    private Contact validContact() {
        return new Contact("1234567890", "Mariyah", "Sealy", "1234567890", "123 Corner Street");
    }

    // test valid construction 

    @Test
    void testCreateValidContact() {
        Contact contact = validContact();
        assertEquals("1234567890", contact.getContactId());
        assertEquals("Mariyah", contact.getFirstName());
        assertEquals("Sealy", contact.getLastName());
        assertEquals("1234567890", contact.getPhone());
        assertEquals("123 Corner Street", contact.getAddress());
    }

    // test REQ 1: contactId 

    @Test
    void testContactIdCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(null, "Mariyah", "Sealy", "1234567890", "123 Corner Street"));
    }

    @Test
    void testContactIdAtExactLimitOf10IsAccepted() {
        Contact contact = new Contact(text(10), "Mariyah", "Sealy", "1234567890", "123 Corner Street");
        assertEquals(10, contact.getContactId().length());
    }

    @Test
    void testContactIdOneOverLimitIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(text(11), "Mariyah", "Sealy", "1234567890", "123 Corner Street"));
    }

    @Test
    void testContactIdHasNoSetterAndIsFinal() throws Exception {
        // Structural check: there is no way to change the ID after construction.
        assertThrows(NoSuchMethodException.class, () ->
                Contact.class.getMethod("setContactId", String.class));
        assertTrue(Modifier.isFinal(Contact.class.getDeclaredField("contactId").getModifiers()));
    }

    @Test
    void testContactIdUnchangedAfterUpdatingOtherFields() {
        Contact contact = validContact();
        contact.setFirstName("Jane");
        contact.setLastName("Doe");
        contact.setPhone("9876543210");
        contact.setAddress("456 Oak Avenue");
        assertEquals("1234567890", contact.getContactId());
    }

    // test REQ 2: firstName 

    @Test
    void testFirstNameCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", null, "Sealy", "1234567890", "123 Corner Street"));
    }

    @Test
    void testFirstNameAtExactLimitOf10IsAccepted() {
        Contact contact = new Contact("1", text(10), "Sealy", "1234567890", "123 Corner Street");
        assertEquals(10, contact.getFirstName().length());
    }

    @Test
    void testFirstNameOneOverLimitIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", text(11), "Sealy", "1234567890", "123 Corner Street"));
    }

    @Test
    void testFirstNameCanBeUpdated() {
        Contact contact = validContact();
        contact.setFirstName("Jane");
        assertEquals("Jane", contact.getFirstName());
    }

    @Test
    void testSetFirstNameNullIsRejected() {
        Contact contact = validContact();
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(null));
    }

    @Test
    void testRejectedFirstNameUpdatePreservesOriginal() {
        Contact contact = validContact();
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(text(11)));
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(null));
        assertEquals("Mariyah", contact.getFirstName());
    }

    // test REQ 3: lastName 

    @Test
    void testLastNameCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", "Mariyah", null, "1234567890", "123 Corner Street"));
    }

    @Test
    void testLastNameAtExactLimitOf10IsAccepted() {
        Contact contact = new Contact("1", "Mariyah", text(10), "1234567890", "123 Corner Street");
        assertEquals(10, contact.getLastName().length());
    }

    @Test
    void testLastNameOneOverLimitIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", "Mariyah", text(11), "1234567890", "123 Corner Street"));
    }

    @Test
    void testLastNameCanBeUpdated() {
        Contact contact = validContact();
        contact.setLastName("Smith");
        assertEquals("Smith", contact.getLastName());
    }

    @Test
    void testRejectedLastNameUpdatePreservesOriginal() {
        Contact contact = validContact();
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName(text(11)));
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName(null));
        assertEquals("Sealy", contact.getLastName());
    }

    // test REQ 4: phone 

    @Test
    void testPhoneCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", "Mariyah", "Sealy", null, "123 Corner Street"));
    }

    @Test
    void testPhoneAtExactly10DigitsIsAccepted() {
        Contact contact = new Contact("1", "Mariyah", "Sealy", digits(10), "123 Corner Street");
        assertEquals(10, contact.getPhone().length());
    }

    @Test
    void testPhoneWith9DigitsIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", "Mariyah", "Sealy", digits(9), "123 Corner Street"));
    }

    @Test
    void testPhoneWith11DigitsIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", "Mariyah", "Sealy", digits(11), "123 Corner Street"));
    }

    @Test
    void testPhoneWithNonDigitCharactersIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", "Mariyah", "Sealy", "12345abcde", "123 Corner Street"));
    }

    @Test
    void testPhoneCanBeUpdated() {
        Contact contact = validContact();
        contact.setPhone("9876543210");
        assertEquals("9876543210", contact.getPhone());
    }

    @Test
    void testRejectedPhoneUpdatePreservesOriginal() {
        Contact contact = validContact();
        assertThrows(IllegalArgumentException.class, () -> contact.setPhone(digits(9)));
        assertThrows(IllegalArgumentException.class, () -> contact.setPhone(null));
        assertEquals("1234567890", contact.getPhone());
    }

    // test REQ 5: address 

    @Test
    void testAddressCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", "Mariyah", "Sealy", "1234567890", null));
    }

    @Test
    void testAddressAtExactLimitOf30IsAccepted() {
        Contact contact = new Contact("1", "Mariyah", "Sealy", "1234567890", text(30));
        assertEquals(30, contact.getAddress().length());
    }

    @Test
    void testAddressOneOverLimitIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", "Mariyah", "Sealy", "1234567890", text(31)));
    }

    @Test
    void testAddressCanBeUpdated() {
        Contact contact = validContact();
        contact.setAddress("456 Oak Avenue");
        assertEquals("456 Oak Avenue", contact.getAddress());
    }

    @Test
    void testRejectedAddressUpdatePreservesOriginal() {
        Contact contact = validContact();
        assertThrows(IllegalArgumentException.class, () -> contact.setAddress(text(31)));
        assertThrows(IllegalArgumentException.class, () -> contact.setAddress(null));
        assertEquals("123 Corner Street", contact.getAddress());
    }
}
