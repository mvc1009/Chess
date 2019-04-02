package chess;
import javax.swing.JFrame;

public class Window extends JFrame {
  public Window(){
    setTitle("Chess Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setContentPane(new GamePanel());
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }


}
