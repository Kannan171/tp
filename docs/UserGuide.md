---
layout: page
title: User Guide
---

## About TeamSync

TeamSync is a contact management app designed to streamline group project management, **built by National University of Singapore (NUS) students, for NUS students**.

Designed with simplicity and efficiency in mind, TeamSync makes it easy to add group members' contact details, schedule meetings, and stay organized. It combines the intuitive visuals of a Graphical User Interface (GUI) with the speed and precision of a Command Line Interface (CLI). Built-in validation checks help prevent errors, ensuring a seamless experience.

Whether you're new to CLI applications or an experienced user, TeamSync enables you to work faster than any GUI-only app — so you can focus on what truly matters.

<div markdown="span" class="alert alert-primary">:bulb: **Tip**<br><br>

New users can refer to the [Quick Start](#quick-start) section on how to get started.<br>

Experienced users may wish to consult the [Command Summary](#command-summary) for a quick overview of the commands.
</div>

## Table of Contents

- [About TeamSync](#about-teamsync)
- [Table of Contents](#table-of-contents)
- [Features](#features)
- [Quick Start](#quick-start)
- [Commands](#commands)
  - [Student Commands](#student-commands)
    - [Adding a new student](#adding-a-new-student-person-add)
    - [Editing a student](#editing-a-student--person-edit)
    - [Deleting a student](#deleting-a-student--person-delete)
    - [Searching for a student](#searching-for-a-student-person-find)
    - [Listing all students](#listing-all-students-person-list)
  - [Module Commands](#module-commands)
    - [Adding a module for a student](#adding-a-module-for-a-student-module-add)
    - [Deleting a module from a student](#deleting-a-module-from-a-student--module-delete)
  - [Meeting Commands](#meeting-commands)
    - [Adding a meeting](#adding-a-meeting-meeting-add)
    - [Deleting a meeting](#deleting-a-meeting-meeting-delete)
    - [Listing all meetings](#listing-all-meetings--meeting-list)
  - [General Commands](#general-commands)
    - [Viewing help](#viewing-help--help)
    - [Clearing all data](#clearing-all-data--clear)
    - [Exiting TeamSync](#exiting-teamsync--exit)
- [Command Summary](#command-summary)
  - [Student Commands](#student-commands-1)
  - [Module Commands](#module-commands-1)
  - [Meeting Commands](#meeting-commands-1)
  - [General Commands](#general-commands-1)
- [FAQ](#FAQ)
- [Known Issues](#known-issues)
- [Glossary](#glossary)

## Features

TeamSync allows you to manage your group projects easily.

1. Quickly **add, edit and delete** the contact details of your group members
2. **Manage and view** each member's timetable.
3. **Schedule** meetings
4. **Visualise** your group's timetable at a glance

### Built-in validation

TeamSync provides built-in validation to ensure that errors are caught as early as possible.

It guarantees
1. No duplicate contacts
2. No module conflicts for each group member
3. No overlapping meetings

Additionally, TeamSync performs basic validation on other contact details.

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

### Command Summary

#### Student Commands

Action | Format                                                                     | Examples
-------|----------------------------------------------------------------------------|---------
**Add a student** | `person add -n NAME -p PHONE_NUMBER -e EMAIL -a ADDRESS [-t TAG]…`         |  `person add -n John Doe -p 98765432 -e johndoe@example.com -a John street, block 123, #01-01`
**Edit a student** | `person edit INDEX [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-t TAG]…` | `person edit 1 -p 91234567 -e johndoe@example.com`
**Delete a student** | `person delete INDEX`                                                      | `person delete 1`
**List all students** | `person list`                                                              | `person list`
**Search for a student** | `person find KEYWORD [MORE_KEYWORDS]`                                      | `person find James Jake`

#### Module Commands

Action | Format                                                                     | Examples
-------|----------------------------------------------------------------------------|---------
**Add a module for a student** | `module add INDEX MODULE_CODE DAY START_TIME END_TIME`         |  `module add 1 cs2101 thu 12:00 15:00`
**Delete a module from a student** | `module delete INDEX MODULE_CODE` | `module delete 1 cs2101`

#### Meeting Commands

Action | Format                                                                     | Examples
-------|----------------------------------------------------------------------------|---------
**Add a meeting** | `meeting add DATE START_TIME END_TIME` | `meeting 27-03-2025 12:00 15:00`
**Delete a meeting** | `meeting delete INDEX` | `meeting delete 1`
**List all meetings** | `meeting list` | `meeting list`

#### General Commands

Action | Format                                                                     | Examples
-------|----------------------------------------------------------------------------|---------
**View help** | `help` | `help`
**Clear all data** | `clear` | `clear`
**Exit TeamSync** | `exit` | `exit`


## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.


## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

## Glossary

**Command Line Interface (CLI)**<br>
A text-based interface that allows users to interact with the application by typing commands

**Index**<br>
An numeric identifier for a student or a meeting

**Module**<br>
A course offered by NUS

**Student**<br>
An NUS student stored as a contact in TeamSync

**Tag**<br>
Optional information associated with a student to facilitate categorisation

**Graphical User Interface (GUI)**<br>
A graphical interface that allows users to interact with the application through visual elements
