package chess.piece;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import chess.*;
public class Pawn extends Piece {

    private int site; //true right -> false left

    public Pawn(boolean color, int site) {
        super(color, PAWN);
        this.site = site;
        initPawn();

    }

    private void initPawn() {
        if(isWhite()){
          loadImage("multimedia/pieces/white_pawn.png");
        }else{
          loadImage("multimedia/pieces/black_pawn.png");
        }
        getImageDimensions();

        this.x = INITIAL_X + ((site - 1) * STEP );    // Set initial Pawn cordinates on board
        this.y = INITIAL_Y - (((!color) ? 1 : 0) * STEP * 6) -(((color) ? 1 : 0) * STEP);
    }

    public void move(int xy) {
      //  toXY();
    }
    /*
    * In this method toXY(), with an algorithm traduces the position
    * of the piece to cordinates in the board
    */
    /*public void toXY(int poss){
      this.x = poss / 10;
      this.y = poss % 10;
    }*/

    public void mousePressed(KeyEvent e) {

        /*int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            fire();
        }

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }*/
    }


    public void mouseReleased(KeyEvent e) {

        /*int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }*/
    }
}