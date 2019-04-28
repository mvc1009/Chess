# Chess Game

## What we do?
We are doing a Player vs Player chess in java!
All media files that we use are [here!][medialink]

***

## What we want to do?
In the future we want to implement two versions for this game. The Player vs Player (PVP) onine, and the Player vs Computer (PVC) implemmented with IA.

***

## How we did?
We considered to do a class Piece and subclass for all the pieces. All piecces of the chessboard has treated as a object with an [ImageIcon][ImageIcon]. We treat the Cheesboard like the background only an image that is displayed on a X and Y cordinates.

We established for all the code the following statements:
- **About colors:** White -> TRUE, Black -> FALSE
- **About pieces:** TOWER -> 1, HORSE -> 2, BISHOP -> 3, QUEEN -> 4, KING -> 5, PAWN -> 6.
- **About board:** We established boxes (HitBox), each box corresponds a position on the chessboard.
In chess the movements are described with the piece, and the position of these chessboard (Ex. E1 (KING))
So we imitated them into the boxes: The letters are displayed by the number of the alphabet. (Ex. 51 (KING))

![hitbox](/readme_media/hitbox_example.png)

We simbolize with a yellow DOT the possible positions of the selected piece. ([Dot][dot])

To remark the piece that is selected from the user, we print a yellow Square arround the box. ([Stroke Pattern][pattern]).


![dot_exemple](/readme_media/yellowDot_example.png "Possible Positions Example")

***

## How to play our game

First of all you don't need to compile because we did for you!
Follow the following steps to open the game:

- Download the game from the Github as a ZIP.
~~~
$ curl -L -o Chess.zip https://github.com/mvc1009/Chess
~~~
- Decompress the ZIP package
~~~
$ unzip Chess.zip
~~~
- Exectue the game
~~~
$ java -cp build/ chess.QuitButtonEx
~~~
- Remove de ZIP file
~~~
$ remove Chess.zip
~~~
***
## First play
At the moment to execute our game a menu will be displayed. You have to options to choose, play and quit.

![menu](/readme_media/menu_example.png "menu")

At the moment when the pawn arrives to the final of the chessboard, we need to choose a piece to substitute it.

![final_pawn](/readme_media/pawnFInal_Example.png "Pawn at the Final")

A menu will appear to choose the piece to substitue the pawn.

![black_menu](/readme_media/blackChoose_example.png "Black Choose menu")                    ![white_menu](/readme_media/whiteChoose_example.png "White Choose menu")

***

## Bibliography
[Java2D games Tutorial][Java2Dgames]  
[Java2D Tutorial][Java2D]  
[JavaSwing Tutorial][JavaSwing]  


[ImageIcon]: https://docs.oracle.com/javase/7/docs/api/javax/swing/ImageIcon.html
[medialink]: https://github.com/mvc1009/Chess/tree/master/multimedia
[Java2D]: http://zetcode.com/gfx/java2d/
[Java2Dgames]: http://zetcode.com/tutorials/javagamestutorial/
[JavaSwing]:http://zetcode.com/tutorials/javaswingtutorial/
[dot]: https://github.com/mvc1009/Chess/blob/master/chess/Dot.java
[pattern]: https://github.com/mvc1009/Chess/blob/master/chess/StrokePattern.java
