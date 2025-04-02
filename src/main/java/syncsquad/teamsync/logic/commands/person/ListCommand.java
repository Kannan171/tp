package syncsquad.teamsync.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static syncsquad.teamsync.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import syncsquad.teamsync.logic.commands.CommandResult;
import syncsquad.teamsync.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends PersonCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
