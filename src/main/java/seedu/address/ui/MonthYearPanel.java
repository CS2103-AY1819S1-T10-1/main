package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CalendarDisplayTimeChangedEvent;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Day Month Panel. Displays the month and day in the calendar.
 */
public class MonthYearPanel extends UiPart<Region> {

    private static final String FXML = "MonthYearPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(MonthYearPanel.class);

    private static final String MONTH_YEAR_DISPLAY_FORMAT = "MMMM uuuu";

    private static final DateTimeFormatter MONTH_YEAR_LABEL_FORMATTER =
        DateTimeFormatter.ofPattern(MONTH_YEAR_DISPLAY_FORMAT);

    @FXML
    private Label monthYearLabel;

    @FXML
    private AnchorPane monthYearPanel;

    public MonthYearPanel(ObservableList<CalendarEvent> calendarEventList) {
        super(FXML);
        renderDayMonthPanel(calendarEventList);
        registerAsAnEventHandler(this);
        calendarEventList.addListener(new ListChangeListener<CalendarEvent>() {
            @Override
            public void onChanged(Change<? extends CalendarEvent> c) {
                c.next();
                if (c.wasUpdated()) {
                    renderDayMonthPanel(calendarEventList);
                }
            }
        });
    }

    @Subscribe
    private void handleCalendarDisplayChangeEvent(CalendarDisplayTimeChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setMonthYearLabelText(event.newLocalDateTime);
    }

    private void setMonthYearLabelText(LocalDateTime startOfWeek) {
        monthYearLabel.setText(startOfWeek.format(MONTH_YEAR_LABEL_FORMATTER));
    }

    private LocalDateTime getStartOfWeek(ObservableList<CalendarEvent> calendarEventList) {
        if (!calendarEventList.isEmpty()) {
            CalendarEvent earliestCalendarEvent = calendarEventList.get(0);
            return earliestCalendarEvent.getStart().localDateTime;
        } else {
            return LocalDateTime.now();
        }
    }

    /**
     * renderDayMonthPanel.
     */
    private void renderDayMonthPanel(ObservableList<CalendarEvent> calendarEventList) {
        LocalDateTime startOfWeek = getStartOfWeek(calendarEventList);
        setMonthYearLabelText(startOfWeek);
    }

}
