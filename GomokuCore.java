/**
 * GomokuCore - The core functions of the Gomoku game.
 *
 * @author Zuoyuan Huang
 * @author Jiaqing Mo
 * @version 03/18/2016 - Two AIs are split from this class. 
 * @version 03/16/2016 - Bug fix for the findThreat() method.
 * @version 03/15/2016 - Bug fix for the checkWin() method.
 * @version 03/14/2016 - Add check certain win for the findThreat() method.
 * @version 03/11/2016 - Improvements for the findThreat() method. 
 *                       Now it is able to detect more types of potential threats.
 * @version 03/09/2016 - Add the findThreat() method to find the potential.
 *                       threat made by user. React fast, improving efficiency.
 * @version 03/08/2016 - Add restrictions to the grid searched. 
                         Improving efficiency. 
 * @version 03/07/2016 - Improvements for the evaluation() method. 
 * @version 03/07/2016 - Improvements for the search() method. (efficiency)
 * @version 03/05/2016 - Initial version of search() and evaluation() method developed.
 * @version 03/04/2016 - Implementation for some basic methods such as checkWin().
 * @version 03/03/2016 - Basic stucture of the class developed. 
*/

import java.awt.Point;
import java.util.ArrayList;

public class GomokuCore {

   private int[][] grid;
   private AI ai;

   /**
    * Constructor for a new game.
    */
   public GomokuCore(int mode) {
      grid = new int[15][15];
      for (int i=0; i<15; i++) {
         for (int j=0; j<15; j++) {
            grid[i][j] = 0;
         }
      }
      if (mode == 1) {
         ai = new RandomAI(grid);
      } else if (mode == 2) {
         ai = new SmartAI(grid);
      }
   }

   /**
    * Constructor for a saved game for 2 players/user vs random.
    * 
    * @param grid The grid saved previously.
    */
   public GomokuCore(int[][] grid) {
      this.grid = grid;
   }

   /**
    * Constructor for a saved smart game.
    *
    * @param grid The grid saved previously.
    * @param first The first point placed in the saved game.
    * @param currentPoint The last placement in the saved game.
    */
   public GomokuCore(int[][] grid, Point first, Point currentPoint) {
      this.grid = grid;
      this.ai = new SmartAI(grid, first, currentPoint);
   }

   /**
    * Check whether someone win at a certain point.
    *
    * @param p The point to be checked.
    * @return True if the user at the provided point wins.
    */
   public boolean checkWin(Point p) {
      return checkWin(p.x, p.y);
   }

   /**
    * Check whether someone win at a certain point.
    *
    * @param x The x coordinate of the point to be checked.
    * @param y The y coordinate of the point to be checked.
    * @return True if the user at the provided point wins.
    */
   public boolean checkWin(int x, int y) {
      int key = grid[x][y];
      if (key == 0) {
         return false;
      }
      int count = 0;
      int doX = x;
      int doY = y;
      while (grid[doX][doY] == key) {
         count++;
         if (doY+1 < 15) {
            doY++;
         } else {
            break;
         }
      }
      if (y != 0) {
         doX = x;
         doY = y-1;
         while (grid[doX][doY] == key) {
            count++;
            if (doY-1 >= 0) {
               doY--;
            } else {
               break;
            }
         }
      }
      if (count >= 5) {
         return true;
      }
      count = 0;
      doX = x;
      doY = y;
      while (grid[doX][doY] == key) {
         count++;
         if (doX+1 < 15) {
            doX++;
         } else {
            break;
         }
      }
      if (x != 0) {
         doX = x-1;
         doY = y;
         while (grid[doX][doY] == key) {
            count++;
            if (doX-1 >= 0) {
               doX--;
            } else {
               break;
            }
         }
      }
      if (count >= 5) {
         return true;
      }
      count = 0;
      doX = x;
      doY = y;
      while (grid[doX][doY] == key) {
         count++;
         if (doX+1<15 && doY+1<15) {
            doX++;
            doY++;
         } else {
            break;
         }
      }
      if (x>0 && y>0) {
         doX = x-1;
         doY = y-1;
         while (grid[doX][doY] == key) {
            count++;
            if (doX-1>=0 && doY-1>=0) {
               doX--;
               doY--;
            } else {
               break;
            }
         }
      }
      if (count >= 5) {
         return true;
      }
      count = 0;
      doX = x;
      doY = y;
      while (grid[doX][doY] == key) {
         count++;
         if (doX+1<15 && doY-1>=0) {
            doX++;
            doY--;
         } else {
            break;
         }
      }
      if (x-1>0 && y+1<15) {
         doX = x-1;
         doY = y+1;
         while (grid[doX][doY] == key) {
            count++;
            if (doX-1>=0 && doY+1<15) {
               doX--;
               doY++;
            } else {
               break;
            }
         }
      }
      if (count >= 5) {
         return true;
      }
      return false;
   }

