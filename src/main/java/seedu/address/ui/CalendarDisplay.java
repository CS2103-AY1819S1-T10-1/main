package seedu.address.ui;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CalendarDisplayTimeChangedEvent;
import seedu.address.commons.events.ui.JumpToDateTimeEvent;
import seedu.address.model.calendarevent.CalendarEvent;


/**
 * The Ui component that is responsible for displaying a Calendar CalendarEvents to the user
 */
public class CalendarDisplay extends UiPart<Region> {
    private static final String FXML = "CalendarDisplay.fxml";
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
        this.currentDateTime = LocalDateTime.now();
        initAgenda();
        setConnections(calendarEventList);
        setControls();

        agenda.getStylesheets().add("view/ModifiedAgenda.css"); // "src/main/resources/view/
        setDisplayedDateTime(currentDateTime); // jump to the current time
    }

    /**
     * Starts up the internal Agenda Object
     * Disables unwanted inbuilt functions of agenda
     * Adds agenda control into the scene
     */
    private void initAgenda() {
        agenda = new Agenda();

        appointmentGroup = new Agenda.AppointmentGroupImpl().withStyleClass("group18");

        // TODO: decide what action callback is best
        // this actionCallBack is called when the user double clicks on an appointment in the display
        agenda.actionCallbackProperty().set(new Callback<Appointment, Void>() {
            @Override
            public Void call(Appointment param) {
                logger.info("User double clicked on " + param.toString());
                CalendarEvent calendarEvent = (CalendarEvent) param;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Event Information");
                alert.setHeaderText(calendarEvent.getTitle().value);
                alert.setContentText(calendarEvent.getDescriptionObject().value);
                alert.show();
                return null;
            }
        });

        agenda.setAppointmentChangedCallback(null);

        agenda.setAllowDragging(false);

        agenda.setEditAppointmentCallback(null);

        agenda.setSkin(new AgendaWeekSkin(agenda));

        calendarDisplayBox.getChildren().add(agenda);
    }

    /**
     * Sync the list of CalendarEvents to the calendar display
     * @param calendarEventList the list of CalendarEvents to display
     */
    private void setConnections(ObservableList<CalendarEvent> calendarEventList) {
        // populate the calendar
        calendarEventList.forEach((calendarEvent -> calendarEvent.setAppointmentGroup(appointmentGroup)));
        agenda.appointments().addAll(calendarEventList);

        // TODO: fix weird add/remove all items bug for first command
        this.calendarEventList.addListener(new ListChangeListener<CalendarEvent>() {
            @Override
            public void onChanged(Change<? extends CalendarEvent> c) {
                while (c.next()) {
                    if (c.wasRemoved()) {
                        for (CalendarEvent removedEvent : c.getRemoved()) {
                            agenda.appointments().remove(removedEvent);
                        }
                    }
                    if (c.wasAdded()) {
                        for (CalendarEvent addedEvent : c.getAddedSubList()) {
                            addedEvent.setAppointmentGroup(appointmentGroup);
                            agenda.appointments().add(addedEvent);
                        }
                    }
                }
            }
        });
    }

    /**
     * Set up the controls for interacting with the calendar display
     * The calendarDisplay must be in focus for this to work
     * TODO: find way to set focus properly
     */
    public void setControls() {
        calendarDisplayBox.addEventFilter(KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                case T: // toggle between day and week view
                    logger.info("Toggle Pressed");
                    toggleSkin();
                    agenda.requestFocus();
                    break;
                case LEFT:
                    logger.info("LEFT arrow Pressed");
                    viewPrevious();
                    indicateCalendarDisplayTimeChanged();
                    break;
                case RIGHT:
                    logger.info("RIGHT arrow Pressed");
                    viewNext();
                    indicateCalendarDisplayTimeChanged();
                    break;
                case L:
                    // for testing
                    // System.out.println("L pressed");
                    break;
                default:
                }
            }
        });
    }

    @FXML
    /**
     * Bug fix: Consumes the DOWN event, preventing strange behaviour
     */
    private void handleKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DOWN) {
            keyEvent.consume();
        }
    }

    /**
     * Raises event to event center of change in display time
     * Depends on the current date, not on the first date displayed in the calendar
     */
    private void indicateCalendarDisplayTimeChanged() {
        raise(new CalendarDisplayTimeChangedEvent(currentDateTime));
    }

    @Subscribe
    private void handleJumpToDateTimeEvent(JumpToDateTimeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setDisplayedDateTime(event.targetLocalDateTime);
    }

    /**
     * Displays the agenda for previous week or day, depending on current skin
     */
    public void viewPrevious() {
        if (isDaySkin()) {
            displayPreviousDay();
        } else if (isWeekSkin()) {
            displayPreviousWeek();
        }
    }

    /**
     * Displays the agenda for next week or day, depending on current skin
     */
    public void viewNext() {
        if (isDaySkin()) {
            displayNextDay();
        } else if (isWeekSkin()) {
            displayNextWeek();
        }
    }

    /**
     * Toggles between Daily and Weekly view
     */
    public void toggleSkin() {
        if (isDaySkin()) {
            setViewToWeeklyView();
        } else if (isWeekSkin()) {
            setViewToDailyView();
        }
    }

    public boolean isDaySkin() {
        return agenda.getSkin() instanceof AgendaDaySkin;
    }

    public boolean isWeekSkin() {
        return agenda.getSkin() instanceof AgendaWeekSkin;
    }

    /**
     * Toggle the view
     */
    public void setViewToWeeklyView() {
        agenda.setSkin(new AgendaWeekSkin(agenda)); // skin for viewing by week
    }

    /**
     * Toggle the view
     */
    public void setViewToDailyView() {
        agenda.setSkin(new AgendaDaySkin(agenda)); // skin for viewing by day
    }

    /**
     * Navigation method
     */
    public void displayNextWeek() {
        setDisplayedDateTime(currentDateTime.plusWeeks(1));
    }

    /**
     * Navigation method
     */
    public void displayPreviousWeek() {
        setDisplayedDateTime(currentDateTime.minusWeeks(1));
    }

    /**
     * Navigation method
     */
    public void displayNextDay() {
        setDisplayedDateTime(currentDateTime.plusDays(1));
    }

    /**
     * Navigation method
     */
    public void displayPreviousDay() {
        setDisplayedDateTime(currentDateTime.minusDays(1));
    }

    public void setDisplayedDateTime(LocalDateTime newLocalDateTime) {
        currentDateTime = newLocalDateTime;
        agenda.setDisplayedLocalDateTime(currentDateTime);
        indicateCalendarDisplayTimeChanged();
    }

}
