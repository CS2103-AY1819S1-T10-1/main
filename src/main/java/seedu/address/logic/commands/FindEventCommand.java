package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.SwitchToSearchTabEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.calendarevent.FuzzySearchComparator;
import seedu.address.model.calendarevent.TagsPredicate;
import seedu.address.model.calendarevent.TitleContainsKeywordsPredicate;

/**
 * Finds and lists all events in the calendar of the scheduler whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindEventCommand extends Command {

    public static final String COMMAND_WORD = "find event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose titles match any of "
        + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
        + "Also optionally filters the search by the specified tags (case-insensitive)."
        + "Parameters: " + COMMAND_WORD + " KEYWORD [MORE_KEYWORDS]... " + PREFIX_TAG + " TAG [MORE_TAGS]...\n"
        + "Example: " + COMMAND_WORD + "project tutorial exam \\tag cs1010s \\tag ma3220 \\tag cs2103";

    private final TitleContainsKeywordsPredicate titlePredicate;
    private final FuzzySearchComparator fuzzySearchComparator;
    private final TagsPredicate tagsPredicate;

    public FindEventCommand(TitleContainsKeywordsPredicate titlePredicate, FuzzySearchComparator fuzzySearchComparator,
                                TagsPredicate tagsPredicate) {
        this.titlePredicate = titlePredicate;
        this.fuzzySearchComparator = fuzzySearchComparator;
        this.tagsPredicate = tagsPredicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetFilteredCalendarEventList();
        model.updateFilteredCalendarEventList(titlePredicate);
        if (tagsPredicate.hasTags()) {
            model.addPredicate(tagsPredicate);
        }
        model.sortFilteredCalendarEventList(fuzzySearchComparator);

        EventsCenter.getInstance().post(new SwitchToSearchTabEvent());

        return new CommandResult(
            String.format(Messages.MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW,
                model.getFilteredCalendarEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindEventCommand // instanceof handles nulls
            && titlePredicate.equals(((FindEventCommand) other).titlePredicate) // state check
            && fuzzySearchComparator.equals(((FindEventCommand) other).fuzzySearchComparator)
            && tagsPredicate.equals(((FindEventCommand) other).tagsPredicate));
    }
}
