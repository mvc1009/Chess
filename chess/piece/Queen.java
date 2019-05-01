package chess.piece;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import chess.*;
import java.util.HashMap;

public class Queen extends Piece {


    public Queen(boolean color,HashMap<Integer, Dot> possibleMovement) {
        super(color, QUEEN, possibleMovement);
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


}
