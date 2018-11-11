package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedCalendarEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalEvents.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendarevent.DateTime;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.testutil.Assert;

public class XmlAdaptedCalendarEventTest {
    private static final String INVALID_TITLE = " ";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_LOCATION = " ";
    private static final String INVALID_START = "2018/10/16 13:45";
    private static final String INVALID_END = "20000-15-42 13:45";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TITLE = BENSON.getTitle().toString();
    private static final String VALID_DESCRIPTION = BENSON.getDescriptionObject().toString();
    private static final String VALID_START = BENSON.getStart().toInputFormat();
    private static final String VALID_END = BENSON.getEnd().toInputFormat();
    private static final String VALID_LOCATION = BENSON.getVenue().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
        .map(XmlAdaptedTag::new)
        .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(INVALID_TITLE, VALID_DESCRIPTION,
            VALID_START, VALID_END, VALID_LOCATION, VALID_TAGS);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(null, VALID_DESCRIPTION,
            VALID_START, VALID_END, VALID_LOCATION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(VALID_TITLE, INVALID_DESCRIPTION,
            VALID_START, VALID_END, VALID_LOCATION, VALID_TAGS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(VALID_TITLE, null,
            VALID_START, VALID_END, VALID_LOCATION,
            VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidStart_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(VALID_TITLE, VALID_DESCRIPTION,
            INVALID_START, VALID_END, VALID_LOCATION, VALID_TAGS);
        String expectedMessage = DateTime.MESSAGE_DATETIME_INPUT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullStart_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(VALID_TITLE, VALID_DESCRIPTION,
            null, VALID_END, VALID_LOCATION,
            VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Start");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }


    @Test
    public void toModelType_invalidEnd_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(VALID_TITLE, VALID_DESCRIPTION,
            VALID_START, INVALID_END, VALID_LOCATION, VALID_TAGS);
        String expectedMessage = DateTime.MESSAGE_DATETIME_INPUT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEnd_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(VALID_TITLE, VALID_DESCRIPTION,
            VALID_START, null, VALID_LOCATION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "End");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }


    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(VALID_TITLE, VALID_DESCRIPTION,
            VALID_START, VALID_END, INVALID_LOCATION, VALID_TAGS);
        String expectedMessage = Venue.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(VALID_TITLE, VALID_DESCRIPTION,
            VALID_START, VALID_END, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedCalendarEvent calendarEvent = new XmlAdaptedCalendarEvent(VALID_TITLE, VALID_DESCRIPTION,
            VALID_START, VALID_END, VALID_LOCATION, invalidTags);
        Assert.assertThrows(IllegalValueException.class, calendarEvent::toModelType);
    }

}
