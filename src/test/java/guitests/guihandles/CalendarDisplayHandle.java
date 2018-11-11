package guitests.guihandles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import seedu.address.model.calendarevent.CalendarEvent;


/**
 * A handle to the {@code CalendarDisplay} in the GUI.
 * The limitations of this class are that the agenda ui cannot be access through ui.
 * Therefore, most of the methods get the state of the agenda ui through its data.
 */
public class CalendarDisplayHandle extends NodeHandle<Node> {
    public static final String CALENDAR_DISPLAY_BOX_ID = "#calendarDisplayBox";
    public static final String CALENDAR_DISPLAY_ID = "#agenda";

    private Agenda agenda;

    public CalendarDisplayHandle(Node calendarDisplayBox) {
        super(calendarDisplayBox);

        agenda = getChildNode(CALENDAR_DISPLAY_ID);
    }

    public LocalDateTime getDisplayedLocalDateTime() {
        return agenda.getDisplayedLocalDateTime();
    }

    /**
     * Gets the list of events that the calendar is currently displaying.
     */
    public List<CalendarEvent> getDisplayedCalendarEvents() {
        ArrayList<CalendarEvent> calendarEvents = new ArrayList<>();
        for (Agenda.Appointment appointment : agenda.appointments()) {
            if (appointment instanceof CalendarEvent) {
                calendarEvents.add((CalendarEvent) appointment);
            }
        }
        return calendarEvents;
    }

    public boolean isWeeklyView() {
        return agenda.getSkin() instanceof AgendaWeekSkin;
    }
}
