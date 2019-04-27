package chess.piece;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import chess.*;
public class Bishop extends Piece {

    private boolean site; //true right -> false left

    public Bishop(boolean color, boolean site) {
        super(color, BISHOP);
        this.site = site;
        initBishop();

    }

    private void initBishop() {
        if(isWhite()){
          loadImage("multimedia/pieces/white_bishop.png");
        }else{
          loadImage("multimedia/pieces/black_bishop.png");
        }
        getImageDimensions();
        this.x = INITIAL_X + (((site) ? 1 : 0) * STEP * 5) + (((!site) ? 1 : 0) * STEP * 2);    // Set initial bishop cordinates on board
        this.y = INITIAL_Y - (((!color) ? 1 : 0) * STEP * 7);
    }
}
