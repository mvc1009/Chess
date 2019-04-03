package chess;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Background {

    protected int width;
    protected int height;
    protected Image image;

    public Background() {
        initBackground();
    }

    private void initBackground() {
        loadImage("multimedia/wallpaper.jpg");
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
