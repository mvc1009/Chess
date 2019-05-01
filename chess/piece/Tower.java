package chess.piece;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import chess.*;

public class Tower extends Piece {

    private boolean site; //true right -> false left

    public Tower(boolean color, boolean site,HashMap<Integer, Dot> possibleMovement) {
        super(color, TOWER, possibleMovement);
        this.site = site;
        initTower();

    }

    private void initTower() {
        if(isWhite()){
          loadImage("multimedia/pieces/white_tower.png");
        }else{
          loadImage("multimedia/pieces/black_tower.png");
        }
        getImageDimensions();
        this.x = INITIAL_X + (((site) ? 1 : 0) * STEP * 7);    // Set initial Tower cordinates on board
        this.y = INITIAL_Y - (((!color) ? 1 : 0) * STEP * 7);
    }
}
