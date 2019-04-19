package chess;

import java.awt.Image;
import javax.swing.ImageIcon;


public class ChessLetters {

    protected int width;
    protected int height;
    protected Image image;


    public ChessLetters() {
        initChessLetters();
    }

    private void initChessLetters() {
        loadImage("multimedia/title.png");
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
