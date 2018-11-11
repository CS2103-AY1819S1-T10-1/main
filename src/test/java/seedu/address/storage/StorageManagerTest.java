package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEvents.getTypicalScheduler;
import static seedu.address.testutil.TypicalTodoListEvents.getTypicalToDoList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.SchedulerChangedEvent;
import seedu.address.commons.events.model.ToDoListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.ReadOnlyScheduler;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.Scheduler;
import seedu.address.model.ToDoList;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlSchedulerStorage addressBookStorage = new XmlSchedulerStorage(getTempFilePath("ab"));
        XmlToDoListStorage toDoListStorage = new XmlToDoListStorage(getTempFilePath("tdl"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, toDoListStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlSchedulerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlSchedulerStorageTest} class.
         */
        Scheduler original = getTypicalScheduler();
        storageManager.saveScheduler(original);
        ReadOnlyScheduler retrieved = storageManager.readScheduler().get();
        assertEquals(original, new Scheduler(retrieved));
    }

    @Test
    public void toDoListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlToDoListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlToDoListStorageTest} class.
         */
        ToDoList original = getTypicalToDoList();
        storageManager.saveToDoList(original);
        ReadOnlyToDoList retrieved = storageManager.readToDoList().get();
        assertEquals(original, new ToDoList(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getSchedulerFilePath());
    }

    @Test
    public void getToDoListFilePath() {
        assertNotNull(storageManager.getToDoListFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlSchedulerStorageExceptionThrowingStub(Paths.get("dummy")),
            new XmlToDoListStorage(Paths.get("dummy")),
            new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleSchedulerChangedEvent(new SchedulerChangedEvent(new Scheduler()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleToDoListChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlSchedulerStorage(Paths.get("dummy")),
            new XmlToDoListStorageExceptionThrowingStub(Paths.get("dummy")),
            new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleToDoListChangedEvent(new ToDoListChangedEvent(new ToDoList()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlSchedulerStorageExceptionThrowingStub extends XmlSchedulerStorage {

        public XmlSchedulerStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveScheduler(ReadOnlyScheduler addressBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlToDoListStorageExceptionThrowingStub extends XmlToDoListStorage {

        public XmlToDoListStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveToDoList(ReadOnlyToDoList toDoList, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

}
