package chess;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.HashMap;
import java.util.ConcurrentModificationException;


import chess.piece.*;

public class Board extends JPanel implements ActionListener {

    /*
    * We established for all the code the following statements:
    * About colors: White -> TRUE, Black -> FALSE
    * About pieces: TOWER -> 1, HORSE -> 2, BISHOP -> 3, QUEEN -> 4, KING -> 5, PAWN -> 6.
    * About board: We established boxes (HitBox), each box corresponds a position on the chessboard.
    *       In chess the movements are described with the piece, and the position of these chessboard (Ex. E1 (KING))
    *       so we imitated them into the boxes: The letters are displayed by the number of the alphabet. (Ex. 51 (KING))
    *
    * We simbolize with a yellow DOT the possible positions of the selected piece. (Dot)
    * To remark the piece that is selected from the user, we print a yellow Square arround the box. (Stroke Pattern)
    */
    public QuitButtonEx menu;
    public ChessGame game;
    public static final int TOWER = 1;
    public static final int HORSE = 2;
    public static final int BISHOP = 3;
    public static final int QUEEN = 4;
    public static final int KING = 5;
    public static final int PAWN = 6;

    public static final int INITIAL_X = 220;
    public static final int INITIAL_Y = 490;
    public static final int STEP = 60;
    public static final int OUTOFBOUND_X = 700;
    public static final int OUTOFBOUND_Y = 550;


    private final int DELAY = 10;
    private Timer timer;
    private Background background;
    private Chessboard chessboard;
    private StrokePattern strokepattern;
    private Dot dot;

    private boolean isWhiteTurn;

    HashMap<Integer, Piece> pieces;
    HashMap<Integer, Dot> posiblesMovements;
    HashMap<Integer, Dot> posiblesMovements2;
    HashMap<Integer, Dot> checkTest;
    HitTestAdapter mouse;
    private int boxPressed = 99;
    private int piecePressed = 99;
    private boolean isBoxPressed = false;

    private boolean largecastling = false;
    private boolean shortcastling = false;

    private boolean check = false;
    private boolean checkMate = false;
    private boolean pawnInChangingPosition = false;

    private int posWhiteKing = 51;
    private int posBlackKing = 58;

    public Board(QuitButtonEx menu, ChessGame game) {
        this.menu = menu;
        this.game = game;
        initBoard();
        initialPiecesPositions();
        initialPosiblePositions();
    }

    private void initBoard() {
        mouse = new HitTestAdapter();
        addMouseListener(mouse);
        setFocusable(true);

        background = new Background();
        chessboard = new Chessboard();
        strokepattern = new StrokePattern();

        isWhiteTurn = true;

        timer = new Timer(DELAY, this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background.getImage(),0,0,this);
        g2d.drawImage(chessboard.getImage(), 200,50,this);
        if(strokepattern.isVisible()){
          g2d.drawImage(strokepattern.getImage(), strokepattern.getX(), strokepattern.getY(), this);
        }
        for( Piece piece : pieces.values() ){
            g2d.drawImage(piece.getImage(), piece.getX(), piece.getY(), this);
        }
        //TURN LABEL
        if(check){
          String msgCheck = "CHECK";
          g2d.setColor(Color.BLUE);
          Font small = new Font("Calibri",Font.BOLD, 25);
          FontMetrics fm = getFontMetrics(small);
          g2d.setFont(small);
          g2d.drawString(msgCheck, 425,32);
        }
        if(!checkMate){
          for(Dot dot : posiblesMovements2.values()){
              if(dot.isVisible()){
                g2d.drawImage(dot.getImage(), dot.getX(), dot.getY(), this);
              }
          }
          String msg = "Turn: ";
          if(isWhiteTurn){
            msg = msg + "WHITE";
            g2d.setColor(Color.WHITE);
          }else{
            msg = msg + "BLACK";
            g2d.setColor(Color.BLACK);
          }
          Font small = new Font("Calibri",Font.BOLD, 25);
          FontMetrics fm = getFontMetrics(small);
          g2d.setFont(small);
          g2d.drawString(msg, 1025,32);
        }
        //When the pawn hits the las position it can turn into another piece:
        if(pawnInChangingPosition){
          String msg = "Turn the pawn into a:";
          String msg2= "(Press: q, b, h, t)";
          g2d.setColor(Color.BLACK);
          Font small = new Font("Calibri",Font.BOLD, 25);
          FontMetrics fm = getFontMetrics(small);
          g2d.setFont(small);
          g2d.drawString(msg, OUTOFBOUND_X + STEP/2, OUTOFBOUND_Y/8 );
          g2d.drawString(msg2, OUTOFBOUND_X + STEP/2, OUTOFBOUND_Y/8 + STEP/2);

        }
    }           //Print the objects on the board, like pieces, labels...
    @Override
    public void actionPerformed(ActionEvent e) {
        updateStroke();
        updatePieces();
        repaint();
    }

//+++++++++++++++++++  PIECES INICIALIZATINON +++++++++++++++++++++++++++

