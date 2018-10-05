package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalCalendarEvents;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import guitests.guihandles.CalendarEventCardHandle;
import guitests.guihandles.CalendarEventListPanelHandle;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.storage.XmlSerializableScheduler;

public class CalendarEventListPanelTest extends GuiUnitTest {
    private static final ObservableList<CalendarEvent> TYPICAL_CALENDAR_EVENTS =
            FXCollections.observableList(getTypicalCalendarEvents());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private CalendarEventListPanelHandle calendarEventListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_CALENDAR_EVENTS);

        for (int i = 0; i < TYPICAL_CALENDAR_EVENTS.size(); i++) {
            calendarEventListPanelHandle.navigateToCard(TYPICAL_CALENDAR_EVENTS.get(i));
            CalendarEvent expectedCalendarEvent = TYPICAL_CALENDAR_EVENTS.get(i);
            CalendarEventCardHandle actualCard = calendarEventListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedCalendarEvent, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_CALENDAR_EVENTS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        CalendarEventCardHandle expectedPerson = calendarEventListPanelHandle.getPersonCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        CalendarEventCardHandle selectedPerson = calendarEventListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPerson, selectedPerson);
    }

    /**
     * Verifies that creating and deleting large number of calendar events in {@code CalendarEventListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<CalendarEvent> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of event cards exceeded time limit");
    }

    /**
     * Returns a list of calendar events containing {@code calendarEventCount} calendar events that is used to populate the
     * {@code CalendarEventListPanel}.
     */
    private ObservableList<CalendarEvent> createBackingList(int calendarEventCount) throws Exception {
        Path xmlFile = createXmlFileWithCalendarEvents(calendarEventCount);
        XmlSerializableScheduler xmlScheduler =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableScheduler.class);
        return FXCollections.observableArrayList(xmlScheduler.toModelType().getCalendarEventList());
    }

    /**
     * Returns a .xml file containing {@code calendarEventCount} calendar events. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithCalendarEvents(int calendarEventCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<scheduler>\n");
        for (int i = 0; i < calendarEventCount; i++) {
            builder.append("<calendarEvents>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<address>a</address>\n");
            builder.append("</calendarEvents>\n");
        }
        builder.append("</scheduler>\n");

        Path manyCalendarEventsFile = Paths.get(TEST_DATA_FOLDER + "manyCalendarEvents.xml");
        FileUtil.createFile(manyCalendarEventsFile);
        FileUtil.writeToFile(manyCalendarEventsFile, builder.toString());
        manyCalendarEventsFile.toFile().deleteOnExit();
        return manyCalendarEventsFile;
    }

    /**
     * Initializes {@code calendarEventListPanelHandle} with a {@code CalendarEventListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code CalendarEventListPanel}.
     */
    private void initUi(ObservableList<CalendarEvent> backingList) {
        CalendarEventListPanel calendarEventListPanel = new CalendarEventListPanel(backingList);
        uiPartRule.setUiPart(calendarEventListPanel);

        calendarEventListPanelHandle = new CalendarEventListPanelHandle(getChildNode(calendarEventListPanel.getRoot(),
                CalendarEventListPanelHandle.CALENDAR_EVENT_LIST_VIEW_ID));
    }
}
