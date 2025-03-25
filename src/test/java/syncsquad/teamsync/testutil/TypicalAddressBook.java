package syncsquad.teamsync.testutil;

import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import syncsquad.teamsync.model.AddressBook;
import syncsquad.teamsync.model.meeting.Meeting;
import syncsquad.teamsync.model.person.Person;


/**
 * A utility class containing a list of {@code Person} and {@code Meeting} objects to be used in tests.
 */
public class TypicalAddressBook {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    public static final Meeting JAN_MEETING = new MeetingBuilder().withDate("01-01-2025")
            .withStartTime("1:00")
            .withEndTime("4:30")
            .build();
    public static final Meeting FEB_MEETING = new MeetingBuilder().withDate("02-02-2025")
            .withStartTime("3:00")
            .withEndTime("5:55")
            .build();
    public static final Meeting JUN_MEETING = new MeetingBuilder().withDate("06-06-2025")
            .withStartTime("4:45")
            .withEndTime("10:59")
            .build();
    public static final Meeting JUL_MEETING = new MeetingBuilder().withDate("07-07-2025")
            .withStartTime("12:55")
            .withEndTime("16:00")
            .build();
    public static final Meeting NOV_MEETING = new MeetingBuilder().withDate("11-11-2025")
            .withStartTime("18:00")
            .withEndTime("21:00")
            .build();
    public static final Meeting DEC_MEETING = new MeetingBuilder().withDate("12-12-2025")
            .withStartTime("20:00")
            .withEndTime("23:59")
            .build();

    private TypicalAddressBook() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Meeting meeting : getTypicalMeetings()) {
            ab.addMeeting(meeting);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Meeting> getTypicalMeetings() {
        return new ArrayList<>(Arrays.asList(JAN_MEETING, FEB_MEETING, JUN_MEETING, JUL_MEETING,
                NOV_MEETING, DEC_MEETING));
    }
}
