package seedu.address.ui;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CalendarDisplayTimeChangedEvent;
import seedu.address.commons.events.ui.CalendarPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToDateTimeEvent;
import seedu.address.model.calendarevent.CalendarEvent;


/**
 * The Ui component that is responsible for displaying a Calendar CalendarEvents to the user
 */
public class CalendarDisplay extends UiPart<Region> {
    private static final String FXML = "CalendarDisplay.fxml";
    private static final String CSS = "view/ModifiedAgenda.css";
    private static final String STYLE_CLASS = "group18"; // style class for agenda appointments
    private final Logger logger = LogsCenter.getLogger(CalendarDisplay.class);

    private ObservableList<CalendarEvent> calendarEventList;
    private Agenda agenda;
    private Agenda.AppointmentGroupImpl appointmentGroup;
    private LocalDateTime currentDateTime;

    @FXML
    private VBox calendarDisplayBox;

    public CalendarDisplay(ObservableList<CalendarEvent> calendarEventList) {
        super(FXML);
        registerAsAnEventHandler(this);

        this.calendarEventList = calendarEventList;
        this.currentDateTime = LocalDateTime.now().withNano(0);
        initAgenda();
        setConnections(calendarEventList);
        setControls();

        agenda.getStylesheets().add(CSS); // "src/main/resources/view/
        setDisplayedDateTime(currentDateTime); // jump to the current time
    }

    /**
     * Starts up the internal Agenda Object.
     * Disables unwanted inbuilt functions of agenda.
     * Adds the agenda Control into the scene.
     */
    private void initAgenda() {
        agenda = new Agenda();
        appointmentGroup = new Agenda.AppointmentGroupImpl().withStyleClass(STYLE_CLASS);

        // this actionCallBack is called when the user double clicks on an appointment in the display
        // Opens a dialog containing the details of the clicked event
        agenda.actionCallbackProperty().set(param -> {
            logger.info("User double clicked on " + param.toString());
            CalendarEventDialog dialog = new CalendarEventDialog((CalendarEvent) param);
            displayPopUp(dialog.getRoot());
            return null;
        });

        agenda.setAllowDragging(false);
        agenda.setAppointmentChangedCallback(param -> null);
        agenda.setEditAppointmentCallback(param -> null);
        agenda.setSkin(new AgendaWeekSkin(agenda));
        agenda.setId("agenda");

        calendarDisplayBox.getChildren().add(agenda);
    }

    /**
     * Creates a new window to display root.
     * CalendarDisplay will block until the new window is closed.
     */
    private void displayPopUp(Parent root) {
        Scene scene = new Scene(root, 300, 200);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Sync the list of CalendarEvents to the calendar display.
     *
     * @param calendarEventList the list of CalendarEvents to display.
     */
    private void setConnections(ObservableList<CalendarEvent> calendarEventList) {
        // populate the calendar
        // setting the appointment group applies the correct CSS to it
        calendarEventList.forEach((calendarEvent -> calendarEvent.setAppointmentGroup(appointmentGroup)));
        agenda.appointments().addAll(calendarEventList);

        // push the changes to agenda
        calendarEventList.addListener(this::forwardChanges);
    }

    /**
     * Forward changes in calendar event list to agenda
     */
    private void forwardChanges(ListChangeListener.Change<? extends CalendarEvent> c) {
        while (c.next()) {
            if (c.wasRemoved()) {
                for (CalendarEvent removedEvent : c.getRemoved()) {
                    agenda.appointments().remove(removedEvent);
                }
            }
            if (c.wasAdded()) {
                for (CalendarEvent addedEvent : c.getAddedSubList()) {
                    addedEvent.setAppointmentGroup(appointmentGroup);
                    agenda.appointments().add(c.getFrom(), addedEvent);
                }
            }
        }
    }

    /**
     * Set up the controls for interacting with the calendar display.
     * The calendarDisplay must be in focus.
     */
    private void setControls() {
        calendarDisplayBox.addEventFilter(KEY_PRESSED, event -> {
            switch (event.getCode()) {
            case LEFT:
                logger.info("LEFT arrow Pressed.");
                displayPreviousWeek();
                indicateCalendarDisplayTimeChanged();
                break;
            case RIGHT:
                logger.info("RIGHT arrow Pressed.");
                displayNextWeek();
                indicateCalendarDisplayTimeChanged();
                break;
            default:
            }
        });
    }

    /**
     * Raises event to event center of change in display time
     * Depends on the current date, not on the first date displayed in the calendar
     */
    private void indicateCalendarDisplayTimeChanged() {
        raise(new CalendarDisplayTimeChangedEvent(currentDateTime));
    }

    /**
     * Calendar will display the period containing the specified LocalDateTime
     */
    @Subscribe
    private void handleJumpToDateTimeEvent(JumpToDateTimeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setDisplayedDateTime(event.targetLocalDateTime);
    }

    /**
     * Calendar will display the period containing the selected CalendarEvent
     */
    @Subscribe
    private void handleCalendarPanelSelectionChangedEvent(CalendarPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setDisplayedDateTime(event.newSelection.getStartLocalDateTime());
    }

    /**
     * Sets the {@code LocaDateTime} that agenda will display.
     * Raises a CalendarDisplayTimeChangedEvent.
     */
    public void setDisplayedDateTime(LocalDateTime newLocalDateTime) {
        currentDateTime = newLocalDateTime;

        /* if user jumps to this LocalDateTime, scrolls away, and jumps back to the same
        time, agenda will not center the display again. So must perturb the display time slightly
        before setting it to the actual target
         */
        agenda.setDisplayedLocalDateTime(currentDateTime.plusSeconds(1));

        agenda.setDisplayedLocalDateTime(currentDateTime);
        indicateCalendarDisplayTimeChanged();
    }

    /**
     * Navigate by setting agenda's displayed DateTime.
     */
    public void displayNextWeek() {
        setDisplayedDateTime(currentDateTime.plusWeeks(1));
    }

    public void displayPreviousWeek() {
        setDisplayedDateTime(currentDateTime.minusWeeks(1));
    }
}
