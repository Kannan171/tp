package syncsquad.teamsync.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import syncsquad.teamsync.model.AddressBook;
import syncsquad.teamsync.model.ReadOnlyAddressBook;
import syncsquad.teamsync.model.person.Address;
import syncsquad.teamsync.model.person.Email;
import syncsquad.teamsync.model.person.Name;
import syncsquad.teamsync.model.person.Person;
import syncsquad.teamsync.model.person.Phone;
import syncsquad.teamsync.model.schedule.Day;
import syncsquad.teamsync.model.schedule.Module;
import syncsquad.teamsync.model.schedule.ModuleCode;
import syncsquad.teamsync.model.schedule.ScheduleTime;
import syncsquad.teamsync.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), getModuleSet("CS2103T FRI 1600 1800", "CS2101 THU 1200 1500")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), getModuleSet("ACC1701 MON 1200 1400")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), getModuleSet("ES2660 TUE 1200 1500", "IS1108 MON 1400 1600")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), getModuleSet("GESS1035 TUE 1800 2000")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), getModuleSet("MA1301 WED 1500 1530", "CDE2605 THU 1700 1900")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), getModuleSet("BN2001 FRI 0830 1130", "BT1101 THU 1600 1800"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a module set containing the list of strings given.
     */
    public static Set<Module> getModuleSet(String... strings) {
        return Arrays.stream(strings)
                .map((s) -> {
                    String[] params = s.split(" ");
                    ModuleCode moduleCode = new ModuleCode(params[0]);
                    Day day = new Day(params[1]);
                    ScheduleTime startScheduleTime = new ScheduleTime(params[2]);
                    ScheduleTime endScheduleTime = new ScheduleTime(params[3]);
                    return new Module(moduleCode, day, startScheduleTime, endScheduleTime);
                })
                .collect(Collectors.toSet());
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
