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
import seedu.address.model.calendarEvent.CalendarEvent;

/**
 * Panel containing the list of persons.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    @FXML
    public EventListPanel(ObservableList<CalendarEvent> calendarEventList) {
        super(FXML);
        setConnections(calendarEventList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<CalendarEvent> calendarEventList) {
        eventListView.setItems(calendarEventList);
        eventListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in calendarEvent list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code EventCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
<<<<<<< HEAD:src/main/java/seedu/address/ui/PersonListPanel.java
     * Custom {@code ListCell} that displays the graphics of a {@code CalendarEvent} using a {@code PersonCard}.
=======
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code EventCard}.
>>>>>>> 4320692fa672f660392df74f27b3c8e238cb81ce:src/main/java/seedu/address/ui/EventListPanel.java
     */
    class PersonListViewCell extends ListCell<CalendarEvent> {
        @Override
        protected void updateItem(CalendarEvent calendarEvent, boolean empty) {
            super.updateItem(calendarEvent, empty);

            if (empty || calendarEvent == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventCard(calendarEvent, getIndex() + 1).getRoot());
            }
        }
    }

}
