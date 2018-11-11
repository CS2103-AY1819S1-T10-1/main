package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.ToDoList;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * An Immutable ToDoList that is serializable to XML format
 */
@XmlRootElement(name = "toDoList")
public class XmlSerializableToDoList {

    public static final String MESSAGE_DUPLICATE_TODOLIST_EVENT = "ToDoList Events list contains duplicate event(s).";

    @XmlElement
    private List<XmlAdaptedToDoListEvent> toDoListEvents;

    /**
     * Creates an empty XmlSerializableToDoList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableToDoList() {
        toDoListEvents = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableToDoList(ReadOnlyToDoList src) {
        this();
        toDoListEvents.addAll(src.getToDoList()
            .stream()
            .map(XmlAdaptedToDoListEvent::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this toDoList into the model's {@code ToDoList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code XmlAdaptedToDoListEvent}.
     */
    public ToDoList toModelType() throws IllegalValueException {
        ToDoList toDoList = new ToDoList();
        for (XmlAdaptedToDoListEvent p : toDoListEvents) {
            ToDoListEvent toDoListEvent = p.toModelType();
            if (toDoList.hasToDoListEvent(toDoListEvent)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TODOLIST_EVENT);
            }
            toDoList.addToDoListEvent(toDoListEvent);
        }
        return toDoList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableToDoList)) {
            return false;
        }
        return toDoListEvents.equals(((XmlSerializableToDoList) other).toDoListEvents);
    }
}
