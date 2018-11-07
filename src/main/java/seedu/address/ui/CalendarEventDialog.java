package seedu.address.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.calendarevent.CalendarEvent;

import java.util.logging.Logger;

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

    public void fillLabels() {
        titleLabel.setText(calendarEvent.getTitle().value);
        descriptionLabel.setText(calendarEvent.getDescriptionObject().value);
        venueLabel.setText(calendarEvent.getVenue().value);
        dateTimeLabel.setText(calendarEvent.getStartLocalDateTime().toString());
    }

    @FXML
    public void onOkButtonClicked(ActionEvent event) {
        closeStage(event);
    }

    private void closeStage(javafx.event.ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
