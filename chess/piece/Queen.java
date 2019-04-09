package chess.piece;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import chess.*;

public class Queen extends Piece {


    public Queen(boolean color) {
        super(color, QUEEN);
        initQueen();
    }

    private void initQueen() {
        if(isWhite()){
          loadImage("multimedia/pieces/white_queen.png");
        }else{
          loadImage("multimedia/pieces/black_queen.png");
        }
        getImageDimensions();
        this.x = INITIAL_X + (STEP * 3) ;    // Set initial Queen cordinates on board
        this.y = INITIAL_Y - (((!color) ? 1 : 0) * STEP * 7);
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
