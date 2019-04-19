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
        for(Dot dot : posiblesMovements.values()){
            if(dot.isVisible()){
              g2d.drawImage(dot.getImage(), dot.getX(), dot.getY(), this);
            }
        }
        //TURN LABEL
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
// Printamos las diferentes posiciones de las piezas
    private void pawnPosiblePositions(int box, boolean color){
      if(color){            // TRUE -> white FALSE -> black
        posiblesMovements.get(box+1).setVisible(true);
        posiblesMovements.get(box+2).setVisible(true);
      }
      else{
        posiblesMovements.get(box-1).setVisible(true);
        posiblesMovements.get(box-2).setVisible(true);
      }
    }

    private void towerPosiblePositions(int box, boolean color){
      int initialpos = box/10;
      for (int k = 1; k<=8; k++){
        if(initialpos*10+k != box){
          posiblesMovements.get(initialpos*10+k).setVisible(true);
        }
      }
    }

    private void bishopPosiblePositions(int box, boolean color){
      //Primera fila, donde va el primer punto
      int dot_x = box/10;
      int dot_y = box%10;
      int x = dot_x;
      int y = dot_y;
      int xneg = box/10;
      int yneg = box%10;
      int pos = 1;
      while(x <= 8 || y <= 8){
        // positivos
        if(x < 8 && y < 8){
          posiblesMovements.get(box + pos*10 + pos*1).setVisible(true);
          posiblesMovements.get(box - pos*10 + pos*1).setVisible(true);
        }
        //negativos
        if(dot_y != 1 && xneg > 1 && yneg > 1){
          posiblesMovements.get(box - pos*10 - pos*1).setVisible(true);
          posiblesMovements.get(box + pos*10 - pos*1).setVisible(true);
          xneg--;
          yneg--;
        }
        x++;
        y++;
        pos++;
      }
    }

    private void horsePosiblePositions(int box, boolean color){
        if(box%10 <= 6){   //PUNTOS SUPERIORES
          if(box/10 < 8)
            posiblesMovements.get(box+12).setVisible(true);
          if(box/10 > 1)
            posiblesMovements.get(box-8).setVisible(true);
          if(box/10 <= 6)
            posiblesMovements.get(box+21).setVisible(true);
          if(box/10 >= 3)
            posiblesMovements.get(box-19).setVisible(true);
        }
        if(box%10 >= 3){    // PUNTOS INFERIORES
          if(box/10 < 8)
            posiblesMovements.get(box+8).setVisible(true);
          if(box/10 > 1)
            posiblesMovements.get(box-12).setVisible(true);
          if(box/10 <= 6)
            posiblesMovements.get(box+19).setVisible(true);
          if(box/10 >= 3)
            posiblesMovements.get(box-21).setVisible(true);
        }
    }

    private void kingPosiblePositions(int box, boolean color){
      posiblesMovements.get(box + 10).setVisible(true);
      posiblesMovements.get(box - 10).setVisible(true);
        if(box%10 < 8){  //Puntos superiores
          posiblesMovements.get(box + 1).setVisible(true);
          posiblesMovements.get(box + 11).setVisible(true);
          posiblesMovements.get(box - 9).setVisible(true);
        }
        if(box%10 > 1){  //Puntos inferiores
          posiblesMovements.get(box + 9).setVisible(true);
          posiblesMovements.get(box - 1).setVisible(true);
          posiblesMovements.get(box - 11).setVisible(true);
        }
    }

    private void queenPosiblePositions(int box, boolean color){
      //MOVIMIENTO VERTICAL
      int initialpos = box/10;
      for (int k = 1; k<=8; k++){
        if(initialpos*10+k != box){
          posiblesMovements.get(initialpos*10+k).setVisible(true);
        }
      }

      // MOVIMIENTO EN DIAGONAL
      //Primera fila, donde va el primer punto
      int dot_x = box/10;
      int dot_y = box%10;
      int x = dot_x;
      int y = dot_y;
      int xneg = box/10;
      int yneg = box%10;
      int pos = 1;
      while(x <= 8 || y <= 8){
        // positivos
        if(x < 8 && y < 8){
          posiblesMovements.get(box + pos*10 + pos*1).setVisible(true);
          posiblesMovements.get(box - pos*10 + pos*1).setVisible(true);
        }
        //izquierda negativa
        if(dot_y != 1 && xneg > 1 && yneg > 1){
          posiblesMovements.get(box - pos*10 - pos*1).setVisible(true);
          posiblesMovements.get(box + pos*10 - pos*1).setVisible(true);
          xneg--;
          yneg--;
        }
        x++;
        y++;
        pos++;
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

    //++++++++++++++++++++++ VISUALICACIÓN PUNTOS ++++++++++++++
    public void posibleMovement(Piece piece2Move){ //rules
      //Esta usando el beginningBox de la clase Piece.
      int box = piece2Move.beginningBox(piece2Move.getX(), piece2Move.getY()); //Antes sumabas 10
      int i = 0;
      boolean pieceInMiddle = false;
      while(i < 8){
        switch(piece2Move.getType()){
          case TOWER: if(!pieces.containsKey((((box/10)*10+(box%10)+i)%8)+1) && !pieceInMiddle){
              towerPosiblePositions(box, piece2Move.getColor());
              break;
            }
            //pieceInMiddle = true;
            break;
          case HORSE:if(!pieces.containsKey((((box/10)*10+(box%10)+i)%8)+1) && !pieceInMiddle){
              horsePosiblePositions(box, piece2Move.getColor());
              break;
            }
            break;
          case BISHOP:if(!pieces.containsKey((((box/10)*10+(box%10)+i)%8)+1) && !pieceInMiddle){
              bishopPosiblePositions(box, piece2Move.getColor());
              break;
            }
            //pieceInMiddle = true;
            break;
          case KING:if(!pieces.containsKey((((box/10)*10+(box%10)+i)%8)+1) && !pieceInMiddle){
              kingPosiblePositions(box, piece2Move.getColor());
              break;
            }
            break;
          case QUEEN:if(!pieces.containsKey(((box+(box%10)+i)%8)+1) && !pieceInMiddle){
              queenPosiblePositions(box, piece2Move.getColor());
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
              isValid = validMove(pieces.get(firstPressed).getType(),
                                  pieces.get(firstPressed).getColor(),
                                  boxPressed, firstPressed);
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
                        strokepattern.setVisible(false);
                        isBoxPressed = false;
                        deletePosibleMovement();
                      }
                    }
                }
            }
            firstPressed = boxPressed;
        }
// ************************** NORMAS DE JUEGO **********************************
        public boolean validMove(int type, boolean color, int box2, int box1){
          //Escojer otras fichas de otro color
          if(pieces.containsKey(box2) && pieces.get(box1).isWhite() == pieces.get(box1).isWhite() && pieces.get(box1).isWhite() == isWhiteTurn){
              return true;
          }
          else{
          switch(type){
            case TOWER:
              int initialpos = box1/10;
              for (int k = 1; k<=8; k++){
                if(initialpos*10+k == box2){
                  return true;
                }
              }
              break;
            case HORSE:
              if(box2 == box1+12 || box2 == box1-8 || box2 == box1+21 ||
                    box2 == box1-19 || box2 == box1+8 || box2 == box1-12 ||
                    box2 == box1+19 || box2 == box1-21){
                      return true;
              }
              break;
            case BISHOP:
              break;
            case KING:
              if(box2 == box1+1 || box2 == box1+10 || box2 == box1+11 ||
                box2 == box1-9 || box2 == box1-10 || box2 == box1-11 ||
                box2 == box1-1 || box2 == box1+9){
                  return true;
              }
              break;
            case QUEEN:
              break;
            case PAWN:
              if(color){// TRUE -> white FALSE -> black
                if(box2== box1+1 || box2== box1+2){
                  return true;
                }else{return false;}
              }
              else{
                if(box2== box1-1 || box2== box1-2){
                  return true;
                }else{return false;}
              }
              //break;
          }
          }
          return false;
        }
    }
}
