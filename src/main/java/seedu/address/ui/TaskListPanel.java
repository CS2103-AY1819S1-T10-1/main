package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * Panel containing the list of tasks
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @javafx.fxml.FXML
    private ListView<ToDoListEvent> toDoListView;

    public TaskListPanel(ObservableList<ToDoListEvent> toDoListEventList) {
        super(FXML);
        setConnections(toDoListEventList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ToDoListEvent> toDoListEventList) {
        toDoListView.setItems(toDoListEventList);
        toDoListView.setCellFactory(listView -> new ToDoListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        toDoListView.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                    raise(new TaskPanelSelectionChangedEvent(newValue));
                }
            });
    }

    /**
     * Scrolls to the {@code ToDoListEventCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            toDoListView.scrollTo(index);
            toDoListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ToDoListEvent} using a {@code ToDoListEventCard}.
     */
    class ToDoListViewCell extends ListCell<ToDoListEvent> {
        @Override
        protected void updateItem(ToDoListEvent toDoListEvent, boolean empty) {
            super.updateItem(toDoListEvent, empty);

            if (empty || toDoListEvent == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ToDoListEventCard(toDoListEvent, getIndex() + 1).getRoot());
            }
        }
    }

}
