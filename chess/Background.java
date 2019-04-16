package chess;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Background  extends JPanel{

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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintBackground(g);

        Toolkit.getDefaultToolkit().sync();
    }

    public void paintBackground(Graphics g){
      Graphics2D g2d = (Graphics2D) g;
      g2d.drawImage(image ,0,0,getWidth(),getHeight(),null);
      setOpaque(false);
    }
}
