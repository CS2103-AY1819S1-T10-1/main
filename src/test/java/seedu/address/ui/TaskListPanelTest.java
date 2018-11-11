package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;
import static seedu.address.testutil.TypicalTodoListEvents.getTypicalToDoListEvents;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysToDo;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEqualsToDo;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.TaskListPanelHandle;
import guitests.guihandles.ToDoListEventCardHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.todolist.ToDoListEvent;
import seedu.address.storage.XmlSerializableToDoList;

public class TaskListPanelTest extends GuiUnitTest {

    private static final ObservableList<ToDoListEvent> TYPICAL_TODOLIST_EVENTS =
        FXCollections.observableList(getTypicalToDoListEvents());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_ELEMENT);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private TaskListPanelHandle taskListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_TODOLIST_EVENTS);

        for (int i = 0; i < TYPICAL_TODOLIST_EVENTS.size(); i++) {
            taskListPanelHandle.navigateToCard(TYPICAL_TODOLIST_EVENTS.get(i));
            ToDoListEvent expectedToDoListEvent = TYPICAL_TODOLIST_EVENTS.get(i);
            ToDoListEventCardHandle actualCard = taskListPanelHandle.getToDoListCardHandle(i);

            assertCardDisplaysToDo(expectedToDoListEvent, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_TODOLIST_EVENTS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        ToDoListEventCardHandle expectedEvent =
            taskListPanelHandle.getToDoListCardHandle(INDEX_SECOND_ELEMENT.getZeroBased());
        ToDoListEventCardHandle selectedEvent = taskListPanelHandle.getHandleToSelectedCard();
        assertCardEqualsToDo(expectedEvent, selectedEvent);
    }

    /**
     * Verifies that creating and deleting large number of todolist events in {@code TaskListPanel} requires
     * lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<ToDoListEvent> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of event cards exceeded time limit");
    }

    /**
     * Returns a list of calendar events containing {@code toDoListEventCount} calendar events that is used to
     * populate the
     * {@code TaskListPanel}.
     */
    private ObservableList<ToDoListEvent> createBackingList(int toDoListEventCount) throws Exception {
        Path xmlFile = createXmlFileWithToDoListEvents(toDoListEventCount);
        XmlSerializableToDoList xmlScheduler =
            XmlUtil.getDataFromFile(xmlFile, XmlSerializableToDoList.class);
        return FXCollections.observableArrayList(xmlScheduler.toModelType().getToDoList());
    }

    /**
     * Returns a .xml file containing {@code toDoListEventCount} todolist events. This file will be deleted when the
     * JVM terminates.
     */
    private Path createXmlFileWithToDoListEvents(int toDoListEventCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<toDoList>\n");
        for (int i = 0; i < toDoListEventCount; i++) {
            builder.append("<toDoListEvent>\n");
            builder.append("<title>").append(i).append("a</title>\n");
            builder.append("<description>aaa</description>\n");
            builder.append("<priority>H</priority>\n");
            builder.append("</toDoListEvent>\n");
        }
        builder.append("</toDoList>\n");

        Path manyToDoListEventsFile = Paths.get(TEST_DATA_FOLDER + "manyToDoListEvents.xml");
        FileUtil.createFile(manyToDoListEventsFile);
        FileUtil.writeToFile(manyToDoListEventsFile, builder.toString());
        manyToDoListEventsFile.toFile().deleteOnExit();
        return manyToDoListEventsFile;
    }

    /**
     * Initializes {@code taskListPanelHandle} with a {@code TaskListPanel} backed by {@code
     * backingList}.
     * Also shows the {@code Stage} that displays only {@code TaskListPanel}.
     */
    private void initUi(ObservableList<ToDoListEvent> backingList) {
        TaskListPanel taskListPanel = new TaskListPanel(backingList);
        uiPartRule.setUiPart(taskListPanel);


        taskListPanelHandle = new TaskListPanelHandle(getChildNode(taskListPanel.getRoot(),
            TaskListPanelHandle.TODOLIST_VIEW_ID));
    }

}
