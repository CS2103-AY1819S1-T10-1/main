package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.SchedulerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyScheduler;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends SchedulerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getSchedulerFilePath();

    @Override
    Optional<ReadOnlyScheduler> readScheduler() throws DataConversionException, IOException;

    @Override
    void saveScheduler(ReadOnlyScheduler scheduler) throws IOException;

    /**
     * Saves the current version of the Scheduler to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleSchedulerChangedEvent(SchedulerChangedEvent sce);
}
