---
layout: page
title: User Guide
---

# TeamSync User Guide

TeamSync is a contact management app designed to streamline group project management, **built by National University of Singapore (NUS) students, for NUS students**.

Designed with simplicity and efficiency in mind, TeamSync makes it easy to add teammates' contact details, schedule meetings, and stay organized. It combines the intuitive visuals of a Graphical User Interface (GUI) with the speed and precision of a Command Line Interface (CLI). Built-in validation checks help prevent errors, ensuring a seamless experience.

Whether you're new to CLI apps or an experienced user, TeamSync enables you to work faster than any GUI-only app—so you can focus on what truly matters.


--------------------------------------------------------------------------------------------------------------------

## Table of Contents

- [Quick Start](#quick-start)
- [Features](#features)
  - [Viewing help](#viewing-help--help)
  - [Adding a student](#adding-a-student--add)
  - [Listing all students](#listing-all-students--list)
  - [Editing a student](#editing-a-student--edit)
  - [Locating students by name](#locating-students-by-name-find)
  - [Deleting a student](#deleting-a-student--delete)
  - [Clearing all entries](#clearing-all-entries--clear)
  - [Exiting the program](#exiting-the-program--exit)
- [FAQ](#FAQ)
- [Known Issues](#known-issues)
- [Command Summary](#command-summary)
- [Glossary](#glossary)

## Quick Start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103T-F10-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for TeamSync.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all students.

   * `add -n John Doe -p 98765432 -p johnd@example.com -a John street, block 123, #01-01` : Adds a student named `John Doe` to TeamSync.

   * `delete 3` : Deletes the 3rd student shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

## Commands

<div markdown="block" class="alert alert-info">:information_source: **Info**

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add -n NAME`, `NAME` is a parameter which can be used as `add -n John Doe`.

* Items in square brackets are optional.<br>
  e.g `-n NAME [-t TAG]` can be used as `-n John Doe -t friend` or as `-n John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[-t TAG]…​` can be used as ` ` (i.e. 0 times), `-t friend`, `-t friend -t family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `-n NAME -p PHONE_NUMBER`, `-p PHONE_NUMBER -n NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Student Commands

Example of how a student is represented in TeamSync:

#### Adding a new student: `person add`

Adds a student to TeamSync.

Format: `person add -n NAME -p PHONE_NUMBER -e EMAIL -a ADDRESS [-t TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip**<br><br>
A student can have any number of tags (including 0)
</div>

Example:
* `person add -n John Doe -p 98765432 -e johndoe@example.com -a John street, block 123, #01-01`

#### Editing a student : `person edit`

Edits an existing student in TeamSync.

Format: `person edit INDEX [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-t TAG]…​`

<div markdown="1" class="alert alert-info">:information_source: **Info**

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …

* At least one of the optional fields must be provided.

* Existing values will be updated to the input values.

* When editing tags, the existing tags of the student will be removed i.e adding of tags is not cumulative.

* You can remove all the student’s tags by typing `-t ` without specifying any tags after it.
</div>

Example:
*  `person edit 1 -p 91234567 -e johndoe@example.com` Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.

#### Deleting a student : `person delete`

Deletes the specified student from TeamSync.

Format: `person delete INDEX`

<div markdown="1" class="alert alert-info">:information_source: **Info**

* Deletes the student at the specified `INDEX`.

* The index refers to the index number shown in the displayed student list.

* The index **must be a positive integer** 1, 2, 3, …​
</div>

Examples:
* `find Betsy` followed by `person delete 1` deletes the 1st student in the results of the `find` command.

#### Searching for a student: `person find`

Finds students whose names contain any of the specified KEYWORDS.

Format: `person find KEYWORD [KEYWORD]...`

<div markdown="1" class="alert alert-info">:information_source: **Info**

* The search is case-insensitive. e.g `hans` will match `Hans`

* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`

* Only the name is searched

* Only full words will be matched e.g. `Han` will not match `Hans`

* Students matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
</div>

Example:
* `person find alex david` returns `Alex Yeoh`, `David Li`

#### Listing all students: `person list`

Lists all students in TeamSync.

Format: `person list`

### Module Commands

#### Adding a module for a student: `module add`

Adds a module to an existing student.

Format: `module add INDEX MODULE_CODE DAY START_TIME END_TIME`

<div markdown="1" class="alert alert-info">:information_source: **Info**

* The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​

* The module code will follow NUS module code format (Department tag + 4-digit number + optional last letter)

* Module code is case-insensitive.<br> e.g. cs2103t and CS2103T will be regarded as the same module

* Module code given has to be new (Given student does not already have this module assigned)

* Day is 3-letter abbreviation of the day of week (MON, TUE, THU etc). It is case-insensitive

* Start time and End time is given in 24-hour format and in HH:MM
</div>

Example:
* `module add 1 cs2101 thu 12:00 15:00` assigns module CS2101 to the first student in TeamSync.

#### Deleting a module from a student : `module delete`

Deletes the specified module from an existing student in TeamSync.

Format: `module delete INDEX MODULE_CODE`

<div markdown="1" class="alert alert-info">:information_source: **Info**

* Deletes the module from the student at the specified `INDEX`.

* The index refers to the index number shown in the displayed student list.

* The index **must be a positive integer** 1, 2, 3, …​

* Module code has to be an existing module assigned to the specified student.

* Module code is case-insensitive. e.g cs2103t will match CS2103T
</div>

Example:
* `module delete 1 cs2101` deletes the module CS2101 from the 1st student in TeamSync.

### Meeting Commands

Example of how a meeting is represented in TeamSync:

#### Adding a meeting: `meeting add`

Creates a meeting with the specified date, start and end time

Format: `meeting add DATE START_TIME END_TIME`

<div markdown="1" class="alert alert-info">:information_source: **Info**

* DATE is in DD-MM-YYYY format

* Start time and End time is given in 24-hour format and in HH:MM
</div>

Examples:
* `meeting add 27-03-2025 12:00 15:00` creates a new meeting on 27th March 2025 from 12pm to 3pm.

#### Deleting a meeting: `meeting delete`

Deletes the meeting identified by the index number used in the displayed meeting list

Format: `meeting delete INDEX`

<div markdown="1" class="alert alert-info">:information_source: **Info**

* Deletes the meeting at the specified `INDEX`.

* The index refers to the index number shown in the displayed meeting list.

* The index **must be a positive integer** 1, 2, 3, …​
</div>

Example:
* `meeting delete 1` deletes the 1st meeting in TeamSync.

#### Listing all meetings : `meeting list`

Shows a list of all meetings in TeamSync.

Format: `meeting list`

Examples:
* `meeting list` lists all existing meetings in TeamSync.

### General Commands

#### Viewing help : `help`

Shows a message explaining how to access the user guide.

Format: `help`

#### Clearing all data : `clear`

Clears all students, modules and meetings from TeamSync.

Format: `clear`

<div markdown="span" class="alert alert-warning">:exclamation: **Warning**<br><br>
**Deletion is irreversible**! Make sure to only use this command if you are sure that the data is no longer needed.
</div>

#### Exiting TeamSync : `exit`

Exits TeamSync.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/dat-a addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add -n NAME -p PHONE_NUMBER -e EMAIL -a ADDRESS [-t TAG]…​` <br> e.g., `add -n James Ho -p 22224444 -e jamesho@example.com -a 123, Clementi Rd, 1234665 -t friend -t colleague`
**Edit** | `edit INDEX [-n NAME] [-p PHONE_NUMBER] [-e EMAIL] [-a ADDRESS] [-t TAG]…​`<br> e.g.,`edit 2 -n James Lee -e jameslee@example.com`
**List** | `list`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**AddModule** | `addmod INDEX MODULE_CODE DAY START_TIME END_TIME` <br> e.g., `addmod 1 cs2101 thu 12:00 15:00`
**DeleteModule** | `delmod INDEX MODULE_CODE` <br> e.g., `delmod 1 cs2101`
**AddMeeting** | `meeting DATE START_TIME END_TIME` <br> e.g., `meeting 27-03-2025 12:00 15:00`
**ListMeetings** | `listmeetings`
**DeleteMeeting** | `delmeeting INDEX` <br> e.g., `delmeeting 1`
**Clear** | `clear`
**Help** | `help`

--------------------------------------------------------------------------------------------------------------------

## Glossary

**Command Line Interface (CLI)**<br>
A text-based interface that allows users to interact with the application by typing commands

**Graphical User Interface (GUI)**<br>
A graphical interface that allows users to interact with the application through visual elements
