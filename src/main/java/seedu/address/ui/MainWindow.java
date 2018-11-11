package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.RefreshCalendarPanelEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.SwitchTabEvent;
import seedu.address.commons.events.ui.SwitchToSearchTabEvent;
import seedu.address.commons.events.ui.SwitchToTasksTabEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final int toDoListPanelTab = 0;
    private final int searchPanelTab = 1;
    private final int numberOfTabs = 2;
    private Stage primaryStage;
    private Logic logic;
    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private CalendarDisplay calendarDisplay;
    private CalendarPanel calendarPanel;
    private TaskListPanel taskListPanel;
    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;
    @FXML
    private TabPane tabPane;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane calendarDisplayPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane calendarPanelPlaceholder;

    @FXML
    private VBox monthYearPanelPlaceholder;


    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage);


        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setWindowDefaultSize(prefs);

        // TODO refactor switchtab to belong to the calendar panel
        setAccelerator(() -> handleSwitchTab(new SwitchTabEvent()), new KeyCodeCombination(KeyCode.TAB,
            KeyCombination.SHIFT_ANY, KeyCombination.CONTROL_DOWN));
        registerAsAnEventHandler(this);

        setAccelerator(() -> calendarDisplay.viewPrevious(), new KeyCodeCombination(KeyCode.LEFT));
        setAccelerator(() -> calendarDisplay.viewNext(), new KeyCodeCombination(KeyCode.RIGHT));
        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    /**
     * Sets the accelerator of an action.
     *
     * @param action         the action to execute when the keyCombination is pressed
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(Runnable action, KeyCombination keyCombination) {
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (!(event.getTarget() instanceof TextInputControl) && keyCombination.match(event)) {
                action.run();
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        TaskListPanel taskListPanel = new TaskListPanel(logic.getFilteredToDoListEventList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        calendarDisplay = new CalendarDisplay(logic.getFullCalendarEventList());
        calendarDisplayPlaceholder.getChildren().add(calendarDisplay.getRoot());

        MonthYearPanel monthYearPanel = new MonthYearPanel(logic.getFilteredCalendarEventList());
        monthYearPanelPlaceholder.getChildren().add(monthYearPanel.getRoot());

        CalendarPanel calendarPanel = new CalendarPanel(logic.getFilteredCalendarEventList());
        calendarPanelPlaceholder.getChildren().add(calendarPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
            (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    // TODO: remove if not needed anymore
    /**
     * Refreshes the calendar event panel
     */
    @FXML
    public void refreshCalendarPanel() {
        calendarPanelPlaceholder.getChildren().add(new CalendarPanel(logic.getFilteredCalendarEventList()).getRoot());
    }

    /**
     * Switches to the tab for the task list panel (if it is not already open)
     */
    @FXML
    public void showTaskListPanel() {
        if (!tabPane.getSelectionModel().isSelected(toDoListPanelTab)) {
            tabPane.getSelectionModel().select(toDoListPanelTab);
        }
    }

    /**
     * Updates the calendar event search panel and switches to its tab (if it is not already open)
     */
    @FXML
    public void showCalendarEventPanel() {
        if (!tabPane.getSelectionModel().isSelected(searchPanelTab)) {
            tabPane.getSelectionModel().select(searchPanelTab);
        }
    }

    private void switchPanel() {
        SelectionModel selectionModel = tabPane.getSelectionModel();
        int currentIndex = selectionModel.getSelectedIndex();
        selectionModel.select((currentIndex + 1) % numberOfTabs);
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public CalendarPanel getCalendarPanel() {
        return calendarPanel;
    }

    public TaskListPanel getTaskListPanel() {
        return taskListPanel;
    }

    // TODO remove method if not using browserPanel anymore
    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    @Subscribe
    private void handleRefreshCalendarPanelEvent(RefreshCalendarPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    @Subscribe
    private void handleSwitchToTasksTabEvent(SwitchToTasksTabEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showTaskListPanel();
    }

    @Subscribe
    private void handleSwitchToSearchTabEvent(SwitchToSearchTabEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showCalendarEventPanel();
    }

    @Subscribe
    private void handleSwitchTab(SwitchTabEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchPanel();
    }
}
