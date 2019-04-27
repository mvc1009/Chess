package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.HashMap;

import chess.piece.*;

public class Board extends JPanel implements ActionListener {

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
    private int boxPressed = 99;
    private int piecePressed = 99;
    private boolean isBoxPressed = false;

    private boolean largecastling = false;
    private boolean shortcastling = false;

    private boolean check = false;
    private boolean checkMate = false;


    public Board() {

        initBoard();
        initialPiecesPositions();
        initialPosiblePositions();

    }

    private void initBoard() {
        addMouseListener(new HitTestAdapter());
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

//**************************    MESA   ******************************
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
        if(!checkMate){
          for(Dot dot : posiblesMovements.values()){
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateStroke();
        updatePieces();
        repaint();
    }

//*************************  PIEZAS *******************************************

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

//+++++++++++++++++ POSIBLES POSICIONES ++++++++++++++++++
//Ponemos un punto en todas las celdas
    private void initialPosiblePositions(){
      posiblesMovements = new HashMap<Integer, Dot>();
      for(int i = 10; i <= 88; i++){
        if(i%10 != 9){
          posiblesMovements.put(i, new Dot(i));
        }
      }
    }

    //Funcion para determinar si se imprime un punto encima de una ficha o no
    //dependiendo de si esta es del mismo color de la ficha clickeada
    //usada en horse y king PosiblePositions.
    private boolean validDot(int boxd, boolean colord){
      if(pieces.containsKey(boxd)){
        if(pieces.get(boxd).getColor() == !colord)
          return true;
        else
          return false;
      }
      return true;
    }

// Printamos las diferentes posiciones de las piezas
    private void pawnPosiblePositions(int box, boolean color){
      // TRUE -> white FALSE -> black
      // Peones blancos: Imprime punto si no hay ficha delante o esta es negra
      if( color ) {
        if(!pieces.containsKey(box+1))
          posiblesMovements.get(box+1).setVisible(true);
        if(box%10 == 2 && !pieces.containsKey(box+1) && !pieces.containsKey(box+2) )
          posiblesMovements.get(box+2).setVisible(true);
        //Comer diagonal
        if(pieces.containsKey(box+11)){posiblesMovements.get(box+11).setVisible(true);}
        if(pieces.containsKey(box-9)){posiblesMovements.get(box-9).setVisible(true);}
      }
      //Peones negros: Imprime punto si no hay ficha delante o esta es blanca
      else if(color == false && !pieces.containsKey(box-1)){
        if(!pieces.containsKey(box-1) )
          posiblesMovements.get(box-1).setVisible(true);
        if(box%10 == 7 && !pieces.containsKey(box-1) && !pieces.containsKey(box-2) )
          posiblesMovements.get(box-2).setVisible(true);
        //Comer
        if(pieces.containsKey(box-11)){posiblesMovements.get(box-11).setVisible(true);}
        if(pieces.containsKey(box+9)){posiblesMovements.get(box+9).setVisible(true);}
      }
    }

    private void towerPosiblePositions(int box){
       int x = box/10;
       int y = box%10;
       boolean pieceInMiddleUP = false;
       boolean pieceInMiddleDOWN = false;
       boolean pieceInMiddleLEFT = false;
       boolean pieceInMiddleRIGHT = false;

       for(int i = 1; i <= 8 ; i++){
         //UP
         if(y+i < 9 && !pieceInMiddleUP){
           if(validDot(box + i, pieces.get(box).getColor())){
             posiblesMovements.get(box + i).setVisible(true);
             if(pieces.containsKey(box + i)){
               pieceInMiddleUP = true;
             }
           }else{
             pieceInMiddleUP = true;
           }
         }
         //LEFT
         if(x-i > 0 && !pieceInMiddleLEFT){
           if(validDot(box - i*10, pieces.get(box).getColor())){
             posiblesMovements.get(box - i*10).setVisible(true);
             if(pieces.containsKey(box - i*10)){
               pieceInMiddleLEFT = true;
             }
           }else{
             pieceInMiddleLEFT = true;
           }
         }
         //RIGHT
         if(x+i < 9 && !pieceInMiddleRIGHT){
           if(validDot(box + i*10, pieces.get(box).getColor())){
             posiblesMovements.get(box + i*10).setVisible(true);
             if(pieces.containsKey(box + i*10)){
               pieceInMiddleRIGHT = true;
             }
           }else{
             pieceInMiddleRIGHT = true;
           }

         }
         //DOWN
         if(y-i > 0 && !pieceInMiddleDOWN ){
           if(validDot(box - i, pieces.get(box).getColor())){
             posiblesMovements.get(box - i).setVisible(true);
             if(pieces.containsKey(box - i)){
               pieceInMiddleDOWN = true;
             }
           }else{
             pieceInMiddleDOWN = true;
           }
         }
       }
    }

    private void bishopPosiblePositions(int box){
      //sin fichas entre medio
      int x = box/10;
      int y = box%10;
      boolean pieceInMiddleUPRIGHT = false;
      boolean pieceInMiddleDOWNRIGHT = false;
      boolean pieceInMiddleUPLEFT = false;
      boolean pieceInMiddleDOWNLEFT = false;

     for(int i = 1; i < 9 ; i++){
       //UP & RIGHT
       if(x+i < 9 && y+i < 9 && !pieceInMiddleUPRIGHT){
         if(validDot(box + i*10 + i, pieces.get(box).getColor())){
           posiblesMovements.get(box + i*10 + i).setVisible(true);
           if(pieces.containsKey(box + i*10 + i)){
             pieceInMiddleUPRIGHT = true;
           }
         }else{
           pieceInMiddleUPRIGHT = true;
         }
       }
       //UP & LEFT
       if(x-i > 0 && y+i < 9 && !pieceInMiddleUPLEFT){
         if(validDot(box - i*10 + i, pieces.get(box).getColor())){
           posiblesMovements.get(box - i*10 + i).setVisible(true);
           if(pieces.containsKey(box - i*10 + i)){
             pieceInMiddleUPLEFT = true;
           }
         }else{
           pieceInMiddleUPLEFT = true;
         }
       }
       //DOWN & RIGHT
       if(x+i < 9 && y-i > 0 && !pieceInMiddleDOWNRIGHT){
         if(validDot(box + i*10 - i, pieces.get(box).getColor())){
           posiblesMovements.get(box + i*10 - i).setVisible(true);
           if(pieces.containsKey(box + i*10 - i)){
             pieceInMiddleDOWNRIGHT = true;
           }
         }else{
           pieceInMiddleDOWNRIGHT = true;
         }

       }
       //DOWN & LEFT
       if(x-i > 0 && y-i > 0 && !pieceInMiddleDOWNLEFT ){
         if(validDot(box - i*10 - i, pieces.get(box).getColor())){
           posiblesMovements.get(box - i*10 - i).setVisible(true);
           if(pieces.containsKey(box - i*10 - i)){
             pieceInMiddleDOWNLEFT = true;
           }
         }else{
           pieceInMiddleDOWNLEFT = true;
         }
       }
     }
    }

    private void horsePosiblePositions(int box){
    //Hay que tener en cuenta de no dibujar los puntos fuera de los limites
    //de la mesa, por eso hay tantas condiciones (zonas limite tablero)

      if(box%10 <= 6){   //PUNTOS SUPERIORES
        if(box/10 < 8 && validDot(box+12, pieces.get(box).getColor()) )
          posiblesMovements.get(box+12).setVisible(true);
        if(box/10 > 1 && validDot(box-8, pieces.get(box).getColor()) )
          posiblesMovements.get(box-8).setVisible(true);
        if(box/10 <= 6 && validDot(box+21, pieces.get(box).getColor()) )
          posiblesMovements.get(box+21).setVisible(true);
        if(box/10 >= 3 && validDot(box-19, pieces.get(box).getColor()) )
          posiblesMovements.get(box-19).setVisible(true);
      }

      if(box%10 >= 3){    // PUNTOS INFERIORES
        if(box/10 < 8 && validDot(box+8, pieces.get(box).getColor()) )
          posiblesMovements.get(box+8).setVisible(true);
        if(box/10 > 1 && validDot(box-12, pieces.get(box).getColor()) )
          posiblesMovements.get(box-12).setVisible(true);
        if(box/10 <= 6 && validDot(box+19, pieces.get(box).getColor()) )
          posiblesMovements.get(box+19).setVisible(true);
        if(box/10 >= 3 && validDot(box-21, pieces.get(box).getColor()) )
          posiblesMovements.get(box-21).setVisible(true);
      }
    }

    private void kingPosiblePositions(int box){
      if( validDot(box+10, pieces.get(box).getColor()) ){
        posiblesMovements.get(box + 10).setVisible(true);
        shortcastling = false;
        if(!pieces.get(box).isMoved() && pieces.containsKey(box + 30) &&
            pieces.get(box+30).getType() == TOWER && !pieces.get(box+30).isMoved()){
          posiblesMovements.get(box + 20).setVisible(true);
          shortcastling = true;
        }
      }
      if( validDot(box-10, pieces.get(box).getColor()) ){
        posiblesMovements.get(box - 10).setVisible(true);
        largecastling = false;
        if(!pieces.get(box).isMoved() && pieces.containsKey(box - 40) &&
            pieces.get(box-40).getType() == TOWER && !pieces.get(box-40).isMoved()){
          posiblesMovements.get(box - 20).setVisible(true);
          largecastling = true;
        }
      }
      if(box%10 < 8){  //Puntos superiores
        if( validDot(box+1, pieces.get(box).getColor()) )
          posiblesMovements.get(box + 1).setVisible(true);
        if( validDot(box+11, pieces.get(box).getColor()) )
          posiblesMovements.get(box + 11).setVisible(true);
        if( validDot(box-9, pieces.get(box).getColor()) )
          posiblesMovements.get(box - 9).setVisible(true);
      }
      if(box%10 > 1){  //Puntos inferiores
        if( validDot(box+9, pieces.get(box).getColor()) )
          posiblesMovements.get(box + 9).setVisible(true);
        if( validDot(box-1, pieces.get(box).getColor()) )
          posiblesMovements.get(box - 1).setVisible(true);
        if( validDot(box-11, pieces.get(box).getColor()) )
          posiblesMovements.get(box - 11).setVisible(true);
      }

      if(check && posiblesMovements.size() == 0){
        checkMate = true;
      }
    }

//++++++++++++++++++ VISUALICACIÓN RECUADRO +++++++++++++++++
    public void updateStroke(){
      if(strokepattern.isVisible()){
        strokepattern.move();
      }
    }
    public void updatePieces(){
      for( Piece piece : pieces.values() ){
          if(piece.isMove()){
            piece.move();
          }
      }
    }

    public boolean contains(Piece piece, int x, int y){
        if (x > piece.getX() && x < piece.getX() + 60 && y > piece.getY() && y < piece.getY() + 60){
          return true;
        }
        return false;
    }

    //++++++++++++++++++++++ VISUALICACIÓN PUNTOS ++++++++++++++++++++++++++++

    public void posibleMovement(Piece piece2Move){ //rules
      //Esta usando el beginningBox de la clase Piece.
      int box = piece2Move.beginningBox(piece2Move.getX(), piece2Move.getY()); //Antes sumabas 10
      int i = 0;
      boolean pieceInMiddle = false;
      while(i < 8){
        switch(piece2Move.getType()){
          case TOWER: if(!pieces.containsKey((((box/10)*10+(box%10)+i)%8)+1) && !pieceInMiddle){
              towerPosiblePositions(box);
              break;
            }
            //pieceInMiddle = true;
            break;
          case HORSE:if(!pieces.containsKey((((box/10)*10+(box%10)+i)%8)+1) && !pieceInMiddle){
              horsePosiblePositions(box);
              break;
            }
            break;
          case BISHOP:if(!pieces.containsKey((((box/10)*10+(box%10)+i)%8)+1) && !pieceInMiddle){
              bishopPosiblePositions(box);
              break;
            }
            //pieceInMiddle = true;
            break;
          case KING:if(!pieces.containsKey((((box/10)*10+(box%10)+i)%8)+1) && !pieceInMiddle){
              kingPosiblePositions(box);
              break;
            }
            break;
          case QUEEN:if(!pieces.containsKey(((box+(box%10)+i)%8)+1) && !pieceInMiddle){
              towerPosiblePositions(box);
              bishopPosiblePositions(box);
              break;
            }
            break;
          case PAWN: if(!pieces.containsKey(((box+(box%10)+i)%8)+1) && !pieceInMiddle){
              pawnPosiblePositions(box, piece2Move.getColor());
              break;
            }
            //pieceInMiddle = true;
            break;
        }
        i++;
      }
    }

//************************* OTRAS FUNCIONES ***********************************
    public void deletePosibleMovement(){
      for (Dot dot : posiblesMovements.values()){
        dot.setVisible(false);
      }
    }

// Funciones usadas por HitTestAdapter
    public int beginningBox(int xi, int yi){
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
      //System.out.println("r: "+result);
      return result;
    }

    public boolean isValidBox(int boxPressed){
      if(boxPressed/10 == 9 || boxPressed%10 == 9){
        return false;
      }
      return true;
    }

//************************ LECTOR MOUSE ***************************************

    class HitTestAdapter extends MouseAdapter {
      int firstPressed = 99;

      @Override
        public void mousePressed(MouseEvent e) {
            //.mousePressed(e);
            int x = e.getX();
            int y = e.getY();
            boolean isValid = false;
            boxPressed = beginningBox(x,y);
            //System.out.println("primero " + boxPressed);
            boolean validBox = isValidBox(boxPressed);

            if(firstPressed != 99 && pieces.containsKey(firstPressed)){
              isValid = validMove(boxPressed, firstPressed, posiblesMovements);
            }

// Escojemos la Ficha a mover:
            if(!isBoxPressed && pieces.containsKey(boxPressed) && pieces.get(boxPressed).isWhite() == isWhiteTurn){
                isBoxPressed = true;
                piecePressed = boxPressed;
                strokepattern.mousePressed(e);
                posibleMovement(pieces.get(boxPressed));
                //System.out.println("segundo " + boxPressed);

// Escojemos donde mover dicha Ficha
              }else if (isBoxPressed && isValid){
              //System.out.println("Despues del if: " + boxPressed);
                if(validBox){
                    if(!pieces.containsKey(boxPressed)){      // No esta la pieza en el HashMap
                      Piece piece2 = pieces.get(piecePressed);
                      pieces.remove(piecePressed);            // Eliminamos la ficha que reponemos
                      pieces.put(boxPressed, piece2);         // Añadimos la nueva pieza
                      pieces.get(boxPressed).mousePressed(e); // Nueva posición de la pieza
                      pieces.get(boxPressed).setMove(true);   // Finalización del movimiento
                      isCheck(posiblesMovements, pieces);
                      castling();
                      strokepattern.setVisible(false);
                      isWhiteTurn = !isWhiteTurn;
                      isBoxPressed = false;
                      deletePosibleMovement();
                    }else{    // La pieza esta en el HashMap
                      // Es una caja donde hay una pieza blanca y es turno del Blanco
                      if(pieces.get(boxPressed).isWhite() == pieces.get(piecePressed).isWhite() && pieces.get(boxPressed).isWhite() == isWhiteTurn){
                        isBoxPressed = true;
                        piecePressed = boxPressed;
                        strokepattern.mousePressed(e);
                        deletePosibleMovement();
                        posibleMovement(pieces.get(boxPressed));

                      // Es una caja donde hay una pieza negra y es turno de las Negras
                      }else{
                        Piece piece2 = pieces.get(piecePressed);
                        pieces.remove(piecePressed);
                        pieces.remove(boxPressed);
                        pieces.put(boxPressed, piece2);
                        pieces.get(boxPressed).mousePressed(e);
                        pieces.get(boxPressed).setMove(true);
                        isWhiteTurn = !isWhiteTurn;
                        isCheck(posiblesMovements, pieces);
                        strokepattern.setVisible(false);
                        isBoxPressed = false;
                        deletePosibleMovement();
                      }
                    }
                    //System.out.println(firstPressed);
                    System.out.println(boxPressed);
                    //Peon llega al final:
                    if(pieces.get(boxPressed).getType()==PAWN && (boxPressed%10 == 8 | boxPressed%10 == 1) ){
                      System.out.println("hola amiguis");
                      if(pieces.get(boxPressed).getColor() ) //blanca
                        System.out.println("hola amiguis blanco");
                        turnInToPiece(boxPressed);
                      if(pieces.get(boxPressed).getColor()==false ) //negra
                        turnInToPiece(boxPressed);
                    }
                }
            }
            firstPressed = boxPressed;
        }
// ************************** NORMAS DE JUEGO **********************************
        public boolean validMove(int box, int box1, HashMap<Integer, Dot> moves){
          //Escojer otras fichas de otro color
          if(moves.get(box).isVisible() )
            return true;
          else if(pieces.containsKey(box) && pieces.get(box1).isWhite() == pieces.get(box).isWhite() && pieces.get(box1).isWhite() == isWhiteTurn){
              return true;
          }
          return false;
        }

        public void isCheck(HashMap<Integer, Dot> posib, HashMap<Integer, Piece> pieces){
          for (Dot dot: posib.values()){
            if(pieces.containsKey(dot.getBox()) && pieces.get(dot.getBox()).getType() == KING){
              check = true;
            }
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


        public void turnInToPiece(int box8){
          //Dar a elejir al jugador en que pieza quiere convertir su peon.
          pieces.remove(box8);
          pieces.put(box8, new Queen(true));
          pieces.get(box8).setMove(true);
        }

    }
}
