package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalScheduler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Scheduler;
import seedu.address.model.ReadOnlyScheduler;

public class XmlSchedulerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSchedulerStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readScheduler_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readScheduler(null);
    }

    private java.util.Optional<ReadOnlyScheduler> readScheduler(String filePath) throws Exception {
        return new XmlSchedulerStorage(Paths.get(filePath)).readScheduler(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readScheduler("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readScheduler("NotXmlFormatScheduler.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readScheduler_invalidPersonScheduler_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readScheduler("invalidCalendarEventScheduler.xml");
    }

    @Test
    public void readScheduler_invalidAndValidPersonScheduler_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readScheduler("invalidAndValidCalendarEvent" +
                "Scheduler.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempScheduler.xml");
        Scheduler original = getTypicalScheduler();
        XmlSchedulerStorage xmlSchedulerStorage = new XmlSchedulerStorage(filePath);

        //Save in new file and read back
        xmlSchedulerStorage.saveScheduler(original, filePath);
        ReadOnlyScheduler readBack = xmlSchedulerStorage.readScheduler(filePath).get();
        assertEquals(original, new Scheduler(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addCalendarEvent(HOON);
        original.removeCalendarEvent(ALICE);
        xmlSchedulerStorage.saveScheduler(original, filePath);
        readBack = xmlSchedulerStorage.readScheduler(filePath).get();
        assertEquals(original, new Scheduler(readBack));

        //Save and read without specifying file path
        original.addCalendarEvent(IDA);
        xmlSchedulerStorage.saveScheduler(original); //file path not specified
        readBack = xmlSchedulerStorage.readScheduler().get(); //file path not specified
        assertEquals(original, new Scheduler(readBack));

    }

    @Test
    public void saveScheduler_nullScheduler_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveScheduler(null, "SomeFile.xml");
    }

    /**
     * Saves {@code scheduler} at the specified {@code filePath}.
     */
    private void saveScheduler(ReadOnlyScheduler scheduler, String filePath) {
        try {
            new XmlSchedulerStorage(Paths.get(filePath))
                    .saveScheduler(scheduler, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveScheduler_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveScheduler(new Scheduler(), null);
    }


}
