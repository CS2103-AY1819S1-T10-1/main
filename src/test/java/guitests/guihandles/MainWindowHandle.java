package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final CalendarPanelHandle calendarPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final CalendarDisplayHandle calendarDisplay;

    public MainWindowHandle(Stage stage) {
        super(stage);
        calendarPanel = new CalendarPanelHandle(getChildNode(CalendarPanelHandle.CALENDAR_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        calendarDisplay = new CalendarDisplayHandle(getChildNode(CalendarDisplayHandle.CALENDAR_DISPLAY_BOX_ID));
    }

    public CalendarPanelHandle getCalendarPanel() {
        return calendarPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public CalendarDisplayHandle getCalendarDisplay() {
        return calendarDisplay;
    }

}
