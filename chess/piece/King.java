package chess.piece;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import chess.*;
import java.util.HashMap;

public class King extends Piece {

    public King(boolean color,HashMap<Integer, Dot> possibleMovement) {
        super(color, KING, possibleMovement);
        initKing();
    }

    private void initKing() {
        if(isWhite()){
          loadImage("multimedia/pieces/white_king.png");
        }else{
          loadImage("multimedia/pieces/black_king.png");
        }
        getImageDimensions();
        this.x = INITIAL_X + STEP * 4 ;    // Set initial King cordinates on board
        this.y = INITIAL_Y - (((!color) ? 1 : 0) * STEP * 7);
    }
}
