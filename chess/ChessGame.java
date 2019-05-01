package chess;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class ChessGame extends JFrame {

    private boolean chessgameVisible = false;
    private QuitButtonEx menu;
    public ChessGame(QuitButtonEx menu) {
        this.menu = menu;
        initUI();
    }

    private void initUI() {

        add(new Board(menu, this));

        setSize(1200, 700);
        setResizable(false);

        setTitle("ChessGame");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startGame() {
        EventQueue.invokeLater(() -> {
            this.setVisible(true);
        });
    }
    public void endGame(){
      EventQueue.invokeLater(() -> {
          this.setVisible(false);
          menu.setVisible(true);
      });
    }
}
