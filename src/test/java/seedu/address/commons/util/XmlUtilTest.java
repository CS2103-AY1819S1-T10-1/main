package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.Scheduler;
import seedu.address.storage.XmlAdaptedCalendarEvent;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableScheduler;
import seedu.address.testutil.CalendarEventBuilder;
import seedu.address.testutil.SchedulerBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validScheduler.xml");
    private static final Path MISSING_CALENDAR_EVENT_FIELD_FILE = TEST_DATA_FOLDER
        .resolve("missingCalendarEventField" + ".xml");
    private static final Path INVALID_CALENDAR_EVENT_FIELD_FILE = TEST_DATA_FOLDER
        .resolve("invalidCalendarEventField" + ".xml");
    private static final Path VALID_CALENDAR_EVENT_FILE = TEST_DATA_FOLDER.resolve("validCalendarEvent.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempScheduler.xml");

    private static final String INVALID_DESCRIPTION = "9482asf424";

    private static final String VALID_TITLE = "Hans Muster";
    private static final String VALID_DESCRIPTION = "9482424";
    private static final String VALID_START = "2018-10-16 14:00";
    private static final String VALID_END = "2018-10-16 16:00";
    private static final String VALID_VENUE = "4th street";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, Scheduler.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, Scheduler.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, Scheduler.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        Scheduler dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableScheduler.class).toModelType();
        assertEquals(9, dataFromFile.getCalendarEventList().size());
    }

    @Test
    public void xmlAdaptedCalendarEventFromFile_fileWithMissingCalendarEventField_validResult() throws Exception {
        XmlAdaptedCalendarEvent actualCalendarEvent = XmlUtil.getDataFromFile(
            MISSING_CALENDAR_EVENT_FIELD_FILE, XmlAdaptedCalendarEventWithRootElement.class);
        XmlAdaptedCalendarEvent expectedCalendarEvent = new XmlAdaptedCalendarEvent(
            null, VALID_DESCRIPTION, VALID_START, VALID_END, VALID_VENUE, VALID_TAGS);
        assertEquals(expectedCalendarEvent, actualCalendarEvent);
    }

    @Test
    public void xmlAdaptedCalendarEventFromFile_fileWithInvalidCalendarEventField_validResult() throws Exception {
        XmlAdaptedCalendarEvent actualCalendarEvent =
            XmlUtil.getDataFromFile(INVALID_CALENDAR_EVENT_FIELD_FILE, XmlAdaptedCalendarEventWithRootElement.class);
        XmlAdaptedCalendarEvent expectedCalendarEvent = new XmlAdaptedCalendarEvent(
            VALID_TITLE, INVALID_DESCRIPTION, VALID_START, VALID_END, VALID_VENUE, VALID_TAGS);
        assertEquals(expectedCalendarEvent, actualCalendarEvent);
    }

    @Test
    public void xmlAdaptedCalendarEventFromFile_fileWithValidCalendarEvent_validResult() throws Exception {
        XmlAdaptedCalendarEvent actualCalendarEvent = XmlUtil.getDataFromFile(
            VALID_CALENDAR_EVENT_FILE, XmlAdaptedCalendarEventWithRootElement.class);
        XmlAdaptedCalendarEvent expectedCalendarEvent = new XmlAdaptedCalendarEvent(
            VALID_TITLE, VALID_DESCRIPTION, VALID_START, VALID_END, VALID_VENUE, VALID_TAGS);
        assertEquals(expectedCalendarEvent, actualCalendarEvent);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new Scheduler());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new Scheduler());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableScheduler dataToWrite = new XmlSerializableScheduler(new Scheduler());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableScheduler dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableScheduler.class);
        assertEquals(dataToWrite, dataFromFile);

        SchedulerBuilder builder = new SchedulerBuilder(new Scheduler());
        dataToWrite = new XmlSerializableScheduler(
            builder.withCalendarEvent(new CalendarEventBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableScheduler.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code
     * XmlAdaptedCalendarEvent}
     * objects.
     */
    @XmlRootElement(name = "calendarEvent")
    private static class XmlAdaptedCalendarEventWithRootElement extends XmlAdaptedCalendarEvent {
    }
}
