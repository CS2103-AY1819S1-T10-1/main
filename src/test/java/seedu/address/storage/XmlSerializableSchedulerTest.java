package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.Scheduler;
import seedu.address.testutil.TypicalPersons;

public class XmlSerializableSchedulerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableSchedulerTest");
    private static final Path TYPICAL_CALENDAR_EVENTS_FILE = TEST_DATA_FOLDER.resolve("typicalCalendarEventsScheduler.xml");
    private static final Path INVALID_CALENDAR_EVENTS_FILE = TEST_DATA_FOLDER.resolve("invalidCalendarEventScheduler.xml");
    private static final Path DUPLICATE_CALENDAR_EVENTS_FILE = TEST_DATA_FOLDER.resolve("duplicateCalendarEventScheduler.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableScheduler dataFromFile = XmlUtil.getDataFromFile(TYPICAL_CALENDAR_EVENTS_FILE,
                XmlSerializableScheduler.class);
        Scheduler schedulerFromFile = dataFromFile.toModelType();
        Scheduler typicalPersonsScheduler = TypicalPersons.getTypicalScheduler();
        assertEquals(schedulerFromFile, typicalPersonsScheduler);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableScheduler dataFromFile = XmlUtil.getDataFromFile(INVALID_CALENDAR_EVENTS_FILE,
                XmlSerializableScheduler.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableScheduler dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_CALENDAR_EVENTS_FILE,
                XmlSerializableScheduler.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableScheduler.MESSAGE_DUPLICATE_CALENDAR_EVENT);
        dataFromFile.toModelType();
    }

}
