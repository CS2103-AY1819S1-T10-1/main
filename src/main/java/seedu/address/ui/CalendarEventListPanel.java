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
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Panel containing the list of calendar events.
 */
public class CalendarEventListPanel extends UiPart<Region> {
    private static final String FXML = "CalendarEventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarEventListPanel.class);

    @FXML
    private ListView<CalendarEvent> personListView;

    public CalendarEventListPanel(ObservableList<CalendarEvent> calendarEventList) {
        super(FXML);
        setConnections(calendarEventList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<CalendarEvent> calendarEventList) {
        personListView.setItems(calendarEventList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in calendarevent list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code CalendarEventCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            personListView.scrollTo(index);
            personListView.getSelectionModel().clearAndSelect(index);
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
    class PersonListViewCell extends ListCell<CalendarEvent> {
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
