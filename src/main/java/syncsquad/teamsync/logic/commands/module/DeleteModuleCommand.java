package syncsquad.teamsync.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static syncsquad.teamsync.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import syncsquad.teamsync.commons.core.index.Index;
import syncsquad.teamsync.commons.util.ToStringBuilder;
import syncsquad.teamsync.logic.Messages;
import syncsquad.teamsync.logic.commands.CommandResult;
import syncsquad.teamsync.logic.commands.exceptions.CommandException;
import syncsquad.teamsync.model.Model;
import syncsquad.teamsync.model.module.Module;
import syncsquad.teamsync.model.module.ModuleCode;
import syncsquad.teamsync.model.person.Person;

/**
 * Deletes a module from the person identified using person index and module
 * code.
 */
public class DeleteModuleCommand extends ModuleCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_GROUP_WORD + " " + COMMAND_WORD
            + ": Deletes the module from person from index of the person and module code.\n"
            + "Parameters: INDEX (must be a positive integer) MODULE_CODE\n"
            + "Example: " + COMMAND_GROUP_WORD + " " + COMMAND_WORD + " 1 CS2103T";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module from: %1$s";

    private final Index targetIndex;
    private final ModuleCode moduleCode;

    /**
     * Initialises DeleteModuleCommand object
     * 
     * @param targetIndex Index of person to delete module
     * @param moduleCode  Module code of module to be deleted
     */
    public DeleteModuleCommand(Index targetIndex, ModuleCode moduleCode) {
        this.targetIndex = targetIndex;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDeleteModule = lastShownList.get(targetIndex.getZeroBased());
        boolean exists = personToDeleteModule.getModules().stream()
                .anyMatch((m) -> m.getModuleCode().equals(moduleCode));
        if (!exists) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE);
        }
        Set<Module> moduleSet = new HashSet<>(personToDeleteModule.getModules());
        moduleSet.removeIf((m) -> m.getModuleCode().equals(moduleCode));
        Person newPerson = new Person(personToDeleteModule.getName(), personToDeleteModule.getPhone(),
                personToDeleteModule.getEmail(), personToDeleteModule.getAddress(),
                personToDeleteModule.getTags(), moduleSet);
        model.setPerson(personToDeleteModule, newPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, Messages.format(newPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteModuleCommand)) {
            return false;
        }

        DeleteModuleCommand otherDeleteModuleCommand = (DeleteModuleCommand) other;
        return targetIndex.equals(otherDeleteModuleCommand.targetIndex)
                && moduleCode.equals(otherDeleteModuleCommand.moduleCode);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("moduleCode", moduleCode)
                .toString();
    }
}
