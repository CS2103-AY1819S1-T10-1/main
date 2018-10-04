package systemtests;

import org.junit.Test;
import seedu.address.model.Scheduler;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TestUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

public class SampleDataTest extends SchedulerSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected Scheduler getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getDataFileLocation() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void addressBook_dataFileDoesNotExist_loadSampleData() {
        CalendarEvent[] expectedList = SampleDataUtil.getSampleCalendarEvents();
        assertListMatching(getPersonListPanel(), expectedList);
    }
}
