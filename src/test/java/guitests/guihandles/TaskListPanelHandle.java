package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * Provides a handle for {@code TaskListPanel} containing the list of {@code ToDoListEventCard}.
 */
public class TaskListPanelHandle extends NodeHandle<ListView<ToDoListEvent>> {

    public static final String TODOLIST_VIEW_ID = "#toDoListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<ToDoListEvent> lastRememberedSelectedToDoListEventCard;

    public TaskListPanelHandle(ListView<ToDoListEvent> taskListPanelNode) {
        super(taskListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ToDoListEventCardHandle}.
     * A maximum of 1 item can be selected at any time.
     *
     * @throws AssertionError        if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ToDoListEventCardHandle getHandleToSelectedCard() {
        List<ToDoListEvent> selectedToDoListEventList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedToDoListEventList.size() != 1) {
            throw new AssertionError("ToDoList size expected 1.");
        }

        return getAllCardNodes().stream()
            .map(ToDoListEventCardHandle::new)
            .filter(handle -> handle.equals(selectedToDoListEventList.get(0)))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<ToDoListEvent> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code todolistevent}.
     */
    public void navigateToCard(ToDoListEvent toDoListEvent) {
        if (!getRootNode().getItems().contains(toDoListEvent)) {
            throw new IllegalArgumentException("ToDoListEvent does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(toDoListEvent);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code ToDoListEventCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the todolistevent card handle of a todolistevent associated with the {@code index} in the list.
     *
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ToDoListEventCardHandle getToDoListCardHandle(int index) {
        return getAllCardNodes().stream()
            .map(ToDoListEventCardHandle::new)
            .filter(handle -> handle.equals(getToDoListEvent(index)))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    private ToDoListEvent getToDoListEvent(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code ToDoListEventCard} in the list.
     */
    public void rememberSelectedToDoListCard() {
        List<ToDoListEvent> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedToDoListEventCard = Optional.empty();
        } else {
            lastRememberedSelectedToDoListEventCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ToDoListEventCard} is different from the value remembered by the most recent
     * {@code rememberSelectedToDoListCard()} call.
     */
    public boolean isSelectedToDolistCardChanged() {
        List<ToDoListEvent> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedToDoListEventCard.isPresent();
        } else {
            return !lastRememberedSelectedToDoListEventCard.isPresent()
                || !lastRememberedSelectedToDoListEventCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
