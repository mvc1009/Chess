package chess;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import chess.piece.*;

public class EndGame implements ActionListener {

  Board b;
  boolean buttonPressed;
  JFrame endgame = new JFrame();
  boolean winner;

  public EndGame(Board b, boolean winner) {
      this.b = b;
      this.winner = winner;
      showFrame();
  }


  private void showFrame(){

    JButton butt = new JButton("OK");
    butt.setOpaque(true);
    butt.setContentAreaFilled(true);
    butt.setBorderPainted(true);
    butt.setBorder(null);
    butt.setBounds(100, 60 , 100, 20);
    butt.setActionCommand("BACK");
    butt.addActionListener(this);



    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setSize(new Dimension(120,120));
    panel.setLocation(500,300);
    panel.setVisible(true);
    String msg = "CHECK MATE, the winner is ";
      if(winner){
        msg = msg + "WHITE";
      }else{
        msg = msg + "BLACK";
      }
    JLabel texto = new JLabel (msg);
    texto.setBounds(20, 0, 300, 40);

    panel.add(texto);
    panel.add(butt);

    endgame.add(panel);
    endgame.setSize(300,150);

    endgame.setTitle("CHECK MATE");
    endgame.setLocationRelativeTo(null);
    endgame.setVisible(true);
  }
  @Override
  public void actionPerformed(ActionEvent e){
    if(e.getActionCommand() == "BACK"){
      b.game.endGame();
    }
  }
}
