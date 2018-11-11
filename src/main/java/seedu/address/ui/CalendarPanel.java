package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CalendarPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Panel containing the list of calendar events.
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);

    @FXML
    private ListView<CalendarEvent> calendarView;

    public CalendarPanel(ObservableList<CalendarEvent> calendarEventList) {
        super(FXML);
        setConnections(calendarEventList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<CalendarEvent> calendarEventList) {
        calendarView.setItems(calendarEventList);
        calendarView.setCellFactory(listView -> new CalendarViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        calendarView.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    logger.fine("Selection in calendar event list panel changed to : '" + newValue + "'");
                    raise(new CalendarPanelSelectionChangedEvent(newValue));
                }
            });
    }

    /**
     * Scrolls to the {@code CalendarEventCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            calendarView.scrollTo(index);
            calendarView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CalendarEvent} using a {@code CalendarEventCard}.
     */
    class CalendarViewCell extends ListCell<CalendarEvent> {
        @Override
        protected void updateItem(CalendarEvent calendarEvent, boolean empty) {
            super.updateItem(calendarEvent, empty);

            if (empty || calendarEvent == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CalendarEventCard(calendarEvent, getIndex() + 1).getRoot());
            }
        }
    }

}
