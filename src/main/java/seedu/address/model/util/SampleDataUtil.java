package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Schedule;
import seedu.address.model.ReadOnlySchedule;
import seedu.address.model.person.*;
import seedu.address.model.person.Event;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Schedule} with sample data.
 */
public class SampleDataUtil {
    public static Event[] getSamplePersons() {
        return new Event[] {
            new Event(new Name("Alex Yeoh"), new Date("87438807"), new Time("alexyeoh@example.com"),
                new Venue("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Event(new Name("Bernice Yu"), new Date("99272758"), new Time("berniceyu@example.com"),
                new Venue("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Event(new Name("Charlotte Oliveiro"), new Date("93210283"), new Time("charlotte@example.com"),
                new Venue("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Event(new Name("David Li"), new Date("91031282"), new Time("lidavid@example.com"),
                new Venue("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Event(new Name("Irfan Ibrahim"), new Date("92492021"), new Time("irfan@example.com"),
                new Venue("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Event(new Name("Roy Balakrishnan"), new Date("92624417"), new Time("royb@example.com"),
                new Venue("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlySchedule getSampleAddressBook() {
        Schedule sampleAb = new Schedule();
        for (Event sampleEvent : getSamplePersons()) {
            sampleAb.addPerson(sampleEvent);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
