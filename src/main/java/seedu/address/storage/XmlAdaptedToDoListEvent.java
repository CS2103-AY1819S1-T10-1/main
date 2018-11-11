package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.todolist.Priority;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * JAXB-friendly version of the ToDoList Event.
 */
public class XmlAdaptedToDoListEvent {

    public static final String MISSING_TODOLIST_FIELD_MESSAGE_FORMAT = "ToDoLost event's %s field is missing!";

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String priority;

    /**
     * Constructs an XmlAdaptedToDoListEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedToDoListEvent() {
    }

    /**
     * Constructs an {@code XmlAdaptedCalendarEvent} with the given calendar event details.
     */
    public XmlAdaptedToDoListEvent(String title, String description, String priority) {

        this.title = title;
        this.description = description;
        this.priority = priority;

    }

    /**
     * Converts a given ToDoList Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedToDoListEvent
     */
    public XmlAdaptedToDoListEvent(ToDoListEvent source) {
        title = source.getTitle().value;
        description = source.getDescription().value;
        priority = source.getPriority().value;
    }

    /**
     * Converts this jaxb-friendly adapted todolistevent object into the model's ToDoListEvent object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted calendar event
     */
    public ToDoListEvent toModelType() throws IllegalValueException {

        if (title == null) {
            throw new IllegalValueException(
                String.format(MISSING_TODOLIST_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValid(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelName = new Title(title);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_TODOLIST_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName()));
        }
        if (!Description.isValid(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_TODOLIST_FIELD_MESSAGE_FORMAT,
                Priority.class.getSimpleName()));
        }
        if (!Priority.isValid(priority)) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }
        final Priority modelPriority = new Priority(priority);

        return new ToDoListEvent(modelName, modelDescription, modelPriority);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedToDoListEvent)) {
            return false;
        }

        XmlAdaptedToDoListEvent otherToDoListEvent = (XmlAdaptedToDoListEvent) other;
        return Objects.equals(title, otherToDoListEvent.title)
            && Objects.equals(description, otherToDoListEvent.description)
            && Objects.equals(priority, otherToDoListEvent.priority);
    }
}