    private void initialPiecesPositions(){
        pieces = new HashMap<Integer,Piece>();
        //White initials:
        pieces.put(11, new Tower(true, false));
        pieces.put(21, new Horse(true, false));
        pieces.put(31, new Bishop(true,false));
        pieces.put(41, new Queen(true));
        pieces.put(51, new King(true));
        pieces.put(61, new Bishop(true, true));
        pieces.put(71, new Horse(true, true));
        pieces.put(81, new Tower(true, true));

        pieces.put(12, new Pawn(true, 1));
        pieces.put(22, new Pawn(true, 2));
        pieces.put(32, new Pawn(true, 3));
        pieces.put(42, new Pawn(true, 4));
        pieces.put(52, new Pawn(true, 5));
        pieces.put(62, new Pawn(true, 6));
        pieces.put(72, new Pawn(true, 7));
        pieces.put(82, new Pawn(true, 8));

        //Black initials:
        pieces.put(18, new Tower(false, false));
        pieces.put(28, new Horse(false, false));
        pieces.put(38, new Bishop(false,false));
        pieces.put(48, new Queen(false));
        pieces.put(58, new King(false));
        pieces.put(68, new Bishop(false, true));
        pieces.put(78, new Horse(false, true));
        pieces.put(88, new Tower(false, true));

        pieces.put(17, new Pawn(false, 1));
        pieces.put(27, new Pawn(false, 2));
        pieces.put(37, new Pawn(false, 3));
        pieces.put(47, new Pawn(false, 4));
        pieces.put(57, new Pawn(false, 5));
        pieces.put(67, new Pawn(false, 6));
        pieces.put(77, new Pawn(false, 7));
        pieces.put(87, new Pawn(false, 8));
    }

//+++++++++++++++++ POSSIBLE POSITIONS ++++++++++++++++++

//We put a DOT that when it is visible, these means that there are a possible movement
    private void initialPosiblePositions(){
      posiblesMovements = new HashMap<Integer, Dot>();
      posiblesMovements2 = new HashMap<Integer, Dot>();
      checkTest = new HashMap<Integer, Dot>();

      for(int i = 10; i <= 88; i++){
        if(i%10 != 9){
          posiblesMovements.put(i, new Dot(i));
          posiblesMovements2.put(i, new Dot(i));
          checkTest.put(i, new Dot(i));
        }
      }
    }
    private boolean validDot(HashMap<Integer, Piece> pieces2, int boxd, boolean colord){

      /*
      * Function that determinates if a DOT will be shown upper the piece.
      * If the piece is about the same colour with the clicked piece, the DOT will be shown.
      *   SUM:  detect if there are a piece in the middle of the piece trayectory and determinates if it can be eaten or not.
      */

      if(pieces2.containsKey(boxd)){
        if(pieces2.get(boxd).getColor() == !colord)
          return true;
        else
          return false;
      }
      return true;
    }

//Diferent possible positions of each type of piece

