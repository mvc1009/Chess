package chess;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Dot {

    public static final int INITIAL_X = 220;
    public static final int INITIAL_Y = 490;
    public static final int STEP = 60;

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;
    protected int box;

    public Dot(int box) {

        this.box = box;
        visible = false;
        initDot();
        getCordinates();
    }
    private void initDot(){
      loadImage("multimedia/yellow_dot.png");
      getImageDimensions();

    }
    private void getCordinates(){
      this.x= STEP*((box/10)) + INITIAL_X;
      this.y = INITIAL_Y - STEP*((box-1)%10);
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

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
