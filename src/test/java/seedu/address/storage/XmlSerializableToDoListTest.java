package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.ToDoList;
import seedu.address.testutil.TypicalTodoListEvents;

public class XmlSerializableToDoListTest {

    private static final Path TEST_DATA_FOLDER =
        Paths.get("src", "test", "data", "XmlSerializableToDoListTest");
    private static final Path TYPICAL_TODOLIST_EVENTS_FILE = TEST_DATA_FOLDER
        .resolve("typicalToDoListEventsToDoList" + ".xml");
    private static final Path INVALID_TODOLIST_EVENTS_FILE = TEST_DATA_FOLDER
        .resolve("invalidToDoListEventToDoList" + ".xml");
    private static final Path DUPLICATE_TODOLIST_EVENTS_FILE = TEST_DATA_FOLDER.resolve(
        "duplicateToDoListEventToDoList.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalToDoListEventsFile_success() throws Exception {
        XmlSerializableToDoList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_TODOLIST_EVENTS_FILE,
            XmlSerializableToDoList.class);
        ToDoList toDoListFromFile = dataFromFile.toModelType();
        ToDoList typicalEventsToDoList = TypicalTodoListEvents.getTypicalToDoList();
        assertEquals(toDoListFromFile, typicalEventsToDoList);
    }

    @Test
    public void toModelType_invalidToDoListEventFile_throwsIllegalValueException() throws Exception {
        XmlSerializableToDoList dataFromFile = XmlUtil.getDataFromFile(INVALID_TODOLIST_EVENTS_FILE,
            XmlSerializableToDoList.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateToDoListEvents_throwsIllegalValueException() throws Exception {
        XmlSerializableToDoList dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_TODOLIST_EVENTS_FILE,
            XmlSerializableToDoList.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableToDoList.MESSAGE_DUPLICATE_TODOLIST_EVENT);
        dataFromFile.toModelType();
    }

}