    private void pawnPosiblePositions(HashMap<Integer, Dot> posiblesMovements, HashMap<Integer, Piece> pieces2,int box, boolean color){
      if( color ) {     //White Pawns
        if(!pieces2.containsKey(box+1) && box%10 < 8)                                                          //Print a DOT if there are any piece in front of them
          posiblesMovements.get(box+1).setVisible(true);
        if(box%10 == 2 && !pieces2.containsKey(box+1) && !pieces2.containsKey(box+2) )              //If the pawn is in the initial position it can be moved 2 positions.
          posiblesMovements.get(box+2).setVisible(true);
                                                                                                  //The pawns eat in diagonal.
        if(pieces2.containsKey(box+11) && pieces2.get(box+11).getColor() != color){
          posiblesMovements.get(box+11).setVisible(true);
        }
        if(pieces2.containsKey(box-9) && pieces2.get(box-9).getColor() != color){
          posiblesMovements.get(box-9).setVisible(true);
        }
      }
      else{           //Black Pawns
        if(!pieces2.containsKey(box-1) )                                                           //Print a DOT if there are any piece in front of them
          posiblesMovements.get(box-1).setVisible(true);
        if(box%10 == 7 && !pieces2.containsKey(box-1) && !pieces2.containsKey(box-2) )              //If the pawn is in the initial position it can be moved 2 positions.
          posiblesMovements.get(box-2).setVisible(true);

        if(pieces2.containsKey(box-11) && pieces2.get(box-11).getColor() != color){                //The pawns eat in diagonal.
          posiblesMovements.get(box-11).setVisible(true);
        }
        if(pieces2.containsKey(box+9) && pieces2.get(box+9).getColor() != color){
          posiblesMovements.get(box+9).setVisible(true);
        }
      }
    }
    private void towerPosiblePositions(HashMap<Integer, Dot> posiblesMovements,HashMap<Integer, Piece> pieces2,int box){

      /*
      *The tower can move upwards, downwards, leftwards and rightwards.
      *If there are a piece in middle the tower can't pass through them.
      *These piece that are in middle if is from the enemy it can be eaten so a DOT will print on it.
      */

       int x = box/10;
       int y = box%10;
       boolean pieceInMiddleUP = false;
       boolean pieceInMiddleDOWN = false;
       boolean pieceInMiddleLEFT = false;
       boolean pieceInMiddleRIGHT = false;

       for(int i = 1; i <= 8 ; i++){
         // +++++++++++++ UPWARD +++++++++++++
         if(y+i < 9 && !pieceInMiddleUP){
           if(validDot(pieces2,box + i, pieces2.get(box).getColor())){
             posiblesMovements.get(box + i).setVisible(true);
             if(pieces2.containsKey(box + i)){
               pieceInMiddleUP = true;
             }
           }else{
             pieceInMiddleUP = true;
           }
         }
         // +++++++++++++ LEFTWARD +++++++++++++
         if(x-i > 0 && !pieceInMiddleLEFT){
           if(validDot(pieces2,box - i*10, pieces2.get(box).getColor())){
             posiblesMovements.get(box - i*10).setVisible(true);
             if(pieces2.containsKey(box - i*10)){
               pieceInMiddleLEFT = true;
             }
           }else{
             pieceInMiddleLEFT = true;
           }
         }
         // +++++++++++++ RIGHTWARD +++++++++++++
         if(x+i < 9 && !pieceInMiddleRIGHT){
           if(validDot(pieces2,box + i*10, pieces2.get(box).getColor())){
             posiblesMovements.get(box + i*10).setVisible(true);
             if(pieces2.containsKey(box + i*10)){
               pieceInMiddleRIGHT = true;
             }
           }else{
             pieceInMiddleRIGHT = true;
           }

         }
         // +++++++++++++ DOWNWARD +++++++++++++
         if(y-i > 0 && !pieceInMiddleDOWN ){
           if(validDot(pieces2,box - i, pieces2.get(box).getColor())){
             posiblesMovements.get(box - i).setVisible(true);
             if(pieces2.containsKey(box - i)){
               pieceInMiddleDOWN = true;
             }
           }else{
             pieceInMiddleDOWN = true;
           }
         }
       }
    }
    private void bishopPosiblePositions(HashMap<Integer, Dot> posiblesMovements,HashMap<Integer, Piece> pieces2,int box){
      /*
      *The Bishop can move in diagonal. The behaviour is like the tower.
      *If there are a piece in middle the tower can't pass through them.
      *These piece that are in middle if is from the enemy it can be eaten so a DOT will print on it.
      */

      int x = box/10;
      int y = box%10;
      boolean pieceInMiddleUPRIGHT = false;
      boolean pieceInMiddleDOWNRIGHT = false;
      boolean pieceInMiddleUPLEFT = false;
      boolean pieceInMiddleDOWNLEFT = false;

     for(int i = 1; i < 9 ; i++){
       // +++++++++++++ UPWARD & RIGHTWARD +++++++++++++
       if(x+i < 9 && y+i < 9 && !pieceInMiddleUPRIGHT){
         if(validDot(pieces2,box + i*10 + i, pieces2.get(box).getColor())){
           posiblesMovements.get(box + i*10 + i).setVisible(true);
           if(pieces2.containsKey(box + i*10 + i)){
             pieceInMiddleUPRIGHT = true;
           }
         }else{
           pieceInMiddleUPRIGHT = true;
         }
       }
       // +++++++++++++ UPWARD & LEFTWARD +++++++++++++
       if(x-i > 0 && y+i < 9 && !pieceInMiddleUPLEFT){
         if(validDot(pieces2,box - i*10 + i, pieces2.get(box).getColor())){
           posiblesMovements.get(box - i*10 + i).setVisible(true);
           if(pieces2.containsKey(box - i*10 + i)){
             pieceInMiddleUPLEFT = true;
           }
         }else{
           pieceInMiddleUPLEFT = true;
         }
       }
       // +++++++++++++ DOWNWARD & RIGHTWARD +++++++++++++
       if(x+i < 9 && y-i > 0 && !pieceInMiddleDOWNRIGHT){
         if(validDot(pieces2,box + i*10 - i, pieces2.get(box).getColor())){
           posiblesMovements.get(box + i*10 - i).setVisible(true);
           if(pieces2.containsKey(box + i*10 - i)){
             pieceInMiddleDOWNRIGHT = true;
           }
         }else{
           pieceInMiddleDOWNRIGHT = true;
         }

       }
       // +++++++++++++ DOWNWARD & LEFTWARD +++++++++++++
       if(x-i > 0 && y-i > 0 && !pieceInMiddleDOWNLEFT ){
         if(validDot(pieces2,box - i*10 - i, pieces2.get(box).getColor())){
           posiblesMovements.get(box - i*10 - i).setVisible(true);
           if(pieces2.containsKey(box - i*10 - i)){
             pieceInMiddleDOWNLEFT = true;
           }
         }else{
           pieceInMiddleDOWNLEFT = true;
         }
       }
     }
    }
    private void horsePosiblePositions(HashMap<Integer, Dot> posiblesMovements,HashMap<Integer, Piece> pieces2,int box){

      /*
      * The Horse can moves in L.
      * If the posible position there are a enemy piece, the horse will eat the enemy piece.
      */

      // +++++++++++++ TOP POINTS ++++++++++++
      if(box%10 <= 6){
        if(box/10 < 8 && validDot(pieces2,box+12, pieces2.get(box).getColor()) )
          posiblesMovements.get(box+12).setVisible(true);
        if(box/10 > 1 && validDot(pieces2,box-8, pieces2.get(box).getColor()) )
          posiblesMovements.get(box-8).setVisible(true);
        if(box/10 <= 6 && validDot(pieces2,box+21, pieces2.get(box).getColor()) )
          posiblesMovements.get(box+21).setVisible(true);
        if(box/10 >= 3 && validDot(pieces2,box-19, pieces2.get(box).getColor()) )
          posiblesMovements.get(box-19).setVisible(true);
      }
      // +++++++++++++ BOTTON POINTS ++++++++++++
      if(box%10 >= 3){
        if(box/10 < 8 && validDot(pieces2,box+8, pieces2.get(box).getColor()) )
          posiblesMovements.get(box+8).setVisible(true);
        if(box/10 > 1 && validDot(pieces2,box-12, pieces2.get(box).getColor()) )
          posiblesMovements.get(box-12).setVisible(true);
        if(box/10 <= 6 && validDot(pieces2,box+19, pieces2.get(box).getColor()) )
          posiblesMovements.get(box+19).setVisible(true);
        if(box/10 >= 3 && validDot(pieces2,box-21, pieces.get(box).getColor()) )
          posiblesMovements.get(box-21).setVisible(true);
      }
    }
    private void kingPosiblePositions(HashMap<Integer, Dot> posiblesMovements,HashMap<Integer, Piece> pieces2,int box){

      /*
      * The King is the most complicated piece to calculate de posible positions of the Chessboard
      * The King can move one position in all directions (UPWARDS, DOWNWARD, RIGHTWARD, LEFTWARD and DIAGONAL)
      * If there are many of these posible positions that are in CHECK the king can't move to there.
      * The King can be castled if the tower and the king haven't move yet.
      * If the king is in CHECK and don't have any posible position the game ends with a CHECKMATE.
      */


      // ++++++++++ RIGHTWARD & SHORT CASTLE ++++++++++
      if( validDot(pieces2,box+10, pieces2.get(box).getColor())){
       posiblesMovements.get(box + 10).setVisible(true);
       shortcastling = false;
       if(!pieces2.get(box).isMoved() && pieces2.containsKey(box + 30) &&
           pieces2.get(box+30).getType() == TOWER && !pieces2.get(box+30).isMoved()){
         posiblesMovements.get(box + 20).setVisible(true);
         shortcastling = true;
       }
     }
      // ++++++++++ LEFTWARD & LONG CASTLE ++++++++++
      if( validDot(pieces2,box-10, pieces2.get(box).getColor())){
        posiblesMovements.get(box - 10).setVisible(true);
        largecastling = false;
        if(!pieces2.get(box).isMoved() && pieces2.containsKey(box - 40) &&
            pieces2.get(box-40).getType() == TOWER && !pieces2.get(box-40).isMoved()){
          posiblesMovements.get(box - 20).setVisible(true);
          largecastling = true;
        }
      }
      // ++++++++++ UPWARD ++++++++++
      if(box%10 < 8){  //Puntos superiores
        if( validDot(pieces2,box+1, pieces2.get(box).getColor()))
          posiblesMovements.get(box + 1).setVisible(true);
        if( validDot(pieces2,box+11, pieces2.get(box).getColor()))
          posiblesMovements.get(box + 11).setVisible(true);
        if( validDot(pieces2,box-9, pieces2.get(box).getColor()))
          posiblesMovements.get(box - 9).setVisible(true);
      }
      // ++++++++++ DOWNWARD ++++++++++
      if(box%10 > 1){  //Puntos inferiores
          if( validDot(pieces2,box+9, pieces2.get(box).getColor()))
            posiblesMovements.get(box + 9).setVisible(true);
          if( validDot(pieces2,box-1, pieces2.get(box).getColor()))
            posiblesMovements.get(box - 1).setVisible(true);
          if( validDot(pieces2,box-11, pieces2.get(box).getColor()))
            posiblesMovements.get(box - 11).setVisible(true);
        }
    }

//++++++++++++++++++ UPDATING THE CHESSBOARD +++++++++++++++++

//Updating the yellow square arround the selected piece
    public void updateStroke(){
      if(strokepattern.isVisible()){
        strokepattern.move();
      }
    }
//Updating the positions of the pieces
    public void updatePieces(){
      for( Piece piece : pieces.values() ){
          if(piece.isMove()){
            piece.move();
          }
      }
    }

//++++++++++++++++++++++ DOTS DISPLAYING ++++++++++++++++++++++++++++

