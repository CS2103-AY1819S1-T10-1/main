package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Provides a handle to a calendar event dialog.
 */
public class CalendarEventDialogHandle extends NodeHandle<Node> {
    private static final String TITLE_FIELD_ID = "#title";
    private static final String VENUE_FIELD_ID = "#venue";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String DATETIME_FIELD_ID = "#dateTime";

    private Label titleLabel;
    private Label descriptionLabel;
    private Label venueLabel;
    private Label dateTimeLabel;

    public CalendarEventDialogHandle(Node dialogNode) {
        super(dialogNode);

        titleLabel = getChildNode(TITLE_FIELD_ID);
        descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        venueLabel = getChildNode(VENUE_FIELD_ID);
        dateTimeLabel = getChildNode(DATETIME_FIELD_ID);
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getVenue() {
        return venueLabel.getText();
    }

    public String getDateTimeString() {
        return dateTimeLabel.getText();
    }

    /**
     * Returns true if this dialog handle contains {@code calendarevent}.
     */
    public boolean equals(CalendarEvent calendarEvent) {
        return getTitle().equals(calendarEvent.getTitle().value)
            && getDescription().equals(calendarEvent.getDescriptionObject().value)
            && getVenue().equals(calendarEvent.getVenue().value)
            && getDateTimeString().equals(calendarEvent.getStartLocalDateTime().toString());
    }
}
