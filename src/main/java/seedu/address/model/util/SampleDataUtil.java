package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyScheduler;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.Scheduler;
import seedu.address.model.ToDoList;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.DateTime;
import seedu.address.model.calendarevent.DateTimeInfo;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.model.todolist.Priority;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * Contains utility methods for populating {@code Scheduler} with sample data.
 * Contains utility methods for populating {@code ToDoList} with sample data.
 */
public class SampleDataUtil {
    public static CalendarEvent[] getSampleCalendarEvents() {
        return new CalendarEvent[]{
            new CalendarEvent(new Title("MA1101 Tutorial"), new Description("Intro to gaussian elimination"),
                new DateTimeInfo(new DateTime("2018-10-16 14:00"), new DateTime("2018-10-16 16:00")),
                new Venue("Blk 30 Geylang Street 29, #06-40"), getTagSet("numerical")),
            new CalendarEvent(new Title("MA1102 Lecture"), new Description("Inverse function theorem"),
                new DateTimeInfo(new DateTime("2018-10-16 18:00"), new DateTime("2018-10-16 20:00")),
                new Venue("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), getTagSet("favourite", "bestseller")),
            new CalendarEvent(new Title("Math Analysis Lecture"), new Description("Topology of metric spaces"),
                new DateTimeInfo(new DateTime("2018-10-17 15:00"), new DateTime("2018-10-17 16:00")),
                new Venue("Blk 11 Ang Mo Kio Street 74, #11-04"), getTagSet("important")),
            new CalendarEvent(new Title("LSM1301 Lecture"), new Description("Plant form and function"),
                new DateTimeInfo(new DateTime("2018-10-17 18:00"), new DateTime("2018-10-17 21:00")),
                new Venue("Blk 436 Serangoon Gardens Street 26, #16-43"), getTagSet("labcoat")),
            new CalendarEvent(new Title("Forensic Science Lab"), new Description("Bring distilled water"),
                new DateTimeInfo(new DateTime("2018-10-18 12:00"), new DateTime("2018-10-18 14:00")),
                new Venue("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("criminal")),
            new CalendarEvent(new Title("Evidence Law Lecture"), new Description("Refutable evidence"),
                new DateTimeInfo(new DateTime("2018-10-18 11:00"), new DateTime("2018-10-18 16:00")),
                new Venue("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("justice"))
        };
    }

    public static ToDoListEvent[] getSampleToDoListEvents() {
        return new ToDoListEvent[]{
            new ToDoListEvent(new Title("CS3230 Homework"), new Description("divide and conquer"),
                new Priority("M")),
            new ToDoListEvent(new Title("CS3241 Lab4"), new Description("draw curve"),
                new Priority("H")),
            new ToDoListEvent(new Title("JS1011 Homework"), new Description("watch Akira movie"),
                new Priority("L")),
            new ToDoListEvent(new Title("CS2103 Project"), new Description("finish UI design"),
                new Priority("H")),
            new ToDoListEvent(new Title("CS2106 Lab5"), new Description("final lab"),
                new Priority("M")),
            new ToDoListEvent(new Title("Modify resume"), new Description("activities"),
                new Priority("L"))
        };
    }

    public static ReadOnlyScheduler getSampleScheduler() {
        Scheduler sampleScheduler = new Scheduler();
        for (CalendarEvent sampleCalendarEvent : getSampleCalendarEvents()) {
            sampleScheduler.addCalendarEvent(sampleCalendarEvent);
        }
        return sampleScheduler;
    }

    public static ReadOnlyToDoList getSampleToDoList() {
        ToDoList sampleToDoList = new ToDoList();
        for (ToDoListEvent sampleToDoListEvent : getSampleToDoListEvents()) {
            sampleToDoList.addToDoListEvent(sampleToDoListEvent);
        }
        return sampleToDoList;
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
