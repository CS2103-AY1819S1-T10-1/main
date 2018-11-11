package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyToDoList;

/**
 * Indicates the ToDoList in the modelToDo has changed
 */
public class ToDoListChangedEvent extends BaseEvent {

    public final ReadOnlyToDoList data;

    public ToDoListChangedEvent(ReadOnlyToDoList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of events " + data.getToDoList().size();
    }

}
