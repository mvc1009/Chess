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
import java.util.Iterator;

public class Board extends JPanel implements ActionListener {


    public static final int INITIAL_X = 220;
    public static final int INITIAL_Y = 70;
    public static final int STEP = 60;
    //private final int ICRAFT_X = 40;
    //private final int ICRAFT_Y = 60;
    private final int DELAY = 10;
    private Timer timer;
    private Background background;
    private Chessboard chessboard;
    int boxPressed = 99;
    boolean isBoxPressed = false;

    public Board() {

        initBoard();
    }

    private void initBoard() {
        addMouseListener(new HitTestAdapter());
        //addKeyListener(new TAdapter());
        //setBackground(Color.BLACK);
        setFocusable(true);

        //spaceShip = new SpaceShip(ICRAFT_X, ICRAFT_Y);
        background = new Background();
        chessboard = new Chessboard();

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


        for( Piece piece : chessboard.getPieces().values() ){
            g2d.drawImage(piece.getImage(), piece.getX(), piece.getY(), this);
        }
        //g2d.drawImage(spaceShip.getImage(), spaceShip.getX(),spaceShip.getY(), this);

        /*List<Missile> missiles = spaceShip.getMissiles();

        for (Missile missile : missiles) {

            g2d.drawImage(missile.getImage(), missile.getX(),
                    missile.getY(), this);
        }*/
        g.setColor(Color.WHITE);            //cutre
        g.drawString("Turn: WHITE", 5, 15);

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 50);    //semao
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.RED);
        g.setFont(small);
        g.drawString(msg, 70,60);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /*updateMissiles();
        updateSpaceShip();*/
        updatePieces();
        repaint();
    }

    private void updatePieces() {
        for( Piece piece : chessboard.getPieces().values() ){
            if(piece.isMove()){
              piece.move(boxPressed);
            }
        }
        /*List<Missile> missiles = spaceShip.getMissiles();

        for (int i = 0; i < missiles.size(); i++) {

            Missile missile = missiles.get(i);

            if (missile.isVisible()) {

                missile.move();
            } else {

                missiles.remove(i);
            }
        }*/
    }

    /*private void updateSpaceShip() {

        //spaceShip.move();
    }*/

    /*private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            //spaceShip.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //spaceShip.keyPressed(e);
        }
    }*/
    class HitTestAdapter extends MouseAdapter {

        //private RectRunnable rectAnimator;
        //private Thread ellipseAnimator;

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();
            boxPressed = beginningBox(x,y);
            if(!isBoxPressed && chessboard.getPieces().containsKey(boxPressed)){
                isBoxPressed = true;
                System.out.println("Piece pressed");
            }else{
                if(box != 99){
                    chessboard.getPieces().get(box).setMove(true);
                    System.out.println("Piece leave");
                }
            }

        }
        public boolean contains(Piece piece, int x, int y){

            if (x > piece.getX() && x < piece.getX() + 60 && y > piece.getY() && y < piece.getY() + 60){
              return true;
            }
            return false;

        }
        public int beginningBox(int xi, int yi){
          int i = 0;
          int box = 0;
          boolean eox = false;
          boolean eoy = false;
          while(i < 8){
              if((xi > INITIAL_X + STEP*i) && !eox){
                box = i * 10;
                eox = true;
              }
              if((yi> INITIAL_Y + STEP * i) && !eoy){
                box = box +i;
                eoy = true;
              }
              i++;
          }
          return box;
        }


    }
}
