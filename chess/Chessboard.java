package chess;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.HashMap;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import chess.piece.*;


public class Chessboard {

    public static final int INITIAL_X = 220;
    public static final int INITIAL_Y = 490;
    public static final int STEP = 60;
    public static final int OUTOFBOUND_X = 700;
    public static final int OUTOFBOUND_Y = 550;

    protected int width;
    protected int height;
    protected Image image;


    public Chessboard() {
        initChessboard();
    }

    private void initChessboard() {
        loadImage("multimedia/chessboard.jpg");
        getImageDimensions();
    }
    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }

    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public Image getImage() {
        return image;
    }
    
}
