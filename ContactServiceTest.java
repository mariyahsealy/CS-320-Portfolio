package ContactService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the ContactService class against its three requirements:
 *   REQ 6 - add contacts with a unique ID
 *   REQ 7 - delete contacts per contactId
 *   REQ 8 - update firstName, lastName, phone, and address per contactId
 */
class ContactServiceTest {

    private String text(int length) {
        return "a".repeat(length);
    }

    private ContactService service;

    @BeforeEach
    void setUp() {
        service = new ContactService();
    }

    private Contact validContact(String id) {
        return new Contact(id, "Mariyah", "Sealy", "1234567890", "123 Corner Street");
    }

    // test REQ 6: add with a unique ID 

    @Test
    void testAddContactStoresTheSameObject() {
        Contact contact = validContact("1");
        service.addContact(contact);
        // assertSame: service holds this exact object, not just an equal one.
        assertSame(contact, service.getContact("1"));
    }

    @Test
    void testAddContactWithDuplicateIdThrows() {
        service.addContact(validContact("1"));
        assertThrows(IllegalArgumentException.class, () -> service.addContact(validContact("1")));
    }

    @Test
    void testRejectedDuplicateDoesNotReplaceOriginal() {
        Contact original = validContact("1");
        service.addContact(original);
        assertThrows(IllegalArgumentException.class, () -> service.addContact(validContact("1")));
        assertSame(original, service.getContact("1"));
    }

    @Test
    void testAddNullContactThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.addContact(null));
    }

    @Test
    void testTwoContactsWithDifferentIdsAreStoredIndependently() {
        Contact first = validContact("1");
        Contact second = validContact("2");
        service.addContact(first);
        service.addContact(second);
        assertSame(first, service.getContact("1"));
        assertSame(second, service.getContact("2"));
    }

    // test REQ 7: delete by ID 

    @Test
    void testDeleteContactRemovesIt() {
        service.addContact(validContact("1"));
        service.deleteContact("1");
        assertNull(service.getContact("1"));
    }

    @Test
    void testDeleteNonExistentContactThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact("doesNotExist"));
    }

    @Test
    void testDeleteNullIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact(null));
    }

    @Test
    void testDeleteOnlyRemovesTheRequestedContact() {
        Contact second = validContact("2");
        service.addContact(validContact("1"));
        service.addContact(second);
        service.deleteContact("1");
        assertNull(service.getContact("1"));
        assertSame(second, service.getContact("2"));
    }

    // test REQ 8: update firstName 

    @Test
    void testUpdateFirstName() {
        service.addContact(validContact("1"));
        service.updateFirstName("1", "Jane");
        assertEquals("Jane", service.getContact("1").getFirstName());
    }

    @Test
    void testUpdateFirstNameOnMissingIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("doesNotExist", "Jane"));
    }

    @Test
    void testUpdateFirstNameTooLongThrows() {
        service.addContact(validContact("1"));
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("1", text(11)));
    }

    @Test
    void testUpdateFirstNameNullThrows() {
        service.addContact(validContact("1"));
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("1", null));
    }

    @Test
    void testRejectedFirstNameUpdateThroughServicePreservesOriginal() {
        service.addContact(validContact("1"));
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("1", text(11)));
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("1", null));
        assertEquals("Mariyah", service.getContact("1").getFirstName());
    }

    // test REQ 8: update lastName 

    @Test
    void testUpdateLastName() {
        service.addContact(validContact("1"));
        service.updateLastName("1", "Smith");
        assertEquals("Smith", service.getContact("1").getLastName());
    }

    @Test
    void testUpdateLastNameOnMissingIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("doesNotExist", "Smith"));
    }

    @Test
    void testUpdateLastNameTooLongThrows() {
        service.addContact(validContact("1"));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("1", text(11)));
    }

    @Test
    void testUpdateLastNameNullThrows() {
        service.addContact(validContact("1"));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("1", null));
    }

    @Test
    void testRejectedLastNameUpdateThroughServicePreservesOriginal() {
        service.addContact(validContact("1"));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("1", text(11)));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("1", null));
        assertEquals("Sealy", service.getContact("1").getLastName());
    }

    // test REQ 8: update phone 

    @Test
    void testUpdatePhone() {
        service.addContact(validContact("1"));
        service.updatePhone("1", "9876543210");
        assertEquals("9876543210", service.getContact("1").getPhone());
    }

    @Test
    void testUpdatePhoneOnMissingIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("doesNotExist", "9876543210"));
    }

    @Test
    void testUpdatePhoneInvalidValueThrows() {
        service.addContact(validContact("1"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("1", "notdigits1"));
    }

    @Test
    void testUpdatePhoneNullThrows() {
        service.addContact(validContact("1"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("1", null));
    }

    @Test
    void testRejectedPhoneUpdateThroughServicePreservesOriginal() {
        service.addContact(validContact("1"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("1", "notdigits1"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("1", null));
        assertEquals("1234567890", service.getContact("1").getPhone());
    }

    // test REQ 8: update address 

    @Test
    void testUpdateAddress() {
        service.addContact(validContact("1"));
        service.updateAddress("1", "456 Oak Avenue");
        assertEquals("456 Oak Avenue", service.getContact("1").getAddress());
    }

    @Test
    void testUpdateAddressOnMissingIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("doesNotExist", "456 Oak Avenue"));
    }

    @Test
    void testUpdateAddressTooLongThrows() {
        service.addContact(validContact("1"));
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("1", text(31)));
    }

    @Test
    void testUpdateAddressNullThrows() {
        service.addContact(validContact("1"));
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("1", null));
    }

    @Test
    void testRejectedAddressUpdateThroughServicePreservesOriginal() {
        service.addContact(validContact("1"));
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("1", text(31)));
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("1", null));
        assertEquals("123 Corner Street", service.getContact("1").getAddress());
    }

    // test contactId is never updatable, even via the service 

    @Test
    void testContactIdUnchangedAfterAllServiceUpdates() {
        service.addContact(validContact("1"));
        service.updateFirstName("1", "Jane");
        service.updateLastName("1", "Smith");
        service.updatePhone("1", "9876543210");
        service.updateAddress("1", "456 Oak Avenue");
        assertEquals("1", service.getContact("1").getContactId());
    }

    // test two-contact isolation under update

    @Test
    void testUpdatingOneContactDoesNotAffectAnother() {
        service.addContact(validContact("1"));
        Contact second = validContact("2");
        service.addContact(second);

        service.updateFirstName("1", "Jane");

        assertEquals("Jane", service.getContact("1").getFirstName());
        assertEquals("Mariyah", second.getFirstName());
    }
}