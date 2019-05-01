package chess.piece;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import chess.*;
import java.util.HashMap;

public class Horse extends Piece {

    private boolean site; //true right -> false left

    public Horse(boolean color, boolean site, HashMap<Integer, Dot> possibleMovement) {
        super(color, HORSE, possibleMovement);
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
        this.y = INITIAL_Y - (((!color) ? 1 : 0) * STEP * 7);
    }
}
