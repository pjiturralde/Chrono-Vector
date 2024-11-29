# Time Puzzle

## Puzzle Premise

This project will be a top down puzzle game involving projectiles and other objects such as pressure plates.
There will be a player that can only move in four directions, up, down, left, and right.
This player can move one tile per button press.
The main premise of the puzzle is there will be an arrow/indicator pointing in either of the four directions the player can move.
If the player moves in the direction of the arrow then time will move one step forward (aka. the projectiles will move forward one tile or two depending on their given velocity).
If the player decides to move opposite to the arrow then time will move backwards, and if the player moves orthogonally to the arrow time won't move at all (the projectiles will stay still).
The arrow indicating the direction of time may or may not be static in further levels (it may switch directions mid game).
There will also be a timer and/or a move counter to see how fast and efficiently the player can complete the level.
This software is designed for people who like to play games/puzzles in their free time.
I've always been interested in both the concept of time and the creation of games in general.
To me this is a good opportunity to both create a puzzle I find interesting and to get more practice in with Java.

## User Stories

- As a user, I want to be able to complete a level and add its level stats to my completed level history.
- As a user, I want to be able to view my completed level histories.
- As a user, I want to be able to move my player in four directions.
- As a user, I want to be able to select a level to play.
- As a user, I want to be given the option to save my completed levels data
- As a user, I want to be given the option to load my completed levels data

## Instructions for End User

- You can add level stats to your Player's level history by successfully completing a level.
- You can clear your level history by clicking the clear button at the top of the level history panel.
- You can sort your level history by using the combo box in the top right of your level history panel.
- You can find a visual component anywhere in the main menu of this game, such as the title icon, or the menu buttons.
- You can save the state of the application to file in the front main menu.
- You can load the state of the application from file in the front main menu.

## Phase 4: Task 2

Fri Nov 29 09:51:33 PST 2024
New level stats were added to Player's level 1 history


Fri Nov 29 09:51:36 PST 2024
New level stats were added to Player's level 1 history


Fri Nov 29 09:51:39 PST 2024
New level stats were added to Player's level 1 history


Fri Nov 29 09:51:44 PST 2024
New level stats were added to Player's level 1 history


Fri Nov 29 09:51:47 PST 2024
New level stats were added to Player's level 1 history


Fri Nov 29 09:51:51 PST 2024
New level stats were added to Player's level 1 history


Fri Nov 29 09:51:53 PST 2024
Sorted Player's level 1 history by moves and then time


Fri Nov 29 09:51:58 PST 2024
Sorted Player's level 1 history by time and then moves


Fri Nov 29 09:51:59 PST 2024
Sorted Player's level 1 history by attempts


Fri Nov 29 09:52:00 PST 2024
Cleared Player's level 1 history

## Phase 4: Task 3

Overall I like how the structure of this looks but there's just a couple things I would change. The first thing would be that I would have another class to handle the levelHistory other than Player just because it would make more sense to have a Player class to handle any movement in game and have another separate class to act as the user as opposed to the having the playable character handle the user actions. Another thing I would change is to have another super class for some of the other panels so that there's less clutter within each individual file. Otherwise, I would like to move some of the code inside GameApp into another file such as anything involving the layout and the border panels in the front main menu just so everything is more compartmentalized and so GameApp is easier to look through.