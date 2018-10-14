package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedCalendarEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendarevent.Email;
import seedu.address.model.calendarevent.Phone;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.testutil.Assert;

public class XmlAdaptedCalendarEventTest {
    private static final String INVALID_NAME = " ";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_LOCATION = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
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
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person =
            new XmlAdaptedCalendarEvent(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_LOCATION, VALID_TAGS);
        String expectedMessage = Title.MESSAGE_TITLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(null, VALID_PHONE, VALID_EMAIL, VALID_LOCATION,
            VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person =
            new XmlAdaptedCalendarEvent(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_LOCATION, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(VALID_NAME, null, VALID_EMAIL, VALID_LOCATION,
            VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person =
            new XmlAdaptedCalendarEvent(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_LOCATION, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(VALID_NAME, VALID_PHONE, null, VALID_LOCATION,
            VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person =
            new XmlAdaptedCalendarEvent(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_LOCATION, VALID_TAGS);
        String expectedMessage = Venue.MESSAGE_LOCATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedCalendarEvent person = new XmlAdaptedCalendarEvent(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
            VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedCalendarEvent calendarEvent =
            new XmlAdaptedCalendarEvent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_LOCATION, invalidTags);
        Assert.assertThrows(IllegalValueException.class, calendarEvent::toModelType);
    }

}
