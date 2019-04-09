package chess;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StrokePattern {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;

    private int dx;
    private int dy;

    private int INITIAL_X_stroke = 220 -2;
    private int INITIAL_Y_stroke = 490 -2;
    public static final int OUTOFBOUND_X = 700;
    public static final int OUTOFBOUND_Y = 550;
    public static final int STEP = 60;


    public StrokePattern() {

        this.x = INITIAL_X_stroke;
        this.y = INITIAL_Y_stroke;
        visible = false;
        initStrokePattern();
    }
    private void initStrokePattern(){
       loadImage("multimedia/yellow_stroke.png");
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    public void move(){
      this.x = dx;
      this.y = dy;
    }
    public void mousePressed(MouseEvent e){
      int box = beginningBox(e.getX(), e.getY());
      dx= STEP*((box/10)-1) + INITIAL_X_stroke;
      dy = INITIAL_Y_stroke - STEP*((box-1)%10);
      setVisible(true);
    }
    public int beginningBox(int xi, int yi){
      int i = 0;
      int x = 9;
      int y = 9;

      while(i < 8){
          if((xi > INITIAL_X_stroke + STEP*i) && xi < OUTOFBOUND_X){
            x = i+1;
          }
          if((yi < INITIAL_Y_stroke - STEP*i) && yi < OUTOFBOUND_Y){
            y= i+2;
          }
          else if((yi > INITIAL_Y_stroke && yi< OUTOFBOUND_Y)){
            y = 1;
          }
          i++;
      }
      return x*10 +y;
    }
}
