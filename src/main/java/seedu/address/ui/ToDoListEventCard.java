package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.logic.Logic;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * An UI component that displays information of a {@code ToDoListEvent}.
 */
public class ToDoListEventCard extends UiPart<Region> {
    private static final String FXML = "ToDoListEventCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Scheduler level 4</a>
     */

    public final ToDoListEvent toDoListEvent;

    @FXML
    private HBox cardPane;
    @FXML
    private CheckBox checkBox;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Button description;

    private int selectedIndex;
    private Logic logic;

    public ToDoListEventCard(ToDoListEvent toDoListEvent, int displayedIndex) {
        super(FXML);
        this.selectedIndex = displayedIndex;
        this.toDoListEvent = toDoListEvent;
        id.setText(displayedIndex + ". ");
        title.setText(toDoListEvent.getTitle().value);
        String priorityValue = toDoListEvent.getPriority().value;
        if (priorityValue.contains("H")) {
            priority.setText("High");
            priority.setBackground(new Background(new BackgroundFill(
                Color.color(0.929, 0.325, 0.325), null, null)));
        } else if (priorityValue.contains("M")) {
            priority.setText("Medium");
            priority.setBackground(new Background(new BackgroundFill(
                Color.color(1.000, 0.647, 0.000), null, null)));
        } else {
            priority.setText("Low");
            priority.setBackground(new Background(new BackgroundFill(
                Color.color(0.408, 0.718, 0.137), null, null)));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ToDoListEventCard)) {
            return false;
        }

        // state check
        ToDoListEventCard card = (ToDoListEventCard) other;
        return id.getText().equals(card.id.getText())
            && toDoListEvent.equals(card.toDoListEvent);
    }
}
