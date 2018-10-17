package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.todolist.ToDoListEvent;

/**
 * A utility class containing a list of {@code ToDoListEvent} objects to be used in tests.
 */
public class TypicalTodoListEvents {
    public static final ToDoListEvent MIDTERM = new ToDoListEventBuilder().withTitle("CS2103 Midterm")
            .withDescription("cover all materials from week 1 to week 7")
            .withPriority("H").build();
    public static final ToDoListEvent TUTORIAL = new ToDoListEventBuilder().withTitle("CS2103 Tutorial")
            .withDescription("project mock demo")
            .withPriority("M").build();
    public static final ToDoListEvent WORKSHOP = new ToDoListEventBuilder().withTitle("Interview workshop")
            .withDescription("will be helpful for interview")
            .withPriority("L").build();
    public static final ToDoListEvent ENTERTAINMENT = new ToDoListEventBuilder().withTitle("Daniel's birthday party")
            .withDescription("remember to buy gifts")
            .withPriority("M").build();


    private TypicalTodoListEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code Scheduler} with all the typical persons.
     */
    /*public static Scheduler getTypicalScheduler() {
        Scheduler ab = new Scheduler();
        for (CalendarEvent calendarEvent : getTypicalCalendarEvents()) {
            ab.addCalendarEvent(calendarEvent);
        }
        return ab;
    }*/

    public static List<ToDoListEvent> getTypicalTodoListEvents() {
        return new ArrayList<>(Arrays.asList(MIDTERM, TUTORIAL, WORKSHOP, ENTERTAINMENT));
    }
}