    public void posibleMovement(HashMap<Integer, Dot> map, HashMap<Integer, Piece> pieces2, Piece piece2Move, int box){ //rules
      //Esta usando el beginningBox de la clase Piece.
        switch(piece2Move.getType()){
          case TOWER:
              towerPosiblePositions(map,pieces2,box);
              break;
          case HORSE:
              horsePosiblePositions(map,pieces2,box);
              break;
          case BISHOP:
              bishopPosiblePositions(map,pieces2,box);
              break;
          case KING:
              kingPosiblePositions(map,pieces2,box);
              break;
          case QUEEN:
              towerPosiblePositions(map,pieces2,box);
              bishopPosiblePositions(map,pieces2,box);
              break;
          case PAWN:
              pawnPosiblePositions(map,pieces2,box, piece2Move.getColor());
              break;
        }
    }
    public void deletePosibleMovement(){

      /*
      * This function turn false the DOT visibility so clears all the possibles movements
      */

      for (Dot dot : posiblesMovements.values()){
        dot.setVisible(false);
      }
    }

    // +++++++++++++++++++++  FINISHIG THE GAME -> CHECKMATE ++++++++++++++++++++++++
       public void endGame(){
          EndGame end = new EndGame(this, isWhiteTurn);

       }
// +++++++++++++++++++++  FUNCTIONS HitTestAdapter USES ++++++++++++++++++++++++
    public int beginningBox(int xi, int yi){

      /*
      * From a X and Y cordinates that function returns the appropiate box of the chessboard
      */

      int i = 0;
      int x = 9;
      int y = 9;

      while(i < 8){
          if((xi > INITIAL_X + STEP*i) && xi < OUTOFBOUND_X){
            x = i+1;
          }
          if((yi < INITIAL_Y - STEP*i) && yi < OUTOFBOUND_Y){
            y= i+2;
          }
          else if(yi >= INITIAL_Y&& yi< OUTOFBOUND_Y){
            y = 1;
          }
          i++;
      }
      int result = x*10 + y;
      return result;
    }
    public boolean isValidBox(int boxPressed){

      /*
      * With a box introduced by parameter this function will say if this box is on the Chessboard.
      */

      if(boxPressed/10 == 9 || boxPressed%10 == 9){
        return false;
      }
      return true;
    }

// ++++++++++++++++++++++++++++ MOUSE LISTENER +++++++++++++++++++++++++++++++++
    class HitTestAdapter extends MouseAdapter {
      int firstPressed = 99;
      int typetochange = 0;
      boolean colourPiece;
      PawnAtEnd pawn;

