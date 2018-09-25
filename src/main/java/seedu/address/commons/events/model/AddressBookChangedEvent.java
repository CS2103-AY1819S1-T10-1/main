package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlySchedule;

/** Indicates the Schedule in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlySchedule data;

    public AddressBookChangedEvent(ReadOnlySchedule data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of events " + data.getEventList().size();
    }
}
