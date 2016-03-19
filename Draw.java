/**
 * @author Zuoyuan Huang, Jiaqing Mo
 * @version 03/17/2016
 */
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JOptionPane;
public class Draw extends JFrame{
   JPanel board=new JPanel();
   JPanel button = new JPanel();
   private static Chess chess[]=new Chess[225];
   private static boolean computerTurn;
   private static GomokuIO gio = new GomokuIO();
   private static GomokuCore gc;
   private static boolean player1Turn = true;
   public static void main(String args[]){
      //search for files
      //if file found
      savedGame();
      //if no files found, mode();
   }
   /**
    * Ask the user if they want to continue previous game. If not, create a new game.
    */
   public static void savedGame(){
      String[] choices = {"continue previous game", "new game","quit"};
      try {
         gio.in();
         JOptionPane j = new JOptionPane("NewGame?",0,-1,null,choices);
         int response=JOptionPane.showOptionDialog(null,"NewGame?"," ",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,choices, "quit");
         switch (response){
            case 0:
               //continue previous game TODO
               j.setVisible(false);
               placePreviousChess();
               break;
            case 1:
               //new game, delete the previous game
               j.setVisible(false);
               gio.removeSaved();
               mode();
               break;
            case 2://quit
               System.exit(0);         
         }
      } catch (Exception e) {
         mode();
      }
   }
   /**
    * Place the previous game on the board according to the saved file.
    */
   public static void placePreviousChess(){
      int mode = gio.getMode();
      new Draw(mode);
      gc = gio.getCore();
      int[][] grid = gc.getGrid();
      if (mode == 0) {
         player1Turn = (gio.getTurn()==1)?true:false;
      }
      for (int i=0; i<15; i++) {
         for (int j=0; j<15; j++) {
            if (grid[i][j] == 1) {
               Chess c = chess[convert(new Point(i, j))];
               c.setBlack();
               c.removeActionListener(c);
            }
            if (grid[i][j] == 2) {
               Chess c = chess[convert(new Point(i, j))];
               c.setWhite();
               c.removeActionListener(c);
            }
         }
      }
   }
   /**
    *Ask the user which mode they want to play. 2 players/1 player level 1/ 1 player level 2.
    */
   public static void mode(){
      String[] choices = {"2 Players", "1 Player Level 1", "1 Player Level 2", "quit"};
      JOptionPane j =new JOptionPane("Mode",0,-1,null,choices);
      int response=JOptionPane.showOptionDialog(null,"Mode"," ",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,choices, "quit");
      gc = new GomokuCore(response);
      switch (response){
         case 0:
            //2 players
            j.setVisible(false);
            new Draw(0);
            break;
         case 1:
            //level 1
            j.setVisible(false);
            new Draw(1);
            break;
         case 2:
            //level 2
            j.setVisible(false);
             new Draw(2);
            break;
         case 3:
            System.exit(0);         
      }
   }
   /**
    * to see if it is player2's turn.
    * @return a boolean represent is player2's turn.
    */
   public static boolean isPlayer2Turn(){
      return !player1Turn;
   }

   /**
    * to see if it is user's turn.
    * @return a boolean represent is user's turn.
    */
   public static boolean isUserTurn() {
      return !computerTurn;
   }
   /**
    * Display the message showing the result of the game.
    */
   public static void displayResult(int res){  
      if (res==0){
         JOptionPane.showMessageDialog(null,"Black chess Win!");
      }
      if (res==1){
         JOptionPane.showMessageDialog(null,"White chess Win!");
      }
      if (res==2){
         JOptionPane.showMessageDialog(null,"You Lose!");
      }
      System.exit(0);
      
   }
   /**
    * Place the chess according to users' input.
    */
   public static void userPlace(Point p, int mode) {
      System.out.println("USER PLACEMENT: "+p);
      System.out.println("------------------------------------");
      if (mode == 0) {
         gc.manualPlace(p.x,p.y,player1Turn?1:2);
      } else {
         gc.manualPlace(p.x,p.y,1);
      }
      if (gc.checkWin(p.x,p.y)){
         if (player1Turn){
            chess[convert(p)].setBlack();
            displayResult(0);//player1 win
         }
         else{

            chess[convert(p)].setWhite();
            displayResult(1);//plyer2 win
         }
      }
      computerTurn = true;
      player1Turn=!player1Turn;
   }
   /**
    * Place the chess according to smartComputer.
    *
    * @return the point according to the smart computer.
    */
   public static Point smartPlace() {
      return gc.smartPlace(2);
   }
   /**
    * check if it is smartComputerTurn.
    */
   public static void smartComputerTurn() {
      computerTurn = true;
      Point p = gc.smartPlace(2);
      if (gc.checkWin(p.x,p.y)){
         chess[convert(p)].setWhite();
         displayResult(2);//player lose
      }
      System.out.println(p);
      Chess c = chess[convert(p)];
      c.setWhite();
      c.removeActionListener(c);
      computerTurn = false;
   }
   /**
    * check if it is normal computer's turn.
    */
   public static void normalComputerTurn(){
      computerTurn = true;
      Point p = gc.randomPlace(2);
      if (gc.checkWin(p.x,p.y)){
         chess[convert(p)].setWhite();
         displayResult(2);//player lose
      }
      System.out.println(p);
      Chess c=chess[convert(p)];
      c.setWhite();
      c.removeActionListener(c);
      computerTurn=false;
   }
   /**
    * convert the chess number into a Point.
    * @return a Point represent the chess number.
    */
   public static Point convert(int chessNum) {
      int x = chessNum/15;
      int y = chessNum%15;
      return new Point(x, y);
   }
   /**
    * convert a Point into a chess number.
    * @return an int represent the Point.
    */
   public static int convert(Point p) {
      return p.x*15+p.y;
   }
   /**
    * Initialize the chess board and the buttons.
    * @param mode an int representing which mode the user chose.
    */
   public Draw(int mode){
      super("Chess");
      setSize(1000,1000);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      board.setSize(new Dimension(400, 400));
      board.setLayout(new GridLayout(15,15));
      if (mode==0){
         for (int i=0;i<225;i++){
            chess[i]=new Chess(i,0);
            board.add(chess[i]);
         }
      }
      if (mode==1){
         for (int i=0;i<225;i++){
            chess[i]=new Chess(i,1);
            board.add(chess[i]);
         }
      }
      if (mode==2){
         for (int i=0;i<225;i++){
            chess[i]=new Chess(i,2);
            board.add(chess[i]);
         }
      }
      JButton exitButton = new JButton("exit");
      exitButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e){
            System.exit(0);
         }
      });
      button.add(exitButton);
      JButton saveButton = new JButton("save");
      final int fmode = mode;
      saveButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e){
            GomokuIO io = null;
            if (fmode == 0) {
               io = new GomokuIO(gc, 0, player1Turn?1:2);
            } else {
               io = new GomokuIO(gc, fmode);
            }
            io.out();
            System.exit(0);
         }
      });
      button.add(saveButton);
      add(board);
      add(button,BorderLayout.EAST);
      setVisible(true);
   }

}

