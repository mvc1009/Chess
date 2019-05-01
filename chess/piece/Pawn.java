package chess.piece;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import chess.*;
import java.util.HashMap;

public class Pawn extends Piece {

    private int site; //true right -> false left

    public Pawn(boolean color, int site,HashMap<Integer, Dot> possibleMovement) {
        super(color, PAWN, possibleMovement);
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
}
