package ContactService;
import java.util.HashMap;
import java.util.Map;

/**
 * In-memory service for managing Contact objects with requirements enforced by this class:
 *  - Add contacts, requiring a unique contact ID
 *  - Delete contacts by contact ID
 *  - Update firstName, lastName, phone, and address fields by contact ID
 *  - contactId itself is never updatable 
 */
public final class ContactService {

    // contactId -> Contact
    private final Map<String, Contact> contacts = new HashMap<>();

     // Adds a new contact. The contact's ID must not already exist in the service.

    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        if (contacts.containsKey(contact.getContactId())) {
            throw new IllegalArgumentException("Contact ID already exists: " + contact.getContactId());
        }
        contacts.put(contact.getContactId(), contact);
    }

     // Deletes the contact with the given ID. Throws if no such contact exists.
    public void deleteContact(String contactId) {
        if (contactId == null || !contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("Contact ID not found: " + contactId);
        }
        contacts.remove(contactId);
    }

     // Retrieves a contact by ID, or null if it does not exist.
    public Contact getContact(String contactId) {
        return contacts.get(contactId);
    }

    private Contact requireContact(String contactId) {
        Contact contact = contacts.get(contactId);
        if (contact == null) {
            throw new IllegalArgumentException("Contact ID not found: " + contactId);
        }
        return contact;
    }

    public void updateFirstName(String contactId, String firstName) {
        requireContact(contactId).setFirstName(firstName);
    }

    public void updateLastName(String contactId, String lastName) {
        requireContact(contactId).setLastName(lastName);
    }

    public void updatePhone(String contactId, String phone) {
        requireContact(contactId).setPhone(phone);
    }

    public void updateAddress(String contactId, String address) {
        requireContact(contactId).setAddress(address);
    }
}