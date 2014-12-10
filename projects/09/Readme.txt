How to compile and run the code
1. use jackComplier to compile the src/PigDice dir
2. use VMEmulator to run complied VM code(open src/PigDice folder)

Game introduction 

The user will play against the computer.
On a turn, a player rolls the die repeatedly until either:

> A 1 is rolled
> The player chooses to hold (stop rolling)

If a 1 is rolled, that player's turn ends and no points are earned.
If the player chooses to hold, all of the points rolled during that turn are added to his or her score.

Scoring Examples

Example 1: User rolls a 3 and decides to continue. He then chooses to roll seven more times (6, 6, 6, 4, 5, 6, 1).
Because he rolled a 1, the user's turn ends and he earns 0 points.

Example 2: The computer rolls a random number of times before holding. For example, the computer rolls 4 times.
(6, 3, 4, 2) and then holds. The computer accumulates 15 points (6+3+4+2=15).

Game play is returned to the user, who must roll as least once, and so on.

Game End

When a player reaches a total of 20 or more points, the game ends and that player is the winner. (I change the upper bound of the score fro 100 to 20, because if roll too many times, the heap of hack machine will overflow)

For a more detailed description of the game, see: http://en.wikipedia.org/wiki/Pig_(dice_game)

P.S.
I write a random class to generate pseudo random number, but it is not that random because the seed is hard coded in the code.
