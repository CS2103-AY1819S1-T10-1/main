package guitests.guihandles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import seedu.address.model.calendarevent.CalendarEvent;


/**
 * A handle to the {@code CommandBox} in the GUI.
 *
 * Methods to test navigation, get the current date that is being displayed
 * Methods to test toggling of daily and weekly view
 * Method to test if changes in calendar event list are reflected in the list of appointments
 */
public class CalendarDisplayHandle extends NodeHandle<Node> {

    public static final String CALENDAR_DISPLAY_BOX_ID = "#calendarDisplayBox";
    public static final String CALENDAR_DISPLAY_ID = "#agenda";

    private VBox calendarDisplayBox;
    private Agenda agenda;

    public CalendarDisplayHandle(Node calendarDisplayBox) {
        super(calendarDisplayBox);

        //calendarDisplayBox = getChildNode(CALENDAR_DISPLAY_BOX_ID);
        agenda = getChildNode(CALENDAR_DISPLAY_ID);
    }

    public LocalDateTime getDisplayedLocalDateTime() {
        return agenda.getDisplayedLocalDateTime();
    }

    /**
     * Gets the list of events that the calendar is currently displaying
     * @return
     */
    public List<CalendarEvent> getDisplayedCalendarEvents() {
        ArrayList<CalendarEvent> events = new ArrayList<>();
        for (Agenda.Appointment appt : agenda.appointments()) {
            events.add((CalendarEvent) appt);
        }
        return events;
    }

    public boolean isDailyView() {
        return agenda.getSkin() instanceof AgendaDaySkin;
    }

    public boolean isWeeklyView() {
        return agenda.getSkin() instanceof AgendaWeekSkin;
    }
}