      @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            boolean isValid = false;
            boxPressed = beginningBox(x,y);
            boolean validBox = isValidBox(boxPressed);
            if(checkMate){
              endGame();
            }
            if(firstPressed != 99 && pieces.containsKey(firstPressed)){
              isValid = validMove(boxPressed, firstPressed, posiblesMovements);
            }

            // Choose the piece you want to move
            if(!isBoxPressed && pieces.containsKey(boxPressed) && pieces.get(boxPressed).isWhite() == isWhiteTurn){
                isBoxPressed = true;
                piecePressed = boxPressed;
                strokepattern.mousePressed(e);
                deletePosibleMovement();
                posibleMovement(posiblesMovements,pieces,pieces.get(piecePressed), piecePressed);
                noCheck(pieces.get(piecePressed),((!isWhiteTurn) ? 1 : 0)*posBlackKing + ((isWhiteTurn) ? 1 : 0)*posWhiteKing); //Valid movement if you skip the check with the move
            // Choose where you want to move the piece
              }else if (isBoxPressed && isValid){
                if(validBox){
                    if(!pieces.containsKey(boxPressed)){      // If the piece isn't in the HashMap
                      Piece piece2 = pieces.get(piecePressed);
                      pieces.remove(piecePressed);            // Remove the piece Presed
                      pieces.put(boxPressed, piece2);         // Add the piece removed to the new box
                      pieces.get(boxPressed).mousePressed(e);
                      pieces.get(boxPressed).setMove(true);   // Set de move to repaint
                      if(pieces.get(boxPressed).getType() == KING){
                        if(pieces.get(boxPressed).isWhite())            //Control de KING positions to calculate the possible check
                          posWhiteKing = boxPressed;
                        else
                          posBlackKing = boxPressed;
                      }
                      deletePosibleMovement();
                      castling();
                      check = isCheck(pieces,pieces.get(boxPressed), ((isWhiteTurn) ? 1 : 0)*posBlackKing + ((!isWhiteTurn) ? 1 : 0)*posWhiteKing, boxPressed);
                      checkMate = isCheckMate();
                      if(checkMate){
                        endGame();
                      }
                      strokepattern.setVisible(false);
                      isWhiteTurn = !isWhiteTurn;
                      isBoxPressed = false;
                    }else{                                     // If the piece is in the HashMap
                      // If the piece in the box is the same color as the turn , the pieccePressed will be changed.
                      if(pieces.get(boxPressed).isWhite() == pieces.get(piecePressed).isWhite() && pieces.get(boxPressed).isWhite() == isWhiteTurn){
                        isBoxPressed = true;
                        piecePressed = boxPressed;
                        strokepattern.mousePressed(e);

                        deletePosibleMovement();
                        posibleMovement(posiblesMovements,pieces, pieces.get(boxPressed), boxPressed);
                        noCheck(pieces.get(boxPressed),((!isWhiteTurn) ? 1 : 0)*posBlackKing + ((isWhiteTurn) ? 1 : 0)*posWhiteKing);
                        posiblesMovements2 = posiblesMovements;
                      // If the piece in the box isn't the same color as the turn, EAT the piece
                      }else{
                        Piece piece2 = pieces.get(piecePressed);
                        pieces.remove(piecePressed);
                        pieces.remove(boxPressed);
                        pieces.put(boxPressed, piece2);
                        pieces.get(boxPressed).mousePressed(e);
                        pieces.get(boxPressed).setMove(true);
                        deletePosibleMovement();
                        check = isCheck(pieces,pieces.get(boxPressed), ((isWhiteTurn) ? 1 : 0)*posBlackKing + ((!isWhiteTurn) ? 1 : 0)*posWhiteKing, boxPressed);
                        System.out.println("CHECK : " + check);
                        checkMate = isCheckMate();
                        if(checkMate){
                          endGame();
                        }
                        strokepattern.setVisible(false);
                        isBoxPressed = false;
                        isWhiteTurn = !isWhiteTurn;

                      }
                    }
                    // If the pawn arrive to the final, the pawn converts to the piece you want (QUEEN, BISHOP, HORSE, TOWER)
                    if(pieces.get(boxPressed).getType()==PAWN && (boxPressed%10 == 8 | boxPressed%10 == 1) ){
                        letPlayerChoose(boxPressed);
                    }
                }
            }
            firstPressed = boxPressed;
        }


