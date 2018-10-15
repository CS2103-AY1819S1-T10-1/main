package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Provides a handle to a calendar event card in the calendar event list panel.
 */
public class CalendarEventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TITLE_FIELD_ID = "#title";
    private static final String VENUE_FIELD_ID = "#venue";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label titleLabel;
    private final Label venueLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;

    public CalendarEventCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        titleLabel = getChildNode(TITLE_FIELD_ID);
        venueLabel = getChildNode(VENUE_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
            .getChildrenUnmodifiable()
            .stream()
            .map(Label.class::cast)
            .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getVenue() {
        return venueLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
            .stream()
            .map(Label::getText)
            .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code calendarevent}.
     */
    public boolean equals(CalendarEvent calendarEvent) {
        return getTitle().equals(calendarEvent.getName().fullTitle)
            && getVenue().equals(calendarEvent.getVenue().value)
            && getPhone().equals(calendarEvent.getPhone().value)
            && getEmail().equals(calendarEvent.getEmail().value)
            && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(calendarEvent.getTags().stream()
            .map(tag -> tag.tagName)
            .collect(Collectors.toList())));
    }
}
