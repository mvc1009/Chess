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

  boolean colour;
  Board.HitTestAdapter mouse;
  int box;
  int typechanged = 0;
  boolean buttonPressed;
  JFrame choices = new JFrame();

  public PawnAtEnd(int box, boolean colour, Board.HitTestAdapter mouse) {
      this.mouse = mouse;
      this.colour=colour;
      this.box = box;
      showOptions();
  }


  private void showOptions(){
    String img = "multimedia/pieces/center/";
    if(colour)
      img = img + "white";
    else
      img = img + "black";

    JButton queenButton = new JButton(new ImageIcon(img + "_queen_center.png"));
    queenButton.setOpaque(true);
    queenButton.setContentAreaFilled(true);
    queenButton.setBorderPainted(false);
    queenButton.setBorder(null);
    queenButton.setBounds(0, 0 , 60, 60);
    queenButton.setActionCommand("QUEEN");
    queenButton.addActionListener(this);

    JButton horseButton = new JButton(new ImageIcon(img + "_horse_center.png"));
    horseButton.setOpaque(true);
    horseButton.setContentAreaFilled(true);
    horseButton.setBorderPainted(false);
    horseButton.setBorder(null);
    horseButton.setBounds(0, 0 , 60, 60);
    horseButton.setActionCommand("HORSE");
    horseButton.addActionListener(this);

    JButton bishopButton = new JButton(new ImageIcon(img+"_bishop_center.png"));
    bishopButton.setOpaque(true);
    bishopButton.setContentAreaFilled(true);
    bishopButton.setBorderPainted(false);
    bishopButton.setBorder(null);
    bishopButton.setBounds(0, 0 , 60, 60);
    bishopButton.setActionCommand("BISHOP");
    bishopButton.addActionListener(this);

    JButton towerButton = new JButton(new ImageIcon(img+"_tower_center.png"));
    towerButton.setOpaque(true);
    towerButton.setContentAreaFilled(true);
    towerButton.setBorderPainted(false);
    towerButton.setBorder(null);
    towerButton.setBounds(0, 0 , 60, 60);
    towerButton.setActionCommand("TOWER");
    towerButton.addActionListener(this);

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
    panel.add(horseButton);
    panel.add(bishopButton);
    panel.add(towerButton);
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
    switch(e.getActionCommand()){
      case "QUEEN":
            typechanged = QUEEN;
            break;
      case "HORSE":
            typechanged = HORSE;
            break;
      case "BISHOP":
            typechanged = BISHOP;
            break;
      case "TOWER":
            typechanged = TOWER;
            break;
    }
    changePiece();
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
