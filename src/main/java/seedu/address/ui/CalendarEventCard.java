package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * An UI component that displays information of a {@code CalendarEvent}.
 */
public class CalendarEventCard extends UiPart<Region> {

    private static final String FXML = "CalendarEventCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Scheduler level 4</a>
     */

    public final CalendarEvent calendarEvent;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label start;
    @FXML
    private Label end;
    @FXML
    private Label description;
    @FXML
    private Label venue;
    @FXML
    private FlowPane tags;

    public CalendarEventCard(CalendarEvent calendarEvent, int displayedIndex) {
        super(FXML);
        this.calendarEvent = calendarEvent;
        id.setText(displayedIndex + ". ");
        title.setText(calendarEvent.getTitle().value);
        start.setText("Start:  " + calendarEvent.getStart().toString());
        end.setText("End:   " + calendarEvent.getEnd().toString());
        description.setText(calendarEvent.getDescriptionObject().value);
        venue.setText(calendarEvent.getVenue().value);
        calendarEvent.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CalendarEventCard)) {
            return false;
        }

        // state check
        CalendarEventCard card = (CalendarEventCard) other;
        return id.getText().equals(card.id.getText())
            && calendarEvent.equals(card.calendarEvent);
    }
}
