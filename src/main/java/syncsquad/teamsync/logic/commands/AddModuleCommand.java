package syncsquad.teamsync.logic.commands;

import static java.util.Objects.requireNonNull;
import static syncsquad.teamsync.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import syncsquad.teamsync.commons.core.index.Index;
import syncsquad.teamsync.commons.util.ToStringBuilder;
import syncsquad.teamsync.logic.Messages;
import syncsquad.teamsync.logic.commands.exceptions.CommandException;
import syncsquad.teamsync.model.Model;
import syncsquad.teamsync.model.person.Person;
import syncsquad.teamsync.model.schedule.Module;

/**
 * Adds a module to an existing person in the address book.
 */
public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = "addmod";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the module to the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "MODULE_CODE "
            + "DAY "
            + "START_TIME "
            + "END_TIME \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "CS2103T FRI 1600 1800";

    public static final String MESSAGE_SUCCESS = "Added Module to Person: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists for the person.";

    private final Index index;
    private final Module module;

    /**
     * @param index of the person in the filtered person list to edit
     * @param module module to add to the person
     */
    public AddModuleCommand(Index index, Module module) {
        requireNonNull(index);
        requireNonNull(module);

        this.index = index;
        this.module = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        if (personToEdit.getModules().stream().anyMatch(module::isSameModule)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        personToEdit.addModule(module);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personToEdit)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddModuleCommand)) {
            return false;
        }

        AddModuleCommand otherAddModuleCommand = (AddModuleCommand) other;
        return index.equals(otherAddModuleCommand.index)
                && module.equals(otherAddModuleCommand.module);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("module", module)
                .toString();
    }
}
