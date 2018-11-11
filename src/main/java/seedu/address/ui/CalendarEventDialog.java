package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.calendarevent.CalendarEvent;


/**
 * A pop-up dialog to display the information of a {@code CalendarEvent} to the user.
 */
public class CalendarEventDialog extends UiPart<Region> {

    private static final String FXML = "CalendarEventDialog.fxml";

    public final CalendarEvent calendarEvent;
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    @FXML
    private Label title;

    @FXML
    private Label description;

    @FXML
    private Label venue;

    @FXML
    private Label dateTime;

    public CalendarEventDialog(CalendarEvent calendarEvent) {
        super(FXML);
        this.calendarEvent = calendarEvent;
        fillLabels();
    }

    /**
     * Fills up the labels using the information of the calendar event.
     */
    private void fillLabels() {
        title.setText(calendarEvent.getTitle().value);
        description.setText(calendarEvent.getDescriptionObject().value);
        venue.setText(calendarEvent.getVenue().value);
        dateTime.setText(calendarEvent.getStartLocalDateTime().toString());
    }

    /**
     * Closes the dialog.
     */
    @FXML
    public void onOkButtonClicked(ActionEvent event) {
        closeStage(event);
    }

    /**
     * Closes this Dialog.
     */
    private void closeStage(javafx.event.ActionEvent event) {
        logger.info("Closing dialog.");
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CalendarEventDialog)) {
            return false;
        }

        // state check
        CalendarEventDialog dialog = (CalendarEventDialog) other;
        return calendarEvent.equals(dialog.calendarEvent);
    }
}
