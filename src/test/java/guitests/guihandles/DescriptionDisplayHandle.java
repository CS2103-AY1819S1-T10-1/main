package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * Provides a handle to a calendar event dialog.
 */
public class DescriptionDisplayHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String DESCRIPTION_FIELD_ID = "#description";

    private Label idLabel;
    private TextArea descriptionTextArea;

    public DescriptionDisplayHandle(Node dialogNode) {
        super(dialogNode);

        idLabel = getChildNode(ID_FIELD_ID);
        descriptionTextArea = getChildNode(DESCRIPTION_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getDescription() {
        return descriptionTextArea.getText();
    }

    /**
     * Returns true if this description display handle contains {@code calendarevent}.
     */
    public boolean equals(ToDoListEvent toDoListEvent) {
        return getDescription().equals(toDoListEvent.getDescription().value);
    }
}