        // ++++++++++++++++++++++++++ GAME RULES +++++++++++++++++++++++++++++++
        public boolean validMove(int box, int box1, HashMap<Integer, Dot> moves){
          //Choose pieces from the other color.
          if(moves.get(box).isVisible() )
            return true;
          else if(pieces.containsKey(box) && pieces.get(box1).isWhite() == pieces.get(box).isWhite() && pieces.get(box1).isWhite() == isWhiteTurn){
              return true;
          }
          return false;
        }
        public boolean isCheck(HashMap<Integer, Piece> pieces2,Piece pie, int posOponentKing, int box){
          /*
          * Calculate if once moved, check if there are check
          */
          posibleMovement(checkTest,pieces2, pie, box);
          for (Dot dot : checkTest.values()){
            if(dot.isVisible() && dot.getBox() == posOponentKing){
              for(Dot dot2 : checkTest.values()){
                dot2.setVisible(false);
              }
              return true;
            }
          }
          for(Dot dot : checkTest.values()){
            dot.setVisible(false);
          }
          return false;

        }
        public boolean isCheckALLPieces(HashMap< Integer, Piece> pies , Piece pie, int posOponentKing){
          /*
          * Calculate if there are a check with all oponent pieces
          */
          for(Piece p : pies.values()){
            if(p.isWhite() != pie.isWhite() && p != pie){
              if(isCheck(pies,p, posOponentKing, p.getBox()))
                return true;
            }
          }
          return false;
        }
        public boolean isCheckALLPiecesMate(HashMap< Integer, Piece> pies , Piece pie, int posOponentKing){
          /*
          * Calculate if there are a check with all oponent pieces
          */
          for(Piece p : pies.values()){
            if(p.isWhite() == pie.isWhite() && p != pie){
              if(isCheck(pies,p, posOponentKing, p.getBox()))
                return true;
            }
          }
          return false;
        }
        public void noCheck(Piece pie, int posKingTurn){
          /*
          * Once obtained the possible positions we need to discart
          * the possible position where if you moved you have been on check
          */
          int posKing = posKingTurn;
          int position = pie.getBox();
          HashMap< Integer, Piece> pies = pieces;
          HashMap< Integer, Dot> poss56 = posiblesMovements;
          Piece piece2 = null;
          boolean thereWasAPiece = false;
          pies.remove(position);
          for(Dot dot : poss56.values()){
            if(dot.isVisible()){
              if(pies.containsKey(dot.getBox())){
                piece2 = pies.get(dot.getBox());
                thereWasAPiece = true;
              }
              pies.put(dot.getBox(), pie);
              if(pie.getType() == KING){
                posKing = dot.getBox();
              }
              if(isCheckALLPieces(pies, pie, posKing)){
                dot.setVisible(false);
              }
              pies.remove(dot.getBox());
              if(thereWasAPiece){
                pies.put(dot.getBox(), piece2);
                thereWasAPiece = false;
              }

            }

          }
          pies.put(position, pie);
          posiblesMovements2 = poss56;
        }
        public void noCheckMate(Piece pie, int posKingTurn){
          /*
          * Once obtained the possible positions we need to discart
          * the possible position where if you moved you have been on check
          */
          int posKing = posKingTurn;
          int position = pie.getBox();
          HashMap< Integer, Piece> pies = pieces;
          HashMap< Integer, Dot> poss56 = posiblesMovements;
          Piece piece2 = null;
          boolean thereWasAPiece = false;
          pies.remove(position);
          for(Dot dot : poss56.values()){
            if(dot.isVisible()){
              if(pies.containsKey(dot.getBox())){
                piece2 = pies.get(dot.getBox());
                thereWasAPiece = true;
              }
              pies.put(dot.getBox(), pie);
              if(pie.getType() == KING){
                posKing = dot.getBox();
              }
              if(isCheckALLPiecesMate(pies, pie, posKing)){
                dot.setVisible(false);
              }
              pies.remove(dot.getBox());
              if(thereWasAPiece){
                pies.put(dot.getBox(), piece2);
                thereWasAPiece = false;
              }

            }

          }
          pies.put(position, pie);
          posiblesMovements2 = poss56;
        }
        public boolean isCheckMate(){
          try{
            HashMap< Integer, Piece> pies = pieces;
              if(check){
                for(Piece p : pies.values()){
                  if(p.isWhite() != isWhiteTurn){
                    deletePosibleMovement();
                    for(Dot d : checkTest.values()){
                      d.setVisible(false);
                    }
                    posibleMovement(checkTest, pies, p, p.getBox());
                    noCheck(p,((isWhiteTurn) ? 1 : 0)*posBlackKing + ((!isWhiteTurn) ? 1 : 0)*posWhiteKing);
                    for(Dot dot : checkTest.values()){
                      if(dot.isVisible()){
                        return false;
                      }
                    }


                  }
                }
                return true;
              }
              return false;
            }catch (ConcurrentModificationException ex){
              return true;
            }

        }
        public void castling(){
          if( (piecePressed == 51 && (boxPressed == 31 || boxPressed == 71)) ||
              (piecePressed == 58 && (boxPressed == 38 || boxPressed == 78)) ){   // white castling
            if(largecastling){
              Piece towr = pieces.get(piecePressed - 40);
              pieces.remove(piecePressed - 40);
              pieces.put(piecePressed - 10, towr);
              pieces.get(piecePressed -10).moveToBox(piecePressed -10);
              pieces.get(piecePressed -10).setMove(true);
            }else if(shortcastling){
              Piece towr = pieces.get(piecePressed +30);
              pieces.remove(piecePressed +30);
              pieces.put(piecePressed +10, towr);
              pieces.get(piecePressed +10).moveToBox(piecePressed +10);
              pieces.get(piecePressed +10).setMove(true);
            }
          }
        }
        public void letPlayerChoose (int boxend){
          //Give the player the choose to choose the appropiate piece
          colourPiece = pieces.get(boxend).getColor();
          pawn = new PawnAtEnd(boxend, colourPiece, this);
        }
        public void turnInToPiece(int boxend){
          // This function is called from PawnAtEnd
          pieces.remove(boxend);
          typetochange = pawn.newType();
          switch(typetochange){
            case TOWER:pieces.put(boxend, new Tower(colourPiece, true));
                  pieces.get(boxend).setMove(true);
                  pieces.get(boxend).moveToBox(boxend);
                  break;
            case HORSE:pieces.put(boxend, new Horse(colourPiece, true));
                  pieces.get(boxend).setMove(true);
                  pieces.get(boxend).moveToBox(boxend);
                  break;
            case BISHOP:pieces.put(boxend, new Bishop(colourPiece, true));
                  pieces.get(boxend).setMove(true);
                  pieces.get(boxend).moveToBox(boxend);
                  break;
            case QUEEN:pieces.put(boxend, new Queen(colourPiece));
                  pieces.get(boxend).setMove(true);
                  pieces.get(boxend).moveToBox(boxend);
                  break;
          }
        }

    }
}
