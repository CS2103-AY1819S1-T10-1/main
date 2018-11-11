package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedToDoListEvent.MISSING_TODOLIST_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalTodoListEvents.LECTURE;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.todolist.Priority;
import seedu.address.testutil.Assert;

public class XmlAdaptedToDoListEventTest {

    private static final String INVALID_TITLE = " ";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_PRIORITY = " ";

    private static final String VALID_TITLE = LECTURE.getTitle().toString();
    private static final String VALID_DESCRIPTION = LECTURE.getDescription().toString();
    private static final String VALID_PRIORITY = LECTURE.getPriority().toString();

    @Test
    public void toModelType_validToDoListEventDetails_returnsToDoListEvent() throws Exception {
        XmlAdaptedToDoListEvent toDoListEvent = new XmlAdaptedToDoListEvent(LECTURE);
        assertEquals(LECTURE, toDoListEvent.toModelType());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        XmlAdaptedToDoListEvent toDoListEvent = new XmlAdaptedToDoListEvent(INVALID_TITLE, VALID_DESCRIPTION,
            VALID_PRIORITY);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, toDoListEvent::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        XmlAdaptedToDoListEvent doListEvent = new XmlAdaptedToDoListEvent(null, VALID_DESCRIPTION,
            VALID_PRIORITY);
        String expectedMessage = String.format(MISSING_TODOLIST_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, doListEvent::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        XmlAdaptedToDoListEvent toDoListEvent = new XmlAdaptedToDoListEvent(VALID_TITLE, INVALID_DESCRIPTION,
            VALID_PRIORITY);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, toDoListEvent::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedToDoListEvent toDoListEvent = new XmlAdaptedToDoListEvent(VALID_TITLE, null,
            VALID_PRIORITY);
        String expectedMessage =
            String.format(MISSING_TODOLIST_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, toDoListEvent::toModelType);
    }

    @Test
    public void toModelType_invalidPriority_throwsIllegalValueException() {
        XmlAdaptedToDoListEvent toDoListEvent = new XmlAdaptedToDoListEvent(VALID_TITLE, VALID_DESCRIPTION,
            INVALID_PRIORITY);
        String expectedMessage = Priority.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, toDoListEvent::toModelType);
    }

    @Test
    public void toModelType_nullPriority_throwsIllegalValueException() {
        XmlAdaptedToDoListEvent toDoListEvent = new XmlAdaptedToDoListEvent(VALID_TITLE, VALID_DESCRIPTION,
            null);
        String expectedMessage = String.format(MISSING_TODOLIST_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, toDoListEvent::toModelType);
    }

}
