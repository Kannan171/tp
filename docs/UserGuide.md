---
layout: page
title: User Guide
---

## About TeamSync

TeamSync is a contact management app designed to streamline group project management, **built by National University of Singapore (NUS) students, for NUS students**.

Designed with simplicity and efficiency in mind, TeamSync makes it easy to add teammates' contact details, schedule meetings, and stay organized. It combines the intuitive visuals of a Graphical User Interface (GUI) with the speed and precision of a Command Line Interface (CLI). Built-in validation checks help prevent errors, ensuring a seamless experience.

Whether you are new to CLI applications or are an experienced user, TeamSync enables you to work faster than any GUI-only app — so you can focus on what truly matters.

<div markdown="span" class="alert alert-success">:bulb: **Tip**<br><br>

New users can refer to the [Quick Start](#quick-start) section on how to get started.<br>

Experienced users may wish to consult the [Command Summary](#command-summary) for a quick overview of the commands.
</div>

## Table of Contents

- [About TeamSync](#about-teamsync)
- [Table of Contents](#table-of-contents)
- [Features](#features)
  - [Built-in validation](#built-in-validation)
  - [Timetable view](#timetable-view)
- [Quick Start](#quick-start)
- [Commands](#commands)
  - [Teammate Commands](#teammate-commands)
    - [Adding a new teammate](#adding-a-new-teammate-person-add)
    - [Editing a teammate](#editing-a-teammate--person-edit)
    - [Deleting a teammate](#deleting-a-teammate--person-delete)
    - [Searching for a teammate](#searching-for-a-teammate-person-find)
    - [Listing all teammates](#listing-all-teammates-person-list)
  - [Module Commands](#module-commands)
    - [Adding a module for a teammate](#adding-a-module-for-a-teammate-module-add)
    - [Deleting a module from a teammate](#deleting-a-module-from-a-teammate--module-delete)
  - [Meeting Commands](#meeting-commands)
    - [Adding a meeting](#adding-a-meeting-meeting-add)
    - [Deleting a meeting](#deleting-a-meeting-meeting-delete)
    - [Listing all meetings](#listing-all-meetings--meeting-list)
  - [General Commands](#general-commands)
    - [Viewing help](#viewing-help--help)
    - [Clearing all data](#clearing-all-data--clear)
    - [Exiting TeamSync](#exiting-teamsync--exit)
- [Command Summary](#command-summary)
  - [Teammate Commands](#teammate-commands-1)
  - [Module Commands](#module-commands-1)
  - [Meeting Commands](#meeting-commands-1)
  - [General Commands](#general-commands-1)
- [FAQ](#FAQ)
- [Known Issues](#known-issues)
- [Glossary](#glossary)

## Features

TeamSync allows you to manage your group projects easily.

1. Quickly **add, edit and delete** the contact details of your teammates
2. **Manage and view** each teammate's timetable
3. **Schedule** meetings
4. **Visualise** your group's timetable at a glance

### Built-in validation

TeamSync provides built-in validation to ensure that errors are caught as early as possible.

It guarantees
1. No duplicate contacts
2. No module conflicts for each teammate
3. No overlapping meetings

Additionally, TeamSync performs basic validation on other contact details.

### Timetable view

TeamSync offers both a textual and a visual representation of schedules. Its colour-coded timetable clearly distinguishes between meetings and each teammate's modules, allowing you to coordinate schedules efficiently.

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

   * `list` : Lists all teammates.

   * `add -n John Doe -p 98765432 -p johnd@example.com -a John street, block 123, #01-01` : Adds a teammate named `John Doe` to TeamSync.

   * `delete 3` : Deletes the 3rd teammate shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

## Commands

TeamSync provides you with 4 types of commands:
* [Teammate Commands](#teammate-commands)
* [Module Commands](#module-commands)
* [Meeting Commands](#meeting-commands)
* [General Commands](#general-commands)

A command consists of a <span style="color : green; font-weight: bold;">command group</span> (with the exception of General Commands), <span style="color : brown; font-weight: bold;">command word</span>, and zero or more <span style="color : darkorange; font-weight: bold;">parameters</span>

**Example**: <code style="color : green; font-weight: bold;">meeting</code> <code style="color : brown; font-weight: bold;">add</code> <code style="color : darkorange; font-weight: bold;">15-02-2024 14:00 15:00</code>

<div markdown="1" class="alert alert-info">:information_source: **Info**

* For each command, the correct syntax is specified under "Format".

* Words in `UPPER_CASE` are the parameters to be supplied by the user.

* Items in square brackets are optional.

* Items with `…`​ after them can be used multiple times including zero times.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `meeting list`, `exit` and `clear`) will be ignored.
</div>

<div markdown="span" class="alert alert-warning">:exclamation: **Warning**<br><br>
If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as whitespaces may be omitted when copied over to TeamSync.
</div>

### Teammate Commands

Example of how a teammate is represented in TeamSync:

#### Adding a new teammate: `person add`

Adds a teammate to TeamSync.

Format: `person add -n NAME -p PHONE_NUMBER -e EMAIL -a ADDRESS [-t TAG]…​`

<div markdown="span" class="alert alert-success">:bulb: **Tip**<br><br>
A teammate can have any number of tags (including 0).
</div>

**Example**: `person add -n John Doe -p 98765432 -e johndoe@example.com -a John street, block 123, #01-01`

<div markdown="span" class="alert alert-success">:bulb: **Tip**<br><br>

Unable to add a teammate? TeamSync does not allow you to add [duplicate teammates](#duplicate-teammate).
</div>

#### Editing a teammate : `person edit`

Edits an existing teammate in TeamSync.

Format: `person edit INDEX [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-t TAG]…​`

<div markdown="1" class="alert alert-info">:information_source: **Info**

* Edits the teammate at the specified `INDEX`. The index refers to the index number shown in the displayed teammate list. The index **must be a positive integer** 1, 2, 3, …

* At least one of the optional fields must be provided.

* Existing values will be updated to the input values.

* When editing tags, the existing tags of the teammate will be removed i.e adding of tags is not cumulative.

* You can remove all the teammate’s tags by typing `-t ` without specifying any tags after it.
</div>

**Example**: `person edit 1 -p 91234567 -e johndoe@example.com` Edits the phone number and email address of the 1st teammate to be `91234567` and `johndoe@example.com` respectively.

#### Deleting a teammate : `person delete`

Deletes the specified teammate from TeamSync.

Format: `person delete INDEX`

<div markdown="1" class="alert alert-info">:information_source: **Info**

* Deletes the teammate at the specified `INDEX`.

* The index refers to the index number shown in the displayed teammate list.

* The index **must be a positive integer** 1, 2, 3, …​
</div>

**Example**: `find Betsy` followed by `person delete 1` deletes the 1st teammate in the results of the `find` command.

#### Searching for a teammate: `person find`

Finds teammates whose names contain any of the specified KEYWORDS.

Format: `person find KEYWORD [KEYWORD]...`

<div markdown="1" class="alert alert-info">:information_source: **Info**

* The search is case-insensitive. **Example**: `hans` will match `Hans`

* The order of the keywords does not matter. **Example**: `Hans Bo` will match `Bo Hans`

* Only the name is searched

* Only full words will be matched **Example**: `Han` will not match `Hans`

* Teammates matching at least one keyword will be returned (i.e. `OR` search).
  **Example**: `Hans Bo` will return `Hans Gruber`, `Bo Yang`
</div>

**Example**: `person find alex david` returns `Alex Yeoh`, `David Li`

#### Listing all teammates: `person list`

Lists all teammates in TeamSync.

Format: `person list`

### Module Commands

#### Adding a module for a teammate: `module add`

Adds a module to an existing teammate.

Format: `module add INDEX MODULE_CODE DAY START_TIME END_TIME`

<div markdown="1" class="alert alert-info">:information_source: **Info**

* The index refers to the index number shown in the displayed teammate list. The index **must be a positive integer** 1, 2, 3, …​

* The module code will follow NUS module code format (Department tag + 4-digit number + optional last letter)

* Module code is case-insensitive.<br> **Example**: cs2103t and CS2103T will be regarded as the same module

* Module code given has to be new (Given teammate does not already have this module assigned)

* Day is 3-letter abbreviation of the day of week (MON, TUE, THU etc). It is case-insensitive

* Start time and End time is given in 24-hour format and in HH:MM
</div>

**Example**: `module add 1 cs2101 thu 12:00 15:00` assigns module CS2101 to the first teammate in TeamSync.

#### Deleting a module from a teammate : `module delete`

Deletes the specified module from an existing teammate in TeamSync.

Format: `module delete INDEX MODULE_CODE`

<div markdown="1" class="alert alert-info">:information_source: **Info**

* Deletes the module from the teammate at the specified `INDEX`.

* The index refers to the index number shown in the displayed teammate list.

* The index **must be a positive integer** 1, 2, 3, …​

* Module code has to be an existing module assigned to the specified teammate.

* Module code is case-insensitive. **Example**: cs2103t will match CS2103T
</div>

**Example**: `module delete 1 cs2101` deletes the module CS2101 from the 1st teammate in TeamSync.

### Meeting Commands

Example of how a meeting is represented in TeamSync:

#### Adding a meeting: `meeting add`

Creates a meeting with the specified date, start and end time

Format: `meeting add DATE START_TIME END_TIME`

<div markdown="1" class="alert alert-info">:information_source: **Info**

* DATE is in DD-MM-YYYY format

* Start time and End time is given in 24-hour format and in HH:MM
</div>

**Example**: `meeting add 27-03-2025 12:00 15:00` creates a new meeting on 27th March 2025 from 12pm to 3pm.

#### Deleting a meeting: `meeting delete`

Deletes the meeting identified by the index number used in the displayed meeting list

Format: `meeting delete INDEX`

<div markdown="1" class="alert alert-info">:information_source: **Info**

* Deletes the meeting at the specified `INDEX`.

* The index refers to the index number shown in the displayed meeting list.

* The index **must be a positive integer** 1, 2, 3, …​
</div>

**Example**: `meeting delete 1` deletes the 1st meeting in TeamSync.

#### Listing all meetings : `meeting list`

Shows a list of all meetings in TeamSync.

Format: `meeting list`

**Example**: `meeting list` lists all existing meetings in TeamSync.

### General Commands

#### Viewing help : `help`

Shows a message explaining how to access the user guide.

Format: `help`

#### Clearing all data : `clear`

Clears all teammates, modules and meetings from TeamSync.

Format: `clear`

<div markdown="span" class="alert alert-warning">:exclamation: **Warning**<br><br>
**Deletion is irreversible**! Make sure to only use this command if you are sure that the data is no longer needed.
</div>

#### Exiting TeamSync : `exit`

Exits TeamSync.

Format: `exit`

### Command Summary

#### Student Commands

Action | Format                                                                     | Example
-------|----------------------------------------------------------------------------|---------
**Add a teammate** | `person add -n NAME -p PHONE_NUMBER -e EMAIL -a ADDRESS [-t TAG]…`         |  `person add -n John Doe -p 98765432 -e johndoe@example.com -a John street, block 123, #01-01`
**Edit a teammate** | `person edit INDEX [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-t TAG]…` | `person edit 1 -p 91234567 -e johndoe@example.com`
**Delete a teammate** | `person delete INDEX`                                                      | `person delete 1`
**List all teammates** | `person list`                                                              | `person list`
**Search for a teammate** | `person find KEYWORD [MORE_KEYWORDS]`                                      | `person find James Jake`

#### Module Commands

Action | Format                                                                     | Example
-------|----------------------------------------------------------------------------|---------
**Add a module for a teammate** | `module add INDEX MODULE_CODE DAY START_TIME END_TIME`         |  `module add 1 cs2101 thu 12:00 15:00`
**Delete a module from a teammate** | `module delete INDEX MODULE_CODE` | `module delete 1 cs2101`

#### Meeting Commands

Action | Format                                                                     | Example
-------|----------------------------------------------------------------------------|---------
**Add a meeting** | `meeting add DATE START_TIME END_TIME` | `meeting 27-03-2025 12:00 15:00`
**Delete a meeting** | `meeting delete INDEX` | `meeting delete 1`
**List all meetings** | `meeting list` | `meeting list`

#### General Commands

Action | Format                                                                     | Example
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

<a name="duplicate-teammate"></a>**Duplicate teammate**<br>
Two teammates are duplicates if they have the same name (case-insensitive) or if they differ only in whitespace

**Index**<br>
An numeric identifier for a teammate or a meeting

**Module**<br>
A course offered by NUS

**Teammate**<br>
An NUS group project teammate stored as a contact in TeamSync.

**Tag**<br>
Optional information associated with a teammate to facilitate categorisation

**Graphical User Interface (GUI)**<br>
A graphical interface that allows users to interact with the application through visual elements
