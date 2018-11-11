package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalTodoListEvents.CS2106LAB;
import static seedu.address.testutil.TypicalTodoListEvents.CS3230WA;
import static seedu.address.testutil.TypicalTodoListEvents.MIDTERM;
import static seedu.address.testutil.TypicalTodoListEvents.getTypicalToDoList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.ToDoList;

public class XmlToDoListStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlToDoListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readToDoList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readToDoList(null);
    }

    private java.util.Optional<ReadOnlyToDoList> readToDoList(String filePath) throws Exception {
        return new XmlToDoListStorage(Paths.get(filePath)).readToDoList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readToDoList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readToDoList("NotXmlFormatToDoList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readToDoList_invalidToDoList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readToDoList("invalidToDoListEventToDoList.xml");
    }

    @Test
    public void readToDoList_invalidAndValidToDoList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readToDoList("invalidAndValidToDoListEvent" + "ToDoList.xml");
    }

    @Test
    public void readAndSaveToDoList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempToDoList.xml");
        ToDoList original = getTypicalToDoList();
        XmlToDoListStorage xmlToDoListStorage = new XmlToDoListStorage(filePath);

        //Save in new file and read back
        xmlToDoListStorage.saveToDoList(original, filePath);
        ReadOnlyToDoList readBack = xmlToDoListStorage.readToDoList(filePath).get();
        assertEquals(original, new ToDoList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addToDoListEvent(CS2106LAB);
        original.removeToDoListEvent(MIDTERM);
        xmlToDoListStorage.saveToDoList(original, filePath);
        readBack = xmlToDoListStorage.readToDoList(filePath).get();
        assertEquals(original, new ToDoList(readBack));

        //Save and read without specifying file path
        original.addToDoListEvent(CS3230WA);
        xmlToDoListStorage.saveToDoList(original); //file path not specified
        readBack = xmlToDoListStorage.readToDoList().get(); //file path not specified
        assertEquals(original, new ToDoList(readBack));

    }

    @Test
    public void saveToDoList_nullToDoList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveToDoList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code toDoList} at the specified {@code filePath}.
     */
    private void saveToDoList(ReadOnlyToDoList toDoList, String filePath) {
        try {
            new XmlToDoListStorage(Paths.get(filePath))
                .saveToDoList(toDoList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveToDoList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveToDoList(new ToDoList(), null);
    }

}
