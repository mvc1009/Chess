package chess;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Piece {


    public static final int INITIAL_X = 220;
    public static final int INITIAL_Y = 70;
    public static final int STEP = 60;

    // PIECES
    public static final int TOWER = 1;
    public static final int HORSE = 2;
    public static final int BISHOP = 3;
    public static final int QUEEN = 4;
    public static final int KING = 5;
    public static final int PAWN = 6;


    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean color;  // TRUE -> white FALSE -> black
    protected Image image;
    protected int type;
  //  protected boolean toMove;
    //protected int futureMove;

    public Piece(boolean color, int type) {

        this.x = 0;
        this.y = 0;
        this.color = color;
        this.type = type;
        this.toMove = false;
        //this.futureMove = 99;

    }
    public int getType(){
      return type;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWhite() {
        return color;
    }
    public boolean isMove(){
       return toMove;
    }
    public void setMove(boolean move){
      this.toMove = move;
    }
    public void move(int box){
      this.x = box/10 + INITIAL_X;
      this.y = box%10 + INITIAL_Y;
      setMove(false);
    }
    /*public int getFutureMove(){
      return futureMove;
    }
    public void setFutureMove(int futureMove){
      this.futureMove = futureMove;
    }
*/
}
