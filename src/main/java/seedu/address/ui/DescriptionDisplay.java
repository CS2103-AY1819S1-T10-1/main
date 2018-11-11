package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * An UI component that displays selected description of a {@code ToDoListEvent}.
 */
public class DescriptionDisplay extends UiPart<Region> {

    private static final String FXML = "DescriptionDisplay.fxml";
    public final ToDoListEvent toDoListEvent;
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private int selectedIndex;

    @FXML
    private TextArea description;

    @FXML
    private Label id;

    public DescriptionDisplay(ToDoListEvent toDoListEvent, int displayedIndex) {
        super(FXML);
        this.selectedIndex = displayedIndex;
        this.toDoListEvent = toDoListEvent;
        id.setText(displayedIndex + 1 + ": ");
        description.setText(toDoListEvent.getDescription().value);
        description.setEditable(false);
    }

}
