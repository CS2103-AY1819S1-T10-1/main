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
 * TODO add javadoc comment
 */
public class CalendarEventDialog extends UiPart<Region> {

    private static final String FXML = "CalendarEventDialog.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    private CalendarEvent calendarEvent;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label venueLabel;

    @FXML
    private Label dateTimeLabel;

    public CalendarEventDialog(CalendarEvent calendarEvent) {
        super(FXML);
        this.calendarEvent = calendarEvent;
        fillLabels();
    }

    /**
     * Fills up the {@code CalendarEventDialog}'s Labels
     */
    public void fillLabels() {
        titleLabel.setText(calendarEvent.getTitle().value);
        descriptionLabel.setText(calendarEvent.getDescriptionObject().value);
        venueLabel.setText(calendarEvent.getVenue().value);
        dateTimeLabel.setText(calendarEvent.getStartLocalDateTime().toString());
    }

    /**
     * TODO add javadoc comment
     */
    @FXML
    public void onOkButtonClicked(ActionEvent event) {
        closeStage(event);
    }

    /**
     * TODO add javadoc comment
     */
    private void closeStage(javafx.event.ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
