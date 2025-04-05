---
layout: page
title: User Guide
---

## About TeamSync

TeamSync is a contact management app designed to streamline group project management **for NUS students**.

Designed with simplicity and efficiency in mind, TeamSync makes it easy to add teammates' contact details, schedule meetings, and stay organized. It combines the intuitive visuals of a Graphical User Interface (GUI) with the speed and precision of a Command Line Interface (CLI). Built-in validation checks help prevent errors, ensuring a seamless experience.

Whether you are new to CLI applications or are an experienced user, TeamSync enables you to work faster than any GUI-only app — so you can focus on what truly matters.

## How to Use This Guide

This User Guide is designed to help you get the most out of TeamSync. Here's how to navigate it effectively:

1. **Quick Start** (For New Users)
   - If you're new to TeamSync, start with the [Quick Start](#quick-start) section
   - This section provides step-by-step instructions to get you up and running quickly

2. **Command Reference** (For All Users)
   - The [Commands](#commands) section contains detailed information about all available commands
   - Commands are organized into categories:
     - [Teammate Commands](#teammate-commands)
     - [Module Commands](#module-commands)
     - [Meeting Commands](#meeting-commands)
     - [General Commands](#general-commands)

3. **Command Summary** (For Quick Reference)
   - The [Command Summary](#command-summary) section provides a quick overview of all commands
   - Use this section as a quick reference when you're familiar with the commands

4. **Additional Resources**
   - [FAQ](#faq): Answers to common questions
   - [Troubleshooting](#troubleshooting): Solutions to common issues
   - [Glossary](#glossary): Definitions of key terms

### Understanding Callout Boxes

Throughout this guide, you'll encounter different types of callout boxes that provide important information:

<div markdown="1" class="alert alert-info">:information_source: **Note Box**<br><br>
Provides additional information or clarifications about a feature or command. Look for these when you need more details about how something works.
</div>

<div markdown="1" class="alert alert-success">:bulb: **Tip Box**<br><br>
Offers helpful tips and best practices. These can help you use TeamSync more effectively.
</div>

<div markdown="1" class="alert alert-warning">:exclamation: **Warning Box**<br><br>
Highlights important warnings or potential issues. Pay special attention to these to avoid problems.
</div>

<div markdown="1" class="alert alert-danger">:warning: **Danger Box**<br><br>
Indicates critical warnings or irreversible actions. Always read these carefully before proceeding.
</div>

## Table of Contents

- [About TeamSync](#about-teamsync)
- [How to Use This Guide](#how-to-use-this-guide)
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
    - [Exporting a teammmate](#exporting-a-teammate-person-export)
  - [Module Commands](#module-commands)
    - [Adding a module for a teammate](#adding-a-module-for-a-teammate-module-add)
    - [Deleting a module from a teammate](#deleting-a-module-from-a-teammate--module-delete)
  - [Meeting Commands](#meeting-commands)
    - [Adding a meeting](#adding-a-meeting-meeting-add)
    - [Deleting a meeting](#deleting-a-meeting-meeting-delete)
  - [General Commands](#general-commands)
    - [Viewing help](#viewing-help--help)
    - [Changing week displayed](#changing-week-displayed-showdate)
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

TeamSync allows you to manage your group projects easily by:

1. quickly **adding, editing and deleting** the contact details of your teammates
2. **managing** each teammate's modules and time commitments
3. **scheduling** meetings
4. **visualising** your group's timetable at a glance

### Stay Organised

TeamSync is built with seamless integration and flawless organisation in mind.

TeamSync comes with neat and adjustable views for your teammates, your schedule and your meetings to suit your visual needs.

TeamSync provides built-in validation to ensure that errors are caught as early as possible.

It guarantees:

1. no duplicate contacts
2. no module conflicts for each teammate
3. no overlapping meetings

TeamSync allows you to export your contact, allowing you to synchronize easily with all your teammates.

### Timetable View

TeamSync offers both a textual and a visual representation of schedules. Its colour-coded timetable clearly distinguishes between meetings and each teammate's modules, allowing you to coordinate schedules efficiently.

## Quick Start

1. Ensure you have Java `17` or above installed on your computer.<br>
   Guide for [**Windows users**](https://se-education.org/guides/tutorials/javaInstallationWindows.html), [**Mac Users**](https://se-education.org/guides/tutorials/javaInstallationMac.html), [**Linux users**](https://se-education.org/guides/tutorials/javaInstallationLinux.html)

1. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103T-F10-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for TeamSync.

1. Open the command prompt (or terminal).

   For **Windows**:
   Open the Command Prompt (you can find it by searching "cmd" in the Start menu).
   
   For **Mac**:
   Open the Terminal app (you can find it in Applications > Utilities).
   
   For **Linux**:
   Open the Terminal app (you can find it in Applications).

1. Type cd followed by the folder where you saved `teamsync.jar`, then press Enter

   **Example**: `cd Downloads`

1. Then type: `java -jar teamsync.jar` and press Enter. TeamSync should open in a few seconds.

1. You can use TeamSync by typing the command in the command box and pressing Enter to execute it.

   **Example**: Typing `help` and pressing Enter will open the help window.

1. Refer to the [Commands](#commands) section below for details of each command.

## Commands

TeamSync provides you with 4 types of commands:
* [Teammate Commands](#teammate-commands)
* [Module Commands](#module-commands)
* [Meeting Commands](#meeting-commands)
* [General Commands](#general-commands)

A command consists of a <span style="color : green; font-weight: bold;">command group</span> (with the exception of General Commands), <span style="color : brown; font-weight: bold;">command word</span>, and zero or more <span style="color : darkorange; font-weight: bold;">parameters</span>

**Example**: <code style="color : green; font-weight: bold;">meeting</code> <code style="color : brown; font-weight: bold;">add</code> <code style="color : darkorange; font-weight: bold;">15-02-2024 14:00 15:00</code>

<div markdown="1" class="alert alert-info">:information_source: **Note**

* For each command, the correct syntax is specified under "Format"

* Commands are case-insensitive

* Words in `UPPER_CASE` are the parameters to be supplied by the user

* Items in square brackets are optional

* Items with `…`​ after them can be used multiple times including zero times

* Extraneous parameters for commands that do not take in parameters (such as `help`, `person list`, `exit` and `clear`) will be ignored
</div>

<div markdown="1" class="alert alert-warning">:exclamation: **Warning**<br><br>
If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as whitespaces may be omitted when copied over to TeamSync.
</div>

<br><br>
### Teammate Commands

#### Adding a new teammate: `person add`

Adds a teammate to TeamSync.

**Format**: `person add -n NAME -p PHONE_NUMBER -e EMAIL -a ADDRESS [-t TAG]… [-m MODULE]…`

<div markdown="1" class="alert alert-info">:information_source: **Note**

* Names should only contain alphanumeric characters and spaces, and it should not be blank
  
  TODO: UPDATE IF NECESSARY

* Phone numbers should only contain numbers, and it should be at least 3 digits long

* Emails should be of the format local-part@domain

* Tags should only contain alphanumeric characters

* See [adding a module for a teammate](#adding-a-module-for-a-teammate-module-add) for more information on the format for module details

* Unable to add a teammate? TeamSync does not allow you to add [duplicate teammates](#duplicate-teammate).
</div>

<div markdown="1" class="alert alert-success">:bulb: **Tip**<br><br>
A teammate can have any number of tags and modules (including 0).
</div>

**Example**: `person add -n John Doe -p 12345678 -e johndoe@u.nus.com -a RC4 -t Backend -m CS2101 Thu 12:00 15:00 -m CS2103T Fri 16:00 18:00`

Adds a person with the following information to TeamSync:<br>
**Name**: `John Doe`<br>
**Phone number**: `12345678`<br>
**Email** `johndoe@u.nus.com`<br>
**Address**: `RC4`<br>
**Tags**: `Backend`<br>
**Modules**: `CS2101` on Thursdays from 12:00 to 15:00, `CS2103T` on Fridays from 16:00 to 18:00

TODO: ADD SCREENSHOT

#### Editing a teammate : `person edit`

Edits an existing teammate in TeamSync.

**Format**: `person edit INDEX [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-t TAG]…`

<div markdown="1" class="alert alert-info">:information_source: **Note**

* Edits the teammate at the specified [`INDEX`](#index), as shown in the displayed teammate list.

* At least one of the optional fields must be provided.

* Existing values will be updated to the input values.

* You can remove all of a teammate's tags by typing `-t ` without specifying any tags after it

* See [adding a new teammate](#adding-a-new-teammate-person-add) for more information on the format for contact details

* TODO: EXPLAIN INDEX CHANGES
</div>

<div markdown="1" class="alert alert-warning">:exclamation: **Warning**<br><br>

When editing tags, **all the existing tags of the teammate will be removed**!
</div>

<div markdown="1" class="alert alert-success">:bulb: **Tip**<br><br>

Trying to add or delete a module for a teammate? Use [`module add`](#adding-a-module-for-a-teammate-module-add) or [`module delete`](#deleting-a-module-from-a-teammate--module-delete) instead!
</div>

**Example**: `person edit 1 -p 87654321 -e newmail@u.nus.com -t`

Edits the information of first teammate, as shown in the displayed teammate list:<br>
**Phone number**: Changed to `87654321`<br>
**Email**: Changed to `newmail@u.nus.com`<br>
**Tags**: All tags are removed

#### Deleting a teammate : `person delete`

Deletes the specified teammate from TeamSync.

**Format**: `person delete INDEX`

<div markdown="1" class="alert alert-info">:information_source: **Note**

Deletes the teammate at the specified [`INDEX`](#index), as shown in the displayed teammate list.
</div>

**Example**: `person delete 1`

Deletes the first teammate, as shown in the displayed teammate list.

<div markdown="1" class="alert alert-warning">:exclamation: **Warning**<br><br>
Deleting a teammate is **irreversible**!
</div>

#### Searching for a teammate: `person find`

Finds teammates in TeamSync whose names contain any of the specified `KEYWORDS`.

**Format**: `person find KEYWORD [KEYWORD]...`

<div markdown="1" class="alert alert-info">:information_source: **Note**

* The search is case-insensitive<br>
  **Example**: `hans` will match `Hans`

* The order of the keywords does not matter<br>
  **Example**: `Hans Bo` will match `Bo Hans`

* Only the name is searched<br>

* Only full words will be matched<br>
  **Example**: `Han` will not match `Hans`

* Teammates matching at least one keyword will be returned<br>
  **Example**: `Hans Bo` will return `Hans Gruber`, `Bo Yang`
</div>

**Example**: `person find alex david`

Displays the teammates `Alex Yeoh` and `David Li`

#### Listing all teammates: `person list`

Lists all teammates in TeamSync.

**Format**: `person list`

**Example**: `person list`

<div markdown="1" class="alert alert-success">:bulb: **Tip**<br><br>

Use `person list` after `find` to exit the filtered view and display all your teammates' information.
</div>

### Exporting a teammate: `person export`

Exports a teammate in TeamSync as text.

**Format**: `person export INDEX`

<div markdown="1" class="alert alert-info">:information_source: **Note**

* Exports the teammate at the specified [`INDEX`](#index), as shown in the displayed teammate list.

* The exported text can be inputted into TeamSync as a command to recreate a teammate with the same information.
</div>

**Example**: `person export 1`

Exports the first teammate, as shown in the displayed teammate list.

TODO: ADD SCREENSHOT

<br><br>
### Module Commands

#### Adding a module for a teammate: `module add`

Adds a module to an existing teammate in TeamSync.

**Format**: `module add INDEX MODULE_CODE DAY START_TIME END_TIME`

<div markdown="1" class="alert alert-info">:information_source: **Note**

* Adds a module for the teammate at the specified [`INDEX`](#index), as shown in the displayed teammate list.

* `MODULE_CODE` follows the NUS module code format:<br>
  **Department tag** (2 - 4 letters) + **4-digit number** + **optional last letter**<br>
  **Example**: `CS2103T` and `CS2040` are both valid module codes, but `COMSCI123` is not

* `MODULE_CODE` is case-insensitive<br>
  **Example**: Both `CS2103T` and `cs2103t` are regarded as the same module code

* `DAY` is a 3-letter abbreviation of the day of week (Mon, Tue, Wed, …). It is case-insensitive

* `START_TIME` and `END_TIME` should be in 24-hour HH:MM format<br>
  **Example**: `14:00` is valid, but `1400` and `2pm` are not

* Unable to add a module? TeamSync does not allow you to add [duplicate](#duplicate-module) or [overlapping modules](#overlapping-module) to the same teammate.
</div>

**Example**: `module add 1 CS2101 Thu 12:00 15:00`

Adds the module CS2101 to the first teammate, as shown in the displayed teammate list.

<div markdown="1" class="alert alert-success">:bulb: **Tip**<br><br>

Unable to add a module? TeamSync does not allow you to add [duplicate modules](#duplicate-module) to the same teammate.
</div>

#### Deleting a module from a teammate : `module delete`

Deletes the specified module from an existing teammate in TeamSync.

**Format**: `module delete INDEX MODULE_CODE`

<div markdown="1" class="alert alert-info">:information_source: **Note**

* Deletes the module from the teammate at the specified [`INDEX`](#index), as shown in the displayed teammate list.

* `MODULE_CODE` follows the NUS module code format:<br>
  **Department tag** (2 - 4 letters) + **4-digit number** + **optional last letter**<br>
  **Example**: `CS2103T` and `CS2040` are both valid module codes, but `COMSCI123` is not

* `MODULE_CODE` is case-insensitive<br>
  **Example**: Both `CS2103T` and `cs2103t` are regarded as the same module code

* The teammate should have the specified module assigned to them.
</div>

**Example**: `module delete 1 CS2101`

Deletes the module CS2101 from the first teammate, as shown in the displayed teammate list.

<div markdown="1" class="alert alert-warning">:exclamation: **Warning**<br><br>
Deleting a module is **irreversible**!
</div>

<br><br>
### Meeting Commands

#### Adding a meeting: `meeting add`

Adds a meeting to TeamSync.

**Format**: `meeting add DATE START_TIME END_TIME`

<div markdown="1" class="alert alert-info">:information_source: **Note**

* `DATE` should be in DD-MM[-YYYY] format<br>
  **Example**: `24-02-2025` and `24-02` are both valid dates, but `24 Feb` is not

* `DATE` is assumed to be the current year if YYYY is not given

* `START_TIME` and `END_TIME` should be in 24-hour HH:MM format<br>
  **Example**: `14:00` is valid, but `1400` and `2pm` are not
</div>

**Example**: `meeting add 27-03-2025 12:00 15:00`

Adds a new meeting on 27 Mar 2025 from 12pm to 3pm.

#### Deleting a meeting: `meeting delete`

Deletes the specified meeting from TeamSync.

**Format**: `meeting delete INDEX`

<div markdown="1" class="alert alert-info">:information_source: **Note**<br><br>
Deletes the meeting at the specified [`INDEX`](#index), as shown in the displayed meeting list.
</div>

**Example**: `meeting delete 1`

Deletes the first meeting in TeamSync, as shown in the displayed meeting list.

<div markdown="1" class="alert alert-warning">:exclamation: **Warning**<br><br>
Deleting a meeting is **irreversible**!
</div>

<br><br>
### General Commands

#### Viewing help : `help`

Opens a help window displaying a summary of all the commands in TeamSync.

**Format**: `help`

**Example**: `help`

TODO: ADD SCREENSHOT

#### Changing week displayed: `showdate`

Changes the week displayed in the timetable view in TeamSync.

Format: `showdate DATE`
<div markdown="1" class="alert alert-info">:information_source: **Info**

* Displays the timetable for the week containing the specified DATE, starting on Monday.

* `DATE` should be in DD-MM[-YYYY] format<br>
  **Example**: `24-02-2025` and `24-02` are both valid dates, but `24 Feb` is not

* `DATE` is assumed to be the current year if YYYY is not given
</div>

Example: `showdate 03-04-2025`

Displays the timetable for 31 Mar 2025 (Mon) to 6 Apr 2025 (Sun).

#### Clearing all data : `clear`

Clears all teammates, modules and meetings from TeamSync.

**Format**: `clear`

**Example**: `clear`

<div markdown="1" class="alert alert-danger">:warning: **Danger**<br><br>
**Deletion is irreversible**! Only use this command if you are sure that all the data is no longer needed.
</div>

#### Exiting TeamSync : `exit`

Exits TeamSync.

**Format**: `exit`

**Example**: `exit`

<br><br>
## Saving the Data
TeamSync data is saved automatically after any command that changes the data. There is no need to save manually.

### Editing the Data File
TeamSync data is saved as a [JSON](#JSON) file. Advanced users are welcome to update data directly by editing `addressbook.json` located in the `data` subfolder of TeamSync's home folder.

TODO: CHANGE IF ADDRESSBOOK.JSON CHANGES

<div markdown="1" class="alert alert-danger">:warning: **Danger**<br><br>
**It is strongly recommended to make a backup of `addressbook.json` before editing it.**

If your edits to `addressbook.json` corrupts it and TeamSync is unable to load the data, **TeamSync will discard all data** and start with an empty data file at the next run.

Furthermore, **certain edits can cause the TeamSync to behave in unexpected ways**, even if TeamSync is able to load the data.

Therefore, **edit the file only if you are confident** that you can update it correctly.
</div>

### Command Summary

#### Student Commands

| Action                    | Format                                                                          | Example                                                                                                  |
|---------------------------|---------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| **Add a teammate**        | `person add -n NAME -p PHONE_NUMBER -e EMAIL -a ADDRESS [-t TAG]… [-m MODULE]…` | `person add -n John Doe -p 12345678 -e johndoe@u.nus.com -a RC4 -t Backend -m CS2101 Thu 12:00 15:00 -m CS2103T Fri 16:00 18:00` |
| **Edit a teammate**       | `person edit INDEX [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-t TAG]…`      | `person edit 1 -p 87654321 -e newmail@u.nus.com -t`                                                       |
| **Delete a teammate**     | `person delete INDEX`                                                           | `person delete 1`                                                                                        |
| **Search for a teammate** | `person find KEYWORD [MORE_KEYWORDS]`                                           | `person find alex david`                                                                                 |
| **List all teammates**    | `person list`                                                                   | `person list`                                                                                            |
| **Export a teammate**     | `person export`                                                                 | `person export 1`                                                                                        |

#### Module Commands

| Action                              | Format                                                 | Example                               |
|-------------------------------------|--------------------------------------------------------|---------------------------------------|
| **Add a module for a teammate**     | `module add INDEX MODULE_CODE DAY START_TIME END_TIME` | `module add 1 CS2101 Thu 12:00 15:00` |
| **Delete a module from a teammate** | `module delete INDEX MODULE_CODE`                      | `module delete 1 CS2101`              |

#### Meeting Commands

| Action               | Format                                 | Example                              |
|----------------------|----------------------------------------|--------------------------------------|
| **Add a meeting**    | `meeting add DATE START_TIME END_TIME` | `meeting add 27-03-2025 12:00 15:00` |
| **Delete a meeting** | `meeting delete INDEX`                 | `meeting delete 1`                   |

#### General Commands

| Action                    | Format          | Example               |
|---------------------------|-----------------|-----------------------|
| **View help**             | `help`          | `help`                |
| **Change week displayed** | `showdate DATE` | `showdate 04-04-2025` |
| **Clear all data**        | `clear`         | `clear`               |
| **Exit TeamSync**         | `exit`          | `exit`                |

## Troubleshooting

1\. **The GUI appears off screen** <br>
This may happen if you are using multiple screens. One solution is to delete the `preferences.json` file created by the application before running the application again.

2\. **The JAR file cannot be run** <br>
Ensure that you have Java 17 or later installed. Run `java --version` in your terminal to check your installed Java version.

3\. **My data is not being saved** <br>
Ensure that the application has write permissions to the directory. Alternatively, try running the app with administrator privileges.  

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the files in the data directory it creates with the files from the previous device.

**Q**: What are the system requirements for TeamSync?
<br>
**A**: Java 17 or later <br>
Operating System: Windows, MacOS, Linux only <br>
Only systems with a GUI is supported

**Q**: Can I use TeamSync without the CLI?
<br>
**A**: Although some operations such as `exit` do have a GUI button, TeamSync is optimised for CLI and functions mainly via the command line. 


## Glossary

| Terms                                                   | Definition                                                                                       |
|---------------------------------------------------------|--------------------------------------------------------------------------------------------------|
| **Command Line Interface (CLI)**                        | A text-based interface that allows users to interact with the application by typing commands     |
| <a name="duplicate-module"></a>**Duplicate module**     | Two modules are duplicates if they have the same module code                                     |
| <a name="duplicate-teammate"></a>**Duplicate teammate** | Two teammates are duplicates if they have the same email address                                 |
| <a name="index"></a>**Index**                           | A **positive integer** 1, 2, 3, … shown in TeamSync that identifies a teammate or a meeting      |
| **Module**                                              | A course offered by NUS                                                                          |
| **Teammate**                                            | An NUS group project teammate stored as a contact in TeamSync                                    |
| **Tag**                                                 | Optional information associated with a teammate to facilitate categorisation                     |
| **Graphical User Interface (GUI)**                      | A graphical interface that allows users to interact with the application through visual elements |