   /**
    * Get the grid of the current game.
    *
    * @return A reference to the grid of the current game.
    */
   public int[][] getGrid() {
      return grid;
   }

   /**
    * Get a copy of the grid of the current game.
    *
    * @return A copy of the grid of the current game.
    */
   public int[][] copyGrid() {
      int[][] newGrid = new int[15][15];
      for (int i=0; i<15; i++) {
         for (int j=0; j<15; j++) {
            newGrid[i][j] = grid[i][j];
         }
      }
      return newGrid;
   }

   /**
    * Get the first placement of current game. 
    *
    * @return The first placement of current game.
    */
   public Point getFirst() {
      if (ai.getClass() == SmartAI.class) {
         return ((SmartAI)ai).getFirst();
      }
      return null;
   }

   /**
    * Get the last placement made.
    *
    * @return The last placement made.
    */
   public Point getCurrentPoint() {
      if (ai.getClass() == SmartAI.class) {
         return ((SmartAI)ai).getCurrentPoint();
      }
      return null;
   }

   private void take(int x, int y) {
      grid[x][y] = 0;
   }

   /**
    * Place a chess on the grid mannually.
    *
    * @param p The point of the expected placement.
    * @param player The number of the player who made the move. 
    */
   public void manualPlace(Point p, int player) {
      manualPlace(p.x, p.y, player);
   }

   public void put(int x, int y, int player) {
      grid[x][y] = player;
   }

   /**
    * Place a chess on the grid mannually.
    *
    * @param x The x coordinate of the expected placement.
    * @param y The y coordinate of the expected placement.
    * @param player The number of the player who made the move. 
    */
   public void manualPlace(int x, int y, int player) 
          throws GomokuIllegalPlacementException {
      if (ai.getClass() == SmartAI.class) {
         if (((SmartAI)ai).getFirst()==null) {
            ((SmartAI)ai).setFirst(new Point(x, y));
         }
      }
      if (grid[x][y] != 0) {
         throw new GomokuIllegalPlacementException();
      }
      put(x, y, player);
   }

   /**
    * Generate a random move by the computer. 
    *
    * @param player The number representation of computer.
    * @return The move generated by the computer. 
    */
   public Point randomPlace(int player) {
      if (ai == null) {
         ai = new RandomAI(grid);
      }
      return ai.place(2);
   }

   /**
    * Generate a move based on the current situation by the computer.
    *
    * @param player The number representation of the computer.
    * @return The move generated by the computer.
    */
   public Point smartPlace(int player) {
      if (ai == null) {
         ai = new SmartAI(grid);
      }
      return ai.place(2);
   }

   /**
    * Print the grid of the current game to the console. 
    */
   public void print() {
      for (int i=0; i<15; i++) {
         for (int j=0; j<15; j++) {
            System.out.print(grid[i][j]+" ");
         }
         System.out.println();
      }
   }

}
