/**
 * Mariyah Sealy
 * CS 320
 * 10/4/2026
 */
package ContactService;

/**
 * Represents a single contact record with requirements enforced by this class:
 *  - contactId: required, non-null, max 10 characters, immutable after creation
 *  - firstName: required, non-null, max 10 characters, updatable
 *  - lastName:  required, non-null, max 10 characters, updatable
 *  - phone:     required, non-null, exactly 10 digits, updatable
 *  - address:   required, non-null, max 30 characters, updatable
 */
public final class Contact {

    // Not updatable once set, no setter is provided for this field.
    private final String contactId;

    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        if (contactId == null) {
            throw new IllegalArgumentException("Contact ID cannot be null");
        }
        if (contactId.length() > 10) {
            throw new IllegalArgumentException("Contact ID cannot be longer than 10 characters");
        }

        this.contactId = contactId;

        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setAddress(address);
    }

    public String getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("First name cannot be null");
        }
        if (firstName.length() > 10) {
            throw new IllegalArgumentException("First name cannot be longer than 10 characters");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Last name cannot be null");
        }
        if (lastName.length() > 10) {
            throw new IllegalArgumentException("Last name cannot be longer than 10 characters");
        }
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone cannot be null");
        }
        if (!phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone must be exactly 10 digits");
        }
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        if (address.length() > 30) {
            throw new IllegalArgumentException("Address cannot be longer than 30 characters");
        }
        this.address = address;
    }
}

