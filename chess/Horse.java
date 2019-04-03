package chess;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {

    private boolean site; //true right -> false left

    public Horse(boolean color, boolean site) {
        super(color, HORSE);
        this.site = site;
        initHorse();
    }

    private void initHorse() {
        if(isWhite()){
          loadImage("multimedia/pieces/white_horse.png");
        }else{
          loadImage("multimedia/pieces/black_horse.png");
        }
        getImageDimensions();
        this.x = INITIAL_X + (((site) ? 1 : 0) * STEP * 6 ) + (((!site) ? 1 : 0) * STEP);    // Set initial horse cordinates on board
        this.y = INITIAL_Y + (((color) ? 1 : 0) * STEP * 7);
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
