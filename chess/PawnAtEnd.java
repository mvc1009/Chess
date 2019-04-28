package chess;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Thread;
import java.lang.Exception;

import java.io.*;
import chess.piece.*;

public class PawnAtEnd implements ActionListener {
  public static final int TOWER = 1;
  public static final int HORSE = 2;
  public static final int BISHOP = 3;
  public static final int QUEEN = 4;
  public static final int KING = 5;
  public static final int PAWN = 6;

  public static final int INITIAL_X = 220;
  public static final int INITIAL_Y = 490;
  public static final int STEP = 60;
  public static final int OUTOFBOUND_X = 700;
  public static final int OUTOFBOUND_Y = 550;

  protected Lock lock;
  protected Condition choiceMade;

  Piece pawn;
  Board.HitTestAdapter mouse;
  int box;
  int typechanged = 0;
  boolean buttonPressed;
  JFrame choices = new JFrame();

  public PawnAtEnd(int box, boolean colour, Board.HitTestAdapter mouse) {
      this.mouse = mouse;
      pawn = new Pawn(colour, box%10);
      this.box = box;
      showOptions();
  }


  private void showOptions(){
    JButton queenButton = new JButton(new ImageIcon("multimedia/pieces/center/white_queen_center.png"));
    queenButton.setOpaque(true);
    queenButton.setContentAreaFilled(true);
    queenButton.setBorderPainted(false);
    queenButton.setBorder(null);
    queenButton.setBounds(0, 0 , 60, 60);
    queenButton.setActionCommand("QUEEN");
    queenButton.addActionListener(this);

    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setSize(new Dimension(450,450));
    panel.setLocation(500,300);
    panel.setVisible(true);

    JLabel texto = new JLabel ("Choose which piece to change for the pawn");
    texto.setBounds(50, 50, 300, 40);

    //Adding Buttons to QuitButtons JPanel
    //choices.setLayout(null);
    panel.add(texto);
    panel.add(queenButton);
    //this.add(quitButton);

    choices.add(panel);
    choices.setSize(OUTOFBOUND_X,OUTOFBOUND_Y);
    //choices.setResizable(false);
    choices.setTitle("Turn pawn into");
    choices.setLocationRelativeTo(null);
    choices.setVisible(true);
  }
  @Override
  public void actionPerformed(ActionEvent e){

    if ("QUEEN".equals(e.getActionCommand())){
      System.out.println(typechanged);
      typechanged = QUEEN;
      changePiece();
    }
  }

  public void changePiece(){
    mouse.turnInToPiece(this.box);
  }
  public boolean choiceMade(){
    return buttonPressed;
  }
  public int newType(){
    return typechanged;
  }
}
