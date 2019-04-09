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
    private int boxPressed = 99;
    private int piecePressed = 99;
    private boolean isBoxPressed = false;


    public Board() {

        initBoard();
        initialPiecesPositions();

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

    public boolean isValidBox(int boxPressed){
      if(boxPressed/10 == 9 || boxPressed%10 == 9){
        return false;
      }
      return true;
    }
    public boolean contains(Piece piece, int x, int y){

        if (x > piece.getX() && x < piece.getX() + 60 && y > piece.getY() && y < piece.getY() + 60){
          return true;
        }
        return false;

    }
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
          else if((yi > INITIAL_Y && yi< OUTOFBOUND_Y)){
            y = 1;
          }
          i++;
      }
      return x*10 +y;
    }
    class HitTestAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            //.mousePressed(e);
            int x = e.getX();
            int y = e.getY();
            boxPressed = beginningBox(x,y);
            boolean validBox = isValidBox(boxPressed);
            if(!isBoxPressed && pieces.containsKey(boxPressed) && pieces.get(boxPressed).isWhite() == isWhiteTurn){
                isBoxPressed = true;
                piecePressed = boxPressed;
                strokepattern.mousePressed(e);

            }else if (isBoxPressed){
                if(validBox){
                    if(!pieces.containsKey(boxPressed)){
                      Piece piece2 = pieces.get(piecePressed);
                      pieces.remove(piecePressed);
                      pieces.put(boxPressed, piece2);
                      pieces.get(boxPressed).mousePressed(e);
                      pieces.get(boxPressed).setMove(true);
                      strokepattern.setVisible(false);
                      isWhiteTurn = !isWhiteTurn;

                      isBoxPressed = false;
                    }else{
                      if(pieces.get(boxPressed).isWhite() == pieces.get(piecePressed).isWhite() && pieces.get(boxPressed).isWhite() == isWhiteTurn){
                        isBoxPressed = true;
                        piecePressed = boxPressed;
                        strokepattern.mousePressed(e);
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
                      }

                    }

                }
            }

        }




    }
}
