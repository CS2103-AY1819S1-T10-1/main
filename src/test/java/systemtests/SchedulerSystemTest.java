package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatchingIgnoreOrder;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CalendarDisplayHandle;
import guitests.guihandles.CalendarPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;

import seedu.address.TestApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCalendarCommand;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.ListEventCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.Model;
import seedu.address.model.Scheduler;
import seedu.address.testutil.TypicalEvents;
import seedu.address.ui.BrowserPanel;
import seedu.address.ui.CommandBox;


/**
 * A system test class for Scheduler, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class SchedulerSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();
    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
        Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);
    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;


    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
        EventsCenter.clearSubscribers();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected Scheduler getInitialData() {
        return TypicalEvents.getTypicalScheduler();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public CalendarPanelHandle getCalendarEventListPanel() {
        return mainWindowHandle.getCalendarPanel();
    }

    public CalendarDisplayHandle getCalendarDisplay() {
        return mainWindowHandle.getCalendarDisplay();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);

    }

    /**
     * Displays all calendar events in the scheduler.
     */
    protected void showAllCalendarEvents() {
        executeCommand(ListEventCommand.COMMAND_WORD);
        assertEquals(getModel().getScheduler().getCalendarEventList().size(),
            getModel().getFilteredCalendarEventList().size());
    }

    /**
     * Displays all calendar events with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showCalendarEventsWithTitle(String keyword) {
        executeCommand(FindEventCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredCalendarEventList().size()
            < getModel().getScheduler().getCalendarEventList().size());
    }

    /**
     * Selects the calendarevent at {@code index} of the displayed list.
     */
    protected void selectCalendarEvent(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getCalendarEventListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all calendar events in the scheduler.
     */
    protected void deleteAllCalendarEvents() {
        executeCommand(ClearCalendarCommand.COMMAND_WORD);
        assertEquals(0, getModel().getScheduler().getCalendarEventList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same calendarevent objects as {@code expectedModel},
     * the calendar event list panel displays the calendar events in the model correctly, and the calendar display
     * displays the calendar events in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new Scheduler(expectedModel.getScheduler()), testApp.readStorageScheduler());
        assertListMatching(getCalendarEventListPanel(), expectedModel.getFilteredCalendarEventList());
        assertListMatchingIgnoreOrder(getCalendarDisplay(), expectedModel.getFullCalendarEventList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code CalendarPanelHandle} and {@code StatusBarFooterHandle} to
     * remember
     * their current state.
     */
    private void rememberStates() {
        getCalendarEventListPanel().rememberSelectedCalendarEventCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url remains displaying the details
     * of the previously selected calendarevent.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getCalendarEventListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the calendarevent in the calendarevent
     * list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     * @see CalendarPanelHandle#isSelectedCalendarEventCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getCalendarEventListPanel().navigateToCard(getCalendarEventListPanel().getSelectedCardIndex());
        String selectedCardName = getCalendarEventListPanel().getHandleToSelectedCard().getTitle();
        URL expectedUrl;
        try {
            expectedUrl = new URL(BrowserPanel.SEARCH_PAGE_URL
                + selectedCardName.replaceAll(" ", "%20"));
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.", mue);
        }

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getCalendarEventListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the calendarevent list panel remain unchanged.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     * @see CalendarPanelHandle#isSelectedCalendarEventCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getCalendarEventListPanel().isSelectedCalendarEventCardChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getCalendarEventListPanel(), getModel().getFilteredCalendarEventList());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }

}
