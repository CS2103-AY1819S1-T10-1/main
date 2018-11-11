package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Provides a handle for {@code CalendarPanel} containing the list of {@code CalendarEventCard}.
 */
public class CalendarPanelHandle extends NodeHandle<ListView<CalendarEvent>> {
    public static final String CALENDAR_VIEW_ID = "#calendarView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<CalendarEvent> lastRememberedSelectedPersonCard;

    public CalendarPanelHandle(ListView<CalendarEvent> personListPanelNode) {
        super(personListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code CalendarEventCardHandle}.
     * A maximum of 1 item can be selected at any time.
     *
     * @throws AssertionError        if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public CalendarEventCardHandle getHandleToSelectedCard() {
        List<CalendarEvent> selectedCalendarEventList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCalendarEventList.size() != 1) {
            throw new AssertionError("CalendarEvent list size expected 1.");
        }

        return getAllCardNodes().stream()
            .map(CalendarEventCardHandle::new)
            .filter(handle -> handle.equals(selectedCalendarEventList.get(0)))
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
        List<CalendarEvent> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code calendarevent}.
     */
    public void navigateToCard(CalendarEvent calendarEvent) {
        if (!getRootNode().getItems().contains(calendarEvent)) {
            throw new IllegalArgumentException("CalendarEvent does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(calendarEvent);
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
     * Selects the {@code CalendarEventCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the calendarevent card handle of a calendarevent associated with the {@code index} in the list.
     *
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public CalendarEventCardHandle getPersonCardHandle(int index) {
        return getAllCardNodes().stream()
            .map(CalendarEventCardHandle::new)
            .filter(handle -> handle.equals(getPerson(index)))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    private CalendarEvent getPerson(int index) {
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
     * Remembers the selected {@code CalendarEventCard} in the list.
     */
    public void rememberSelectedCalendarEventCard() {
        List<CalendarEvent> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPersonCard = Optional.empty();
        } else {
            lastRememberedSelectedPersonCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code CalendarEventCard} is different from the value remembered by the most recent
     * {@code rememberSelectedCalendarEventCard()} call.
     */
    public boolean isSelectedCalendarEventCardChanged() {
        List<CalendarEvent> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPersonCard.isPresent();
        } else {
            return !lastRememberedSelectedPersonCard.isPresent()
                || !lastRememberedSelectedPersonCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
