/**
 * AI - The abstract class served as the template for two AIs. 
 *
 * @author Zuoyuan Huang
 * @author Jiaqing Mo
 * @version 03/18/2016 - Initial version developed. 
 */

import java.awt.Point;
import java.util.ArrayList;

public abstract class AI {

   private int[][] grid;

   /**
    * A constructor for the AI abstract class. 
    *
    * @param grid The grid to be input to the AI.
    */
   public AI(int[][] grid) {
      this.grid = grid;
   }

   /**
    * Put a chess on the destinated position. 
    *
    * @param x The x coordinate of the chess to be put.
    * @param y The y coordinate of the chess to be put.
    * @param chess The type of the chess to be put.
    */
   public void put(int x, int y, int chess) {
      grid[x][y] = chess;
   }

   /**
    * Return the current grid operated.
    *
    * @return The grid currently operated.
    */
   public int[][] getGrid() {
      return grid;
   }

   /**
    * Take the destinated chess from the grid. 
    *
    * @param x The x coordinate of the chess to be taken..
    * @param y The y coordinate of the chess to be taken..
    */
   public void take(int x, int y) {
      grid[x][y] = 0;
   }

   /**
    * Generate a placement by the computer. 
    *
    * @param player The number representation of computer.
    * @return The move generated by the computer. 
    */
   public abstract Point place(int player);

}
