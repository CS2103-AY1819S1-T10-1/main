package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyScheduler;

/**
 * A class to access Scheduler data stored as an xml file on the hard disk.
 */
public class XmlSchedulerStorage implements SchedulerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlSchedulerStorage.class);

    private Path filePath;

    public XmlSchedulerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getSchedulerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyScheduler> readScheduler() throws DataConversionException, IOException {
        return readScheduler(filePath);
    }

    /**
     * Similar to {@link #readScheduler()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyScheduler> readScheduler(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Scheduler file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableScheduler xmlScheduler = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlScheduler.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveScheduler(ReadOnlyScheduler scheduler) throws IOException {
        saveScheduler(scheduler, filePath);
    }

    /**
     * Similar to {@link #saveScheduler(ReadOnlyScheduler)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveScheduler(ReadOnlyScheduler scheduler, Path filePath) throws IOException {
        requireNonNull(scheduler);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableScheduler(scheduler));
    }

}
