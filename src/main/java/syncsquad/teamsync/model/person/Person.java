package syncsquad.teamsync.model.person;

import static syncsquad.teamsync.commons.util.CollectionUtil.requireAllNonNull;
import static syncsquad.teamsync.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static syncsquad.teamsync.logic.parser.CliSyntax.PREFIX_EMAIL;
import static syncsquad.teamsync.logic.parser.CliSyntax.PREFIX_MODULE;
import static syncsquad.teamsync.logic.parser.CliSyntax.PREFIX_NAME;
import static syncsquad.teamsync.logic.parser.CliSyntax.PREFIX_PHONE;
import static syncsquad.teamsync.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import syncsquad.teamsync.commons.util.ToStringBuilder;
import syncsquad.teamsync.model.module.Module;
import syncsquad.teamsync.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Person implements Comparable<Person> {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Module> modules = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Set<Module> modules) {
        requireAllNonNull(name, phone, email, address, modules, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.modules.addAll(modules);
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable module set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Module> getModules() {
        return Collections.unmodifiableSet(this.modules);
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getEmail().equals(getEmail());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && modules.equals(otherPerson.modules)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, modules, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("modules", modules)
                .add("tags", tags)
                .toString();
    }

    /**
     * Returns a addable string representation of the person
     * in the format of -n NAME -p PHONE -e EMAIL -a ADDRESS -t TAGS -m MODULES
     */
    public String toExportString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME).append(name.fullName).append(" ");
        sb.append(PREFIX_PHONE).append(phone.value).append(" ");
        sb.append(PREFIX_EMAIL).append(email.value).append(" ");
        sb.append(PREFIX_ADDRESS).append(address.value).append(" ");

        if (!tags.isEmpty()) {
            tags.forEach(tag -> sb.append(PREFIX_TAG).append(tag.tagName).append(" "));
        }

        if (!modules.isEmpty()) {
            modules.forEach(module -> sb.append(PREFIX_MODULE).append(module.toExportString()).append(" "));
        }

        return sb.toString().trim();
    }

    @Override
    public int compareTo(Person other) {
        if (other == null) {
            throw new NullPointerException();
        }

        return name.compareTo(other.name);
    }
}
