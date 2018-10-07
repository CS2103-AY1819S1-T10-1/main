package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Scheduler;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * A utility class containing a list of {@code CalendarEvent} objects to be used in tests.
 */
public class TypicalPersons {

    public static final CalendarEvent ALICE = new CalendarEventBuilder().withName("Alice Pauline")
        .withLocation("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
        .withPhone("94351253")
        .withTags("friends").build();
    public static final CalendarEvent BENSON = new CalendarEventBuilder().withName("Benson Meier")
        .withLocation("311, Clementi Ave 2, #02-25")
        .withEmail("johnd@example.com").withPhone("98765432")
        .withDateTime(LocalDateTime.of(2018,10,07,18,00))
        .withTags("owesMoney", "friends").build();
    public static final CalendarEvent CARL = new CalendarEventBuilder().withName("Carl Kurz").withPhone("95352563")
        .withEmail("heinz@example.com").withLocation("wall street").build();
    public static final CalendarEvent DANIEL = new CalendarEventBuilder().withName("Daniel Meier").withPhone("87652533")
        .withEmail("cornelia@example.com").withLocation("10th street").withTags("friends").build();
    public static final CalendarEvent ELLE = new CalendarEventBuilder().withName("Elle Meyer").withPhone("9482224")
        .withEmail("werner@example.com").withLocation("michegan ave").build();
    public static final CalendarEvent FIONA = new CalendarEventBuilder().withName("Fiona Kunz").withPhone("9482427")
        .withEmail("lydia@example.com").withLocation("little tokyo").build();
    public static final CalendarEvent GEORGE = new CalendarEventBuilder().withName("George Best").withPhone("9482442")
        .withEmail("anna@example.com").withLocation("4th street").build();

    // Manually added
    public static final CalendarEvent HOON = new CalendarEventBuilder().withName("Hoon Meier").withPhone("8482424")
        .withEmail("stefan@example.com").withLocation("little india").build();
    public static final CalendarEvent IDA = new CalendarEventBuilder().withName("Ida Mueller").withPhone("8482131")
        .withEmail("hans@example.com").withLocation("chicago ave").build();

    // Manually added - CalendarEvent's details found in {@code CommandTestUtil}
    public static final CalendarEvent AMY =
        new CalendarEventBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withLocation(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final CalendarEvent BOB =
        new CalendarEventBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withLocation(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND,
            VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

    /**
     * Returns an {@code Scheduler} with all the typical persons.
     */
    public static Scheduler getTypicalScheduler() {
        Scheduler ab = new Scheduler();
        for (CalendarEvent calendarEvent : getTypicalCalendarEvents()) {
            ab.addCalendarEvent(calendarEvent);
        }
        return ab;
    }

    public static List<CalendarEvent> getTypicalCalendarEvents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
