package chess;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.EventQueue;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import java.awt.Color;

public class QuitButtonEx extends JPanel implements ActionListener{
    private Background background;

    public QuitButtonEx(){
        initMenu();
    }

    private void initMenu() {
      background = new Background();
      menu();
    }

    private void menu() {
      JFrame menu = new JFrame("SAD Chess Menue");
      menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //menu.setBounds(700,350,400,350);

      //FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 50, 50);
      //layout.add(background);
      //menu.setLayout(layout);
      menu.setLocationRelativeTo(null);
      menu.setLayout(new BorderLayout());

      // PANEL
      JPanel panel = new JPanel();
      panel.setSize(new Dimension(400,150 ));
      //panel.setBackground(Color.ORANGE);
      //panel.setPreferredSize(new Dimension(400, 400));

      JButton quitButton = new JButton("Quit");
      JButton playButton = new JButton("PLAY: SAD loser and a Winer");
      playButton.setVerticalTextPosition(AbstractButton.CENTER);
      playButton.setHorizontalTextPosition(AbstractButton.LEADING);
      quitButton.setVerticalTextPosition(AbstractButton.BOTTOM);
      quitButton.setHorizontalTextPosition(AbstractButton.CENTER);

      quitButton.addActionListener((event) -> System.exit(0));
      playButton.setActionCommand("PLAY");
      playButton.addActionListener(this);

      JLabel texto = new JLabel("Chess game,choose your option: ");
      texto.setBounds(50,50,220,40);

      panel.add(texto);
      panel.add(playButton);
      panel.add(quitButton);
      menu.add(panel);

      menu.add(background);//, BorderLayout.CENTER);
//      panel.repaint();
      menu.pack();
      menu.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if ("PLAY".equals(e.getActionCommand())){
        ChessGame chess = new ChessGame();
        chess.startGame();
        }
        else{}
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintBackground(g);

        Toolkit.getDefaultToolkit().sync();
    }

    public void paintBackground(Graphics g){
      Graphics2D m2d = (Graphics2D) g;
      m2d.drawImage(background.getImage(),0,0,this);
    }

    public static void main(String[] args) {
          QuitButtonEx ex = new QuitButtonEx();
          ex.setVisible(true);
      }
}
