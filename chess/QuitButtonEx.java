package chess;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.EventQueue;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

public class QuitButtonEx extends JPanel implements ActionListener{
    private Background panel;
    private ChessLetters title;
    JFrame menu;

    public QuitButtonEx(){
        initMenu();
    }

    private void initMenu() {
      panel = new Background();
      title = new ChessLetters();
      menu();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void menu() {

      //BUTTONS
      JButton playButton = new JButton(new ImageIcon("multimedia/play.png"));
      playButton.setOpaque(false);
      playButton.setContentAreaFilled(false);
      playButton.setBorderPainted(false);
      playButton.setBorder(null);
      playButton.setBounds(480,250,60,60);
      playButton.setActionCommand("PLAY");
      playButton.addActionListener(this);

      JButton quitButton = new JButton(new ImageIcon("multimedia/quit.png"));
      quitButton.setOpaque(false);
      quitButton.setContentAreaFilled(false);
      quitButton.setBorderPainted(false);
      quitButton.setBorder(null);
      quitButton.setBounds(660,250,60,60);
      quitButton.addActionListener((event) -> System.exit(0));

      //Adding Buttons to QuitButtons JPanel
      this.setLayout(null);
      //this.setSize(180,60);
      this.add(playButton);
      this.add(quitButton);

      //Fixing menu JFrame

      menu = new JFrame();
      menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      menu.add(this);
      menu.setSize(1200,700);
      menu.setVisible(true);
      menu.setResizable(false);
      menu.setTitle("Main Menu");
      menu.setLocationRelativeTo(null);
      menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    @Override
    public void actionPerformed(ActionEvent e){
        if ("PLAY".equals(e.getActionCommand())){
          ChessGame chess = new ChessGame();
          chess.startGame();
          menu.setVisible(false);
        }
    }

    public void doDrawing(Graphics g){
      Graphics2D g2d = (Graphics2D) g;
      g2d.drawImage(panel.getImage(),0,0,this);
      g2d.drawImage(title.getImage(),50,50,this);

      g2d.setColor(Color.BLACK);
      Font small = new Font("Calibri",Font.BOLD, 25);
      FontMetrics fm = getFontMetrics(small);
      g2d.setFont(small);
      g2d.drawString("Choose your Option: ", 200,200);
    }

    public static void main(String[] args) {
          QuitButtonEx ex = new QuitButtonEx();
          ex.setVisible(true);
          //Borrar las dos lineas a continuación, descomentar lo anterior.
          //ChessGame chess = new ChessGame();
          //chess.startGame();
    }
}
