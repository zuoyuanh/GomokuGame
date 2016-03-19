/**
 * @author Zuoyuan Huang, Jiaqing Mo
 * @version 03/17/16
 */
import java.io.File;
import java.awt.Point;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.FileNotFoundException;

public class GomokuIO {

   private GomokuCore core;
   private int turn;
   private int mode;
   /**
    * create an empty GomokuIO.
    */
   public GomokuIO() {
      turn = 0;
      mode = -1;
   }
   /**
    * create a GomokuIO with mode and turn.
    * @param core the GomokuCore containing information.
    * @param mode an int representing the mode.
    * @param turn an int representing the turn.
    */
   public GomokuIO(GomokuCore core, int mode, int turn) {
      this.core = core;
      this.turn = turn;
      mode = 1;
   }
   /**
    * create a GomokuIO with mode.
    * @param core the GomokuCore containing information.
    * @param mode an int representing the mode.
    */
   public GomokuIO(GomokuCore core, int mode) {
      this.core = core;
      this.mode = mode;
   }
   /**
    * read the file from from current directory.
    * @throws FileNotFoundException when the file can't be found.
    */
   public void in() throws FileNotFoundException {
      Scanner sc = new Scanner(new File(".Recent.gmkData"));
      mode = sc.nextInt();
      int[][] grid = new int[15][15];
      for (int i=0; i<15; i++) {
         for (int j=0; j<15; j++) {
            grid[i][j] = sc.nextInt();
         }
      }
      if (mode == 0) {
         turn = sc.nextInt();
         core = new GomokuCore(grid);
      } else if (mode == 1) {
         core = new GomokuCore(grid);
      } else if (mode == 2) {
         int x = -1;
         int y = -1;
         if (sc.hasNext()) {
            x = sc.nextInt();
         }
         if (sc.hasNext()) {
            y = sc.nextInt();
         }
         Point first = null;
         if (x!=-1 && y!=-1) {
            first = new Point(x, y);
         }
         x = -1;
         y = -1;
         if (sc.hasNext()) {
            x = sc.nextInt();
         }
         if (sc.hasNext()) {
            y = sc.nextInt();
         }
         Point currentPoint = null;
         if (x!=-1 && y!=-1) {
            currentPoint = new Point(x, y);
         }
         core = new GomokuCore(grid, first, currentPoint);
      }
      sc.close();
      removeSaved();
   }
   /**
    * create a new file and save the chess information.
    */
   public void out() {
      int[][] grid = core.getGrid();
      try {
         PrintStream ps = new PrintStream(new File(".Recent.gmkData"));
         if (mode == 0) {
            ps.println("0");
            for (int i=0; i<15; i++) {
               for (int j=0; j<15; j++) {
                  ps.print(grid[i][j]+" ");
               }
               ps.println();
            }
            ps.println(turn);
         } else if (mode == 1) {
            ps.println("1");
            for (int i=0; i<15; i++) {
               for (int j=0; j<15; j++) {
                  ps.print(grid[i][j]+" ");
               }
               ps.println();
            }
         } else if (mode == 2) {
            ps.println("2");
            for (int i=0; i<15; i++) {
               for (int j=0; j<15; j++) {
                  ps.print(grid[i][j]+" ");
               }
               ps.println();
            }
            Point first = core.getFirst();
            Point currentPoint = core.getCurrentPoint();
            ps.println(first.x+" "+first.y);
            ps.println(currentPoint.x+" "+currentPoint.y);
         }
         ps.close();
      } catch (Exception e) {
      }
   }
   /**
    * remove existing file.
    */
   public void removeSaved() {
      try {
         File f = new File(".Recent.gmkData");
         f.delete();
      } catch (Exception e) {
      }
   }
   /**
    * get the GomokuCore containing information.
    * @return GomomuCore.
    */
   public GomokuCore getCore() {
      return core;
   }
   /**
    * get the turn. user/computer.
    * @return an int representing the turn.
    */
   public int getTurn() {
      return turn;
   }

   /**
    * get the mode. 
    * @return an int representing the mode.
    */
   public int getMode() {
      return mode;
   }

}
