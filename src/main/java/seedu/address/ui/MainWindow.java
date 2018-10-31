package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
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

    private final int toDoListPanelTab = 0;
    private final int searchPanelTab = 1;

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
    private MenuItem helpMenuItem;

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

        // TODO decide if setAccelerators is necessary
        // setAccelerators();
        registerAsAnEventHandler(this);

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
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
        calendarPanelPlaceholder.getChildren().add(new CalendarPanel(logic.getFilteredCalendarEventList()).getRoot());
        if (!tabPane.getSelectionModel().isSelected(searchPanelTab)) {
            tabPane.getSelectionModel().select(searchPanelTab);
        }
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
    private void handleSwitchToTasksTabEvent(SwitchToTasksTabEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showTaskListPanel();
    }

    @Subscribe
    private void handleSwitchToSearchTabEvent(SwitchToSearchTabEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showCalendarEventPanel();
    }
}
