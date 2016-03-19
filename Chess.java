/**
 * @author Jiaqing Mo, Zuoyuan Huang
 * @version 1.0.0
 * @version 3/17/2016
 * */
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Point;
/**
 * A Chess class specified the Chess behaviour on the chess board. Extends JButton, implements ActionListener.
 */
public class Chess extends JButton implements ActionListener{
   private int chessNum;
   private int mode;
   ImageIcon white,black;
   public Chess(int chessNum, int mode){
      white=new ImageIcon(this.getClass().getResource("white.png"));
      black=new ImageIcon(this.getClass().getResource("black.png"));
      this.chessNum = chessNum;
      this.addActionListener(this);
      this.mode=mode;
   }
   /**
    * Set the chess to black.
    */
   public void setBlack() {
      setIcon(black);
   }
   /**
    * Set the chess to black.
    */
   public void setWhite() {
      setIcon(white);
   }
   /**
    * Change the chess color according to the computer and player's action.
    * 
    * @param e the ActionEvent perform
    */
   public void actionPerformed(ActionEvent e){
      System.out.println(chessNum);
      if (mode==2){//smart computer
         if (Draw.isUserTurn()) {
            setIcon(black);
            Draw.userPlace(Draw.convert(chessNum),2);
            this.removeActionListener(this);
            Draw.smartComputerTurn();
         }
      }
      if (mode==1){ //random computer     
         if (Draw.isUserTurn()){
            setIcon(black);
            Draw.userPlace(Draw.convert(chessNum),1);
            this.removeActionListener(this);
            Draw.normalComputerTurn();
         }
      }
      if (mode==0){//2 players
         if (Draw.isPlayer2Turn()){       
            setIcon(white);
         }
         else{
            setIcon(black);
         }
         Draw.userPlace(Draw.convert(chessNum),0);
         this.removeActionListener(this);
      }
   }
}  
