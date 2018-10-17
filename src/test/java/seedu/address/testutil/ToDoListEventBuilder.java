package seedu.address.testutil;

import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.todolist.Priority;
import seedu.address.model.todolist.ToDoListEvent;


/**
 * A utility class to help with building ToDoListEvent objects.
 */
public class ToDoListEventBuilder {

    public static final String DEFAULT_TITLE = "CS2103 Lecture";
    public static final String DEFAULT_DESCRIPTION = "Abstraction, IntelliJ, Gradle";
    public static final String DEFAULT_PRIORITY = "H";

    private Title title;
    private Description description;
    private Priority priority;

    public ToDoListEventBuilder() {
        title = new Title(DEFAULT_TITLE);
        description = new Description(DEFAULT_DESCRIPTION);
        priority = new Priority(DEFAULT_PRIORITY);
    }

    /**
     * Initializes the ToDoListEventBuilder with the data of {@code toDoListEventToCopy}.
     */
    public ToDoListEventBuilder(ToDoListEvent toDoListEventToCopy) {
        title = toDoListEventToCopy.getTitle();
        description = toDoListEventToCopy.getDescription();
        priority = toDoListEventToCopy.getPriority();
    }

    /**
     * Sets the {@code Title} of the {@code ToDoListEvent} that we are building.
     */
    public ToDoListEventBuilder withTitle(String name) {
        this.title = new Title(name);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code ToDoListEvent} that we are building.
     */
    public ToDoListEventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code ToDoListEvent} that we are building.
     */
    public ToDoListEventBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    public ToDoListEvent build() {
        return new ToDoListEvent(title, description, priority);
    }
}
