package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Day Month Panel. Displays the month and day in the calendar.
 */
public class DayMonthPanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(DayMonthPanel.class);
    private static final String FXML = "DayMonthPanel.fxml";

    @FXML
    private Label monthLabel;

    @FXML
    private AnchorPane monthPanel;

    public DayMonthPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        monthLabel.setText("October 2017");

    }

}
