package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIFFICULT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Schedule;
import seedu.address.model.person.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Event ALICE = new EventBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withDate("94351253")
            .withTags("friends").build();
    public static final Event BENSON = new EventBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withDate("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Event CARL = new EventBuilder().withName("Carl Kurz").withDate("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Event DANIEL = new EventBuilder().withName("Daniel Meier").withDate("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Event ELLE = new EventBuilder().withName("Elle Meyer").withDate("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Event FIONA = new EventBuilder().withName("Fiona Kunz").withDate("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Event GEORGE = new EventBuilder().withName("George Best").withDate("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Event HOON = new EventBuilder().withName("Hoon Meier").withDate("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Event IDA = new EventBuilder().withName("Ida Mueller").withDate("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final Event MIDTERM = new EventBuilder().withName(VALID_NAME_MIDTERM).withDate(VALID_DATE_MIDTERM)
            .withEmail(VALID_TIME_MIDTERM).withAddress(VALID_VENUE_MIDTERM).withTags(VALID_TAG_DIFFICULT).build();
    public static final Event WORKSHOP = new EventBuilder().withName(VALID_NAME_WORKSHOP).withDate(VALID_DATE_WORKSHOP)
            .withEmail(VALID_TIME_WORKSHOP).withAddress(VALID_VENUE_WORKSHOP).withTags(VALID_TAG_IMPORTANT, VALID_TAG_DIFFICULT)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code Schedule} with all the typical events.
     */
    public static Schedule getTypicalSchedule() {
        Schedule ab = new Schedule();
        for (Event event : getTypicalPersons()) {
            ab.addPerson(event);
        }
        return ab;
    }

    public static List<Event> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
