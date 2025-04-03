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

<div markdown="span" class="alert alert-info">:information_source: **Note Box**<br><br>
Provides additional information or clarifications about a feature or command. Look for these when you need more details about how something works.
</div>

<div markdown="span" class="alert alert-success">:bulb: **Tip Box**<br><br>
Offers helpful tips and best practices. These can help you use TeamSync more effectively.
</div>

<div markdown="span" class="alert alert-warning">:exclamation: **Warning Box**<br><br>
Highlights important warnings or potential issues. Pay special attention to these to avoid problems.
</div>

<div markdown="span" class="alert alert-danger">:warning: **Danger Box**<br><br>
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

1. Refer to the [Commands](#commands) below for details of each command.

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

* Commands are case sensitive

* Words in `UPPER_CASE` are the parameters to be supplied by the user

* Items in square brackets are optional

* Items with `…`​ after them can be used multiple times including zero times

* Extraneous parameters for commands that do not take in parameters (such as `help`, `meeting list`, `exit` and `clear`) will be ignored
</div>

<div markdown="span" class="alert alert-warning">:exclamation: **Warning**<br><br>
If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as whitespaces may be omitted when copied over to TeamSync.
</div>

<br><br>
### Teammate Commands

#### Adding a new teammate: `person add`

Adds a teammate to TeamSync.

**Format**: `person add -n NAME -p PHONE_NUMBER -e EMAIL -a ADDRESS [-t TAG]… [-m MODULE]…`

<div markdown="1" class="alert alert-info">:information_source: **Note**

* Names should only contain alphanumeric characters and spaces, and it should not be blank

* Phone numbers should only contain numbers, and it should be at least 3 digits long

* Emails should be of the format local-part@domain

* Tags should only contain alphanumeric characters

* See [adding a module for a teammate](#adding-a-module-for-a-teammate-module-add) for more information on the format for module details
</div>

<div markdown="span" class="alert alert-success">:bulb: **Tip**<br><br>
A teammate can have any number of tags and modules (including 0).
</div>

**Example**: `person add -n John Doe -p 98765432 -e johndoe@example.com -a John street, block 123, #01-01`

Adds a person with the name `John Doe` with phone number `98765432`, email address `johndoe@example.com` and address `John street, block 123, #01-01` to TeamSync.

<div markdown="span" class="alert alert-success">:bulb: **Tip**<br><br>

Unable to add a teammate? TeamSync does not allow you to add [duplicate teammates](#duplicate-teammate).
</div>

#### Editing a teammate : `person edit`

Edits an existing teammate in TeamSync.

**Format**: `person edit INDEX [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-t TAG]… [-m MODULE]…`

<div markdown="1" class="alert alert-info">:information_source: **Note**

* Edits the teammate at the specified [`INDEX`](#index), as shown in the displayed teammate list.

* At least one of the optional fields must be provided.

* Existing values will be updated to the input values.

* You can remove all of a teammate's tags by typing `-t ` without specifying any tags after it

* You can remove all of a teammate's modules by typing `-m ` without specifying any tags after it

* See [adding a new teammate](#adding-a-new-teammate-person-add) for more information on the format for contact details
</div>

<div markdown="span" class="alert alert-warning">:exclamation: **Warning**<br><br>

When editing tags / modules, **all the existing tags / modules of the teammate will be removed**!
</div>

**Example**: `person edit 1 -p 91234567 -e johndoe@example.com`

Edits the phone number and email address of the 1st teammate to be `91234567` and `johndoe@example.com` respectively.

#### Deleting a teammate : `person delete`

Deletes the specified teammate from TeamSync.

**Format**: `person delete INDEX`

<div markdown="1" class="alert alert-info">:information_source: **Note**

Deletes the teammate at the specified [`INDEX`](#index), as shown in the displayed teammate list.
</div>

**Example**: `person delete 1`

Deletes the first teammate, as shown in the displayed teammate list.

<div markdown="span" class="alert alert-warning">:exclamation: **Warning**<br><br>
Deleting a teammate is **irreversible**!
</div>

#### Searching for a teammate: `person find`

Finds teammates in TeamSync whose names contain any of the specified `KEYWORDS`.

**Format**: `person find KEYWORD [KEYWORD]...`

<div markdown="1" class="alert alert-info">:information_source: **Note**

* The search is case-insensitive

  **Example**: `hans` will match `Hans`

* The order of the keywords does not matter

  **Example**: `Hans Bo` will match `Bo Hans`

* Only the name is searched

* Only full words will be matched

  **Example**: `Han` will not match `Hans`

* Teammates matching at least one keyword will be returned (i.e. `OR` search)

  **Example**: `Hans Bo` will return `Hans Gruber`, `Bo Yang`
</div>

**Example**: `person find alex david`

Displays the teammates `Alex Yeoh` and `David Li`

#### Listing all teammates: `person list`

Lists all teammates in TeamSync.

**Format**: `person list`

**Example**: `person list`

<div markdown="span" class="alert alert-success">:bulb: **Tip**<br><br>

Use `person list` after `find` to exit the filtered view and display all your teammates' information.
</div>


### Module Commands

#### Adding a module for a teammate: `mod add`

Adds a module to an existing teammate.

**Format**: `mod add INDEX MODULE_CODE DAY START_TIME END_TIME`

<div markdown="1" class="alert alert-info">:information_source: **Note**

* Adds a module for the teammate at the specified [`INDEX`](#index), as shown in the displayed teammate list.

* `MODULE_CODE` follows the NUS module code format: **Department tag** (2 - 4 letters) + **4-digit number** + **optional last letter**

  **Example**: `CS2103T` and `CS2040` are both valid module codes, but `COMSCI123` is not

* `MODULE_CODE` is case-insensitive

  **Example**: Both `CS2103T` and `cs2103t` are regarded as the same module code

* `DAY` is 3-letter abbreviation of the day of week (Mon, Tue, Wed, …). It is case-insensitive

* `START_TIME` and `END_TIME` should be in 24-hour HH:MM format

  **Example**: `14:00` is valid, but `1400` and `2pm` are not
</div>

**Example**: `mod add 1 CS2101 Thu 12:00 15:00`

Adds the module CS2101 to the first teammate, as shown in the displayed teammate list.

<div markdown="span" class="alert alert-success">:bulb: **Tip**<br><br>

Unable to add a module? TeamSync does not allow you to add [duplicate modules](#duplicate-module) to the same teammate.
</div>

#### Deleting a module from a teammate : `mod delete`

Deletes the specified module from an existing teammate in TeamSync.

**Format**: `mod delete INDEX MODULE_CODE`

<div markdown="1" class="alert alert-info">:information_source: **Note**

* Deletes the module from the teammate at the specified [`INDEX`](#index), as shown in the displayed teammate list.

* `MODULE_CODE` follows the NUS module code format: **Department tag** (2 - 4 letters) + **4-digit number** + **optional last letter**

  **Example**: `CS2103T` and `CS2040` are both valid module codes, but `COMSCI123` is not

* `MODULE_CODE` is case-insensitive

  **Example**: Both `CS2103T` and `cs2103t` are regarded as the same module code

* Module code has to be an existing module assigned to the specified teammate.
</div>

**Example**: `mod delete 1 CS2101`

Deletes the module CS2101 from the first teammate in TeamSync.

<div markdown="span" class="alert alert-warning">:exclamation: **Warning**<br><br>
Deleting a module is **irreversible**!
</div>

### Meeting Commands

#### Adding a meeting: `meeting add`

Adds a meeting with the specified date, start and end time to TeamSync.

**Format**: `meeting add DATE START_TIME END_TIME`

<div markdown="1" class="alert alert-info">:information_source: **Note**

* `DATE` should be in DD-MM[-YYYY] format

  **Example**: `24-02-2025` and `24-02` are both valid dates, but `24 Feb` is not

* `START_TIME` and `END_TIME` should be in 24-hour HH:MM format

  **Example**: `14:00` is valid, but `1400` and `2pm` are not
</div>

**Example**: `meeting add 27-03-2025 12:00 15:00`

Adds a new meeting on 27 Mar 2025 from 12pm to 3pm.

#### Deleting a meeting: `meeting delete`

Deletes the specified meeting

**Format**: `meeting delete INDEX`

<div markdown="1" class="alert alert-info">:information_source: **Note**
Deletes the meeting at the specified [`INDEX`](#index), as shown in the displayed meeting list.
</div>

**Example**: `meeting delete 1`

Deletes the first meeting in TeamSync.

<div markdown="span" class="alert alert-warning">:exclamation: **Warning**<br><br>
Deleting a meeting is **irreversible**!
</div>

#### Listing all meetings : `meeting list`

Shows a list of all meetings in TeamSync.

**Format**: `meeting list`

**Example**: `meeting list`

Lists all existing meetings in TeamSync.

### General Commands

#### Viewing help : `help`

Shows a message explaining how to access the user guide.

**Format**: `help`

**Example**: `help`

Displays a pop-up

#### Clearing all data : `clear`

Clears all teammates, modules and meetings from TeamSync.

**Format**: `clear`

**Example**: `clear`

<div markdown="span" class="alert alert-danger">:warning: **Danger**<br><br>
**Deletion is irreversible**! Only use this command if you are sure that all the data is no longer needed.
</div>

#### Exiting TeamSync : `exit`

Exits TeamSync.

**Format**: `exit`

**Example**: `exit`

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
**Add a module for a teammate** | `mod add INDEX MODULE_CODE DAY START_TIME END_TIME`         |  `mod add 1 cs2101 thu 12:00 15:00`
**Delete a module from a teammate** | `mod delete INDEX MODULE_CODE` | `mod delete 1 cs2101`

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

**Q**: It is troublesome to start the app via terminal every time. Can I simply launch the app by double-clicking?
<br>
**A**: Yes, although the recommended way is to launch via terminal. 

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
