package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelManagerToDo;
import seedu.address.model.ModelToDo;
import seedu.address.model.ReadOnlyScheduler;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.Scheduler;
import seedu.address.model.ToDoList;
import seedu.address.model.UserPrefs;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlSerializableScheduler;
import seedu.address.storage.XmlSerializableToDoList;
import seedu.address.testutil.TestUtil;


/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    public static final Path SAVE_LOCATION_FOR_TESTING_TODO = TestUtil.getFilePathInSandboxFolder("sampleDataToDo.xml");
    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
        TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyScheduler> initialDataSupplierCalendarEvent = () -> null;
    protected Supplier<ReadOnlyToDoList> initialDataSupplierToDo = () -> null;
    protected Path saveFileLocationCalendarEvent = SAVE_LOCATION_FOR_TESTING;
    protected Path saveFileLocationToDo = SAVE_LOCATION_FOR_TESTING_TODO;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyScheduler> initialDataSupplierCalendarEvent,
                   Supplier<ReadOnlyToDoList> initialDataSupplierToDo,
                   Path saveFileLocationCalendarEvent,
                   Path saveFileLocationToDo) {
        super();
        this.initialDataSupplierCalendarEvent = initialDataSupplierCalendarEvent;
        this.saveFileLocationCalendarEvent = saveFileLocationCalendarEvent;

        this.initialDataSupplierToDo = initialDataSupplierToDo;
        this.saveFileLocationToDo = saveFileLocationToDo;

        // If some initial local data has been provided, write those to the files
        if (initialDataSupplierCalendarEvent.get() != null) {
            createDataFileWithData(new XmlSerializableScheduler(this.initialDataSupplierCalendarEvent.get()),
                this.saveFileLocationCalendarEvent);
        }

        if (initialDataSupplierCalendarEvent.get() != null) {
            createDataFileWithData(new XmlSerializableToDoList(this.initialDataSupplierToDo.get()),
                this.saveFileLocationToDo);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setSchedulerFilePath(saveFileLocationCalendarEvent);
        userPrefs.setToDoListFilePath(saveFileLocationToDo);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the scheduler data stored inside the storage file.
     */
    public Scheduler readStorageScheduler() {
        try {
            return new Scheduler(storage.readScheduler().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the Scheduler format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns a defensive copy of the todolist data stored inside the storage file.
     */
    public ToDoList readStorageToDoList() {
        try {
            return new ToDoList(storage.readToDoList().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the ToDoList format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getSchedulerFilePath();
    }

    /**
     * Returns the todolist file path of the storage file.
     */
    public Path getStorageSaveLocationToDo() {
        return storage.getToDoListFilePath();
    }


    /**
     * Returns a defensive copy of the model.
     * The new Model has the same predicate and comparator, and thus the same filters and sorting
     * as the original {@code ObservableList}.
     */
    public Model getModel() {
        return new ModelManager(model.getScheduler(), new UserPrefs(), model.getPredicate(), model.getComparator());
    }

    /**
     * Returns a defensive copy of the modelToDo.
     */
    public ModelToDo getModelToDo() {
        return new ModelManagerToDo(modelToDo.getToDoList(), new UserPrefs());
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, Path filePath) {
        try {
            FileUtil.createIfMissing(filePath);
            XmlUtil.saveDataToFile(filePath, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
