package chess;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class ChessGame extends JFrame {

    private boolean chessgameVisible = false;
    public ChessGame() {

        initUI();
    }

    private void initUI() {

        add(new Board());

        setSize(1200, 700);
        setResizable(false);

        setTitle("ChessGame");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ChessGame ex = new ChessGame();
            ex.setVisible(true);
        });
    }
}
