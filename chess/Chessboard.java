package chess;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.HashMap;


public class Chessboard {

    protected int width;
    protected int height;
    protected Image image;
    HashMap<Integer, Piece> chessboard;

    public Chessboard() {
        initChessboard();
        initialPiecesPositions();
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
    private void initialPiecesPositions(){
        chessboard = new HashMap<Integer,Piece>();
        //White initials:
        chessboard.put(1, new Tower(true, false));
        chessboard.put(2, new Horse(true, false));
        chessboard.put(3, new Bishop(true,false));
        chessboard.put(4, new Queen(true));
        chessboard.put(5, new King(true));
        chessboard.put(6, new Bishop(true, true));
        chessboard.put(7, new Horse(true, true));
        chessboard.put(8, new Tower(true, true));

        chessboard.put(11, new Pawn(true, 1));
        chessboard.put(21, new Pawn(true, 2));
        chessboard.put(31, new Pawn(true, 3));
        chessboard.put(41, new Pawn(true, 4));
        chessboard.put(51, new Pawn(true, 5));
        chessboard.put(61, new Pawn(true, 6));
        chessboard.put(71, new Pawn(true, 7));
        chessboard.put(81, new Pawn(true, 8));

        //Black initials:
        chessboard.put(18, new Tower(false, false));
        chessboard.put(28, new Horse(false, false));
        chessboard.put(38, new Bishop(false,false));
        chessboard.put(48, new Queen(false));
        chessboard.put(58, new King(false));
        chessboard.put(68, new Bishop(false, true));
        chessboard.put(78, new Horse(false, true));
        chessboard.put(88, new Tower(false, true));

        chessboard.put(17, new Pawn(false, 1));
        chessboard.put(27, new Pawn(false, 2));
        chessboard.put(37, new Pawn(false, 3));
        chessboard.put(47, new Pawn(false, 4));
        chessboard.put(57, new Pawn(false, 5));
        chessboard.put(67, new Pawn(false, 6));
        chessboard.put(77, new Pawn(false, 7));
        chessboard.put(87, new Pawn(false, 8));
    }
    public HashMap<Integer,Piece> getPieces(){
       return chessboard;
    }
    public void move(){

    }
}
