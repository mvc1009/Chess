package chess;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Dot {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;

    public Dot(int x, int y) {

        this.x = x;
        this.y = y;
        visible = false;
        initDot();
    }
    private void initDot(){
      loadImage("multimedia/yellow_dot");
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
}
