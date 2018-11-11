package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.RefreshCalendarPanelEvent;
import seedu.address.commons.events.ui.SwitchToSearchTabEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelToDo;
import seedu.address.model.calendarevent.DatePredicate;
import seedu.address.model.calendarevent.FuzzySearchComparator;
import seedu.address.model.calendarevent.FuzzySearchFilterPredicate;
import seedu.address.model.calendarevent.TagsPredicate;

/**
 * Finds and lists all events in the calendar of the scheduler whose title, venue
 * or description contain any of the argument keywords.
 * Optionally filters events based on date and tags
 * Keyword matching is case insensitive.
 */
public class FindEventCommand extends Command {

    public static final String COMMAND_WORD = "find event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose titles match any of "
        + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
        + "Optionally filters the search between the specified dates and times (using natural language parsing).\n"
        + "Also optionally filters the search by the specified tags (case-insensitive).\n"
        + "Parameters: " + COMMAND_WORD + " KEYWORD [MORE_KEYWORDS]... [" + PREFIX_FROM + " DATE/TIME] ["
        + PREFIX_TO + " DATE/TIME] [" + PREFIX_TAG + " TAG] [" + PREFIX_TAG + " ANOTHER_TAG]...\n"
        + "Example: " + COMMAND_WORD + "project tutorial exam " + PREFIX_TAG + " ma3220 " + PREFIX_TAG + " cs2103";

    private final FuzzySearchFilterPredicate titlePredicate;
    private final FuzzySearchComparator fuzzySearchComparator;
    private final DatePredicate datePredicate;
    private final TagsPredicate tagsPredicate;

    public FindEventCommand(FuzzySearchFilterPredicate titlePredicate, FuzzySearchComparator fuzzySearchComparator,
                            DatePredicate datePredicate, TagsPredicate tagsPredicate) {
        this.titlePredicate = titlePredicate;
        this.fuzzySearchComparator = fuzzySearchComparator;
        this.datePredicate = datePredicate;
        this.tagsPredicate = tagsPredicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.clearAllPredicatesAndComparators();
        model.addPredicate(titlePredicate);
        if (tagsPredicate.hasTags()) {
            model.addPredicate(tagsPredicate);
        }
        model.addPredicate(datePredicate);
        model.sortFilteredCalendarEventList(fuzzySearchComparator);

        EventsCenter.getInstance().post(new RefreshCalendarPanelEvent());
        EventsCenter.getInstance().post(new SwitchToSearchTabEvent());

        return new CommandResult(
            String.format(Messages.MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW,
                model.getFilteredCalendarEventList().size()));
    }

    @Override
    public CommandResult execute(ModelToDo modelToDo, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_INCORRECT_MODEL_CALENDAR);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindEventCommand // instanceof handles nulls
            && titlePredicate.equals(((FindEventCommand) other).titlePredicate) // state check
            && fuzzySearchComparator.equals(((FindEventCommand) other).fuzzySearchComparator)
            && datePredicate.equals(((FindEventCommand) other).datePredicate)
            && tagsPredicate.equals(((FindEventCommand) other).tagsPredicate));
    }
}
