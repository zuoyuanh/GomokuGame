/**
 * RandomAI - The random version of AI.
 *
 * @author Zuoyuan Huang
 * @version 03/03/2016 - Basic stucture of the class developed. 
 */

import java.awt.Point;

public class RandomAI extends AI {

   private int[][] grid;

   /**
    * Construct a random AI with grid. 
    */
   public RandomAI(int[][] grid) {
      super(grid);
      this.grid = super.getGrid();
   }

   /**
    * Generate a move randomly by the computer. 
    *
    * @param player The number of the player who made the move.
    */
   public Point place(int player) {
      int x = (int)(Math.random()*15);
      int y = (int)(Math.random()*15);
      while (grid[x][y] != 0) {
         x = (int)(Math.random()*15);
         y = (int)(Math.random()*15);
      }
      put(x, y, player);
      return new Point(x, y);
   }

}
