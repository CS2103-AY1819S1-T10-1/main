package seedu.address.ui;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.calendarevent.CalendarEvent;


/**
 * The Ui component that is responsible for displaying a List of CalendarEvents to the user
 */
public class CalendarDisplay extends UiPart<Region> {
    private static final String FXML = "CalendarDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarEventListPanel.class);

    // the actual Calendar control
    private Agenda agenda;

    // TODO: group CalendarEvents by week and add to this Map
    private HashMap<String, Agenda.AppointmentGroup> appointmentGroups = new HashMap<>();

    // keeps track of which week calendar is showing
    private LocalDateTime currentDateTime = LocalDateTime.now()
            .withHour(0).withMinute(0).withSecond(0).withNano(0);

    @FXML
    private VBox calendarDisplayBox;

    public CalendarDisplay(ObservableList<CalendarEvent> calendarEventList) {
        super(FXML);
        // registerAsAnEventHandler(this); // I don't think this is needed as of now

        // set up agenda internally
        initAgenda();

        setConnections(calendarEventList);
        setControls();
    }

    /**
     * Starts up the internal Agenda Object
     * Prevents the user from interacting directly with the calendar display
     */
    private void initAgenda() {
        agenda = new Agenda();

        // register actionCallBack, to be executed when user double click on appointment
        agenda.actionCallbackProperty().set(new Callback<Agenda.Appointment, Void>() {
            @Override
            public Void call(Agenda.Appointment param) {
                // can add more functionality here
                logger.info("User double clicked on " + param.toString());
                return null;
            }
        });

        // disable allowing user to create appointments by clicking on screen
        agenda.setAppointmentChangedCallback(null);

        // disable dragging appointments around
        agenda.setAllowDragging(false);

        // set the week for calendar to display
        agenda.setDisplayedLocalDateTime(currentDateTime);

        // show 1 week
        agenda.setSkin(new AgendaWeekSkin(agenda));

        // add agenda to the VBox area
        calendarDisplayBox.getChildren().add(agenda);
    }

    /**
     * Passes agenda the list of CalendarEvents to display
     * @param calendarEventList
     */
    private void setConnections(ObservableList<CalendarEvent> calendarEventList) {
        /**
         * init appointment group
         * appointment group is a way to sub-divide a bunch of appointments
         * then can apply separate styles, and also make memory more efficient
         * TODO: split appointments into weekly groups
         */
        Agenda.AppointmentGroupImpl apptGroup = new Agenda.AppointmentGroupImpl().withStyleClass("group1");

        /**
         * populate the calendar by adding all the events to the calendar
         * But when user updates the events using the CLI, need to somehow update this list as well
         * TODO: connect this list and calendarEventList, so that changes in calendarEventList will update this
         */
        for (CalendarEvent calendarEvent : calendarEventList) {
            Agenda.AppointmentImplLocal myAppt = new Agenda.AppointmentImplLocal()
                    .withStartLocalDateTime(calendarEvent.getStart().localDateTime)
                    .withEndLocalDateTime(calendarEvent.getEnd().localDateTime)
                    .withDescription(calendarEvent.getDescription().toString())
                    .withAppointmentGroup(apptGroup); // right now, putting everything into 1 group

            agenda.appointments().add(myAppt);
        }

        // Set connections...
    }

    /**
     * Temporary convienience method to test navigation
     * The calendarDisplay must be in focus for this to work, i.e. must click on the calendar
     */
    public void setControls() {
        calendarDisplayBox.addEventFilter(KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                /**
                 * Right now, when toggling, the focus will reset
                 * So need to click on the calendar again
                 * This won't be a problem if we using CLI to navigate
                 * In the meantime I'll try to think of a fix
                 * TODO: fix window losing focus when toggling
                 */
                if (event.getCode() == KeyCode.T) { // toggle between day and week view
                    if (agenda.getSkin() instanceof AgendaDaySkin) {
                        setViewToWeeklyView();
                    } else if (agenda.getSkin() instanceof AgendaWeekSkin) {
                        setViewToDailyView();
                    }
                } else if (event.getCode() == KeyCode.LEFT) {
                    logger.info("LEFT arrow Pressed");
                    if (agenda.getSkin() instanceof AgendaDaySkin) {
                        displayPreviousDay();
                    } else if (agenda.getSkin() instanceof AgendaWeekSkin) {
                        displayPreviousWeek();
                    }
                } else if (event.getCode() == KeyCode.RIGHT) {
                    logger.info("RIGHT arrow Pressed");
                    if (agenda.getSkin() instanceof AgendaDaySkin) {
                        displayNextDay();
                    } else if (agenda.getSkin() instanceof AgendaWeekSkin) {
                        displayNextWeek();
                    }
                }
            }
        });
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
        currentDateTime = currentDateTime.plusDays(7);
        agenda.setDisplayedLocalDateTime(currentDateTime);
    }

    /**
     * Navigation method
     */
    public void displayPreviousWeek() {
        currentDateTime = currentDateTime.minusDays(7);
        agenda.setDisplayedLocalDateTime(currentDateTime);
    }

    /**
     * Navigation method
     */
    public void displayNextDay() {
        currentDateTime = currentDateTime.plusDays(1);
        agenda.setDisplayedLocalDateTime(currentDateTime);
    }

    /**
     * Navigation method
     */
    public void displayPreviousDay() {
        currentDateTime = currentDateTime.minusDays(1);
        agenda.setDisplayedLocalDateTime(currentDateTime);
    }
}
