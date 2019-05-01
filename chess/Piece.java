package chess;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Piece {


    public static final int INITIAL_X = 220;
    public static final int INITIAL_Y = 490;
    public static final int STEP = 60;
    public static final int OUTOFBOUND_X = 700;
    public static final int OUTOFBOUND_Y = 550;

    // PIECES
    public static final int TOWER = 1;
    public static final int HORSE = 2;
    public static final int BISHOP = 3;
    public static final int QUEEN = 4;
    public static final int KING = 5;
    public static final int PAWN = 6;


    protected int x;
    protected int y;
    private int dx;
    private int dy;
    protected int width;
    protected int height;
    protected boolean color;  // TRUE -> white FALSE -> black
    protected Image image;
    protected int type;
    protected boolean toMove;
    private boolean moved;
    //protected int futureMove;

    public Piece(boolean color, int type) {

        this.x = 0;
        this.y = 0;
        this.color = color;
        this.type = type;
        this.toMove = false;
        this.moved = false;
        //this.futureMove = 99;

    }
    public int getType(){
      return type;
    }
    public boolean getColor(){
      return color;
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
    public void move(){
      this.x = dx;
      this.y = dy;
      moved = true;
      setMove(false);
    }
    public void mousePressed(MouseEvent e){
      int box = beginningBox(e.getX(), e.getY());
      dx= STEP*((box/10)-1) + INITIAL_X;
      dy = INITIAL_Y - STEP*((box-1)%10);
    }
    public void moveToBox(int box){
      dx= STEP*((box/10)-1) + INITIAL_X;
      dy = INITIAL_Y - STEP*((box-1)%10);
    }

    public void moveToShow(int x,int y){
      dx= x;
      dy = y;
    }
    public int getBox(){
      return beginningBox(x,y);
    }
    public int beginningBox(int xi, int yi){
      int i = 0;
      int x = 9;
      int y = 9;

      while(i < 8){
          if((xi >= INITIAL_X + STEP*i) && xi < OUTOFBOUND_X){
            x = i+1;
          }
          if((yi < INITIAL_Y - STEP*i) && yi < OUTOFBOUND_Y){
            y= i+2;
          }
          else if((yi >= INITIAL_Y && yi< OUTOFBOUND_Y)){
            y = 1;
          }
          i++;
      }
      return x*10 +y;
    }
    public boolean isMoved(){
      return moved;
    }
}
