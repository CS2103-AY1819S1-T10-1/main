package seedu.address.testutil;

import seedu.address.model.Schedule;
import seedu.address.model.person.Event;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code Schedule ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Schedule addressBook;

    public AddressBookBuilder() {
        addressBook = new Schedule();
    }

    public AddressBookBuilder(Schedule addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Event} to the {@code Schedule} that we are building.
     */
    public AddressBookBuilder withPerson(Event event) {
        addressBook.addPerson(event);
        return this;
    }

    public Schedule build() {
        return addressBook;
    }
}
