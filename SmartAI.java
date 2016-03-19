/**
 * SmartAI - The smart version of AI.
 *
 * @author Zuoyuan Huang
 * @author Jiaqing Mo
 * @version 03/18/2016 - Basic stucture of the class developed. 
 */

import java.awt.Point;
import java.util.ArrayList;

public class SmartAI extends AI {

   private int[][] grid;
   private Point first;
   private boolean isFirst = true;
   private Point currentPoint;

   /**
    * Construct a smart AI with grid. 
    */
   public SmartAI(int[][] grid) {
      super(grid);
      isFirst = true;
      this.grid = super.getGrid();
   }

   /**
    * Construct a smart AI with grid, the start point and the current point. 
    */
   public SmartAI(int[][] grid, Point first, Point currentPoint) {
      super(grid);
      this.grid = super.getGrid();
      isFirst = false;
      this.first = first;
      this.currentPoint = currentPoint;
   }

   /**
    * Return the first placement. 
    *
    * @return The first placement. 
    */
   public Point getFirst() {
      return first;
   }

   /**
    * Set the first point with destinated point.
    *
    * @param first Set the first point. 
    */
   public void setFirst(Point first) {
      this.first = first;
   }

   /**
    * Return the current point.
    *
    * @return The current point.
    */
   public Point getCurrentPoint() {
      return currentPoint;
   }

   private Point put(int x, int y) {
      System.out.println(isFirst);
      Point p = new Point(x, y);
      if (x<0 || y<0 || x>14 || y>14) {
         x = first.x;
         y = first.y;
         if (x-1 >= 0) {
            p = new Point(x-1, y);
         }
         if (x+1 < 15) {
            p = new Point(x+1, y);
         }
         if (y-1 >= 0) {
            p = new Point(x, y-1);
         }
         if (y+1 < 15) {
            p = new Point(x, y+1);
         }
         if (x-1>=0 && y-1>=0) {
            p = new Point(x-1, y-1);
         }
         if (x+1<15 && y+1<15) {
            p = new Point(x+1, y+1);
         }
         if (x-1>=0 && y+1<15) {
            p = new Point(x-1, y+1);
         }
         if (x+1<15 && y-1>=0) {
            p = new Point(x+1, y-1);
         }
      } else {
         grid[x][y] = 2;
      }
      return p;
   }

   private int scores(int num, int active, int table) {
      if (table == 1) {
         if (active == 2) {
            switch (num) {
               case 1: return 30;
               case 2: return 100;
               case 3: return 5000;
               case 4: return 50000;
               case 5: return 50000;
            }
         } else if (active == 1) {
            switch (num) {
               case 1: return 5;
               case 2: return 25;
               case 3: return 100;
               case 4: return 5000;
               case 5: return 50000;
            }
         } else if (active==0 && num==5) {
            return 50000;
         }
      } else if (table == 2) {
         if (active == 2) {
            switch (num) {
               case 1: return 10;
               case 2: return 100;
               case 3: return 5000;
               case 4: return 50000;
               case 5: return 50000;
            }
         } else if (active == 1) {
            switch (num) {
               case 1: return 5;
               case 2: return 25;
               case 3: return 100;
               case 4: return 50000;
               case 5: return 50000;
            }
         } else if (active==0 && num==5) {
            return 10000;
         }
      }
      return 0;
   }

   private int evaluate(Point start, Point end) {
      return evaluate(start, end, 1);
   }

   private int evaluate(Point start, Point end, int table) {
      int[] value = new int[2];
      boolean[][] calculated = new boolean[15][15];
      boolean[][] calculated1 = new boolean[15][15];
      boolean[][] calculated2 = new boolean[15][15];
      boolean[][] calculated3 = new boolean[15][15];
      for (int i=start.x; i<=end.x; i++) {
         for (int j=start.y; j<=end.y; j++) {
            int key = grid[i][j];
            if (key == 0) {
               continue;
            }
            int active = 0;
            int count = 0;
            int doX = i;
            int doY = j;
            if (calculated[i][j] != true) {
               if (doY!=0 && grid[doX][doY-1]==0) {
                  active++;
               }
               while (grid[doX][doY] == key) {
                  count++;
                  calculated[doX][doY] = true;
                  if (doY+1 < 15) {
                     doY++;
                  } else {
                     break;
                  }
               }
               if (doY != 15) {
                  if (grid[doX][doY] == 0) {
                     active++;
                  }
               }
               value[key-1] += scores(count, active, table);
            }
            if (calculated1[i][j] != true) {
               active = 0;
               count = 0;
               doX = i;
               doY = j;
               if (doX!=0 && grid[doX-1][doY]==0) {
                  active++;
               }
               while (grid[doX][doY] == key) {
                  count++;
                  calculated1[doX][doY] = true;
                  if (doX+1 < 15) {
                     doX++;
                  } else {
                     break;
                  }
               }
               if (doY != 15) {
                  if (grid[doX][doY] == 0) {
                     active++;
                  }
               }
               value[key-1] += scores(count, active, table);
            }
            if (calculated2[i][j] != true) {
               active = 0;
               count = 0;
               doX = i;
               doY = j;
               if (doX!=0 && doY!=0 && grid[doX-1][doY-1]==0) {
                  active++;
               }
               while (grid[doX][doY] == key) {
                  count++;
                  calculated2[doX][doY] = true;
                  if (doX+1<15 && doY+1<15) {
                     doX++;
                     doY++;
                  } else {
                     break;
                  }
               }
               if (doX!=15 && doY!=15) {
                  if (grid[doX][doY] == 0) {
                     active++;
                  }
               }
               value[key-1] += scores(count, active, table);
            }
            if (calculated3[i][j] != true) {
               active = 0;
               count = 0;
               doX = i;
               doY = j;
               if (doX!=0 && doY!=14 && grid[doX-1][doY+1]==0) {
                  active++;
               }
               while (grid[doX][doY] == key) {
                  count++;
                  calculated3[doX][doY] = true;
                  if (doX+1<15 && doY-1>=0) {
                     doX++;
                     doY--;
                  } else {
                     break;
                  }
               }
               if (doX!=15 && doY!=0) {
                  if (grid[doX][doY] == 0) {
                     active++;
                  }
               }
               value[key-1] += scores(count, active, table);
            }
         }
      }
      return value[1] - value[0];
   }

   private class Threat {

      public Point start;
      public Point end;
      public Point hole;
      public Point instruction;
      public int direction;
      public int threatClass;
      public boolean hasHole;
      public boolean hasThreat;
      public boolean instructed;

      public Threat() {
         this.hasThreat = false;
         this.hasHole = false;
      }

      public Threat(Point instruction, int threatClass) {
         this.hasThreat = true;
         this.instructed = true;
         this.instruction = instruction;
         this.threatClass = threatClass;
      }

      public Threat(Point start, Point end, int threatClass, int direction) {
         this.hasThreat = true;
         this.hasHole = false;
         this.start = start;
         this.end = end;
         this.threatClass = threatClass;
         this.direction = direction;
      }

      public Threat(Point start, Point end, Point hole, 
                  int threatClass, int direction) {
         this.hasThreat = true;
         this.hasHole = true;
         this.start = start;
         this.end = end;
         this.hole = hole;
         this.threatClass = threatClass;
         this.direction = direction;
      }

      public String toString() {
         return start + " " + end;
      }

   }

   private Threat findThreat() {
      int[] value = new int[2];
      Threat win = new Threat();
      Threat threat = new Threat();
      boolean[][] calculated = new boolean[15][15];
      boolean[][] calculated1 = new boolean[15][15];
      boolean[][] calculated2 = new boolean[15][15];
      boolean[][] calculated3 = new boolean[15][15];
      for (int i=0; i<15; i++) {
         for (int j=0; j<15; j++) {
            int key = 1;
            int myKey = 2;
            int active = 0;
            int count = 0;
            int myCount = 0;
            int doX = i;
            int doY = j;
            boolean special = false;
            Point hole = null;
            Point instruction = null;
            boolean foundHole = false;
            if (calculated[i][j] != true) {
               if (doY!=0 && grid[doX][doY-1]==0) {
                  instruction = new Point(doX, doY-1);
                  active++;
               }
               if (grid[doX][doY] == key) {
                  for (int k=doY; k<=(((doY+3)<14)?(doY+3):14); k++) {
                     if (grid[doX][k] == key) {
                        calculated[doX][k] = true;
                        count++;
                     } else {
                        if (k != (((doY+3)<14)?(doY+3):14)) {
                           if (grid[doX][k] == 0) {
                              hole = new Point(doX, k);
                              foundHole = true;
                           }
                        }
                     }
                  }
               }
               if (grid[doX][doY] == myKey) {
                  for (int k=doY; k<=(((doY+3)<14)?(doY+3):14); k++) {
                     if (grid[doX][k] == myKey) {
                        calculated[doX][k] = true;
                        myCount++;
                     }
                  }
               }
               if (doY != 15) {
                  if (count == 3) {
                     if (foundHole) {
                        if (doY+4<15 && grid[doX][doY+4] == 0) {
                           active++;
                        }
                        if (grid[doX][(doY+4<15)?(doY+4):14] == key) {
                           special = true;
                        }
                     } else {
                        if (doY+3<15 && grid[doX][doY+3] == 0) {
                           active++;
                        }
                     }
                  }
                  if (count == 4) {
                     if (doY+4<15 && grid[doX][doY+4] == 0) {
                        active++;
                     }
                  }
                  if (myCount == 4) {
                     if (doY+4<15 && grid[doX][doY+4] == 0) {
                        instruction = new Point(doX, doY+4);
                        active++;
                     }
                  }
                  if (myCount == 3) {
                     if (doY+3<15 && grid[doX][doY+3] == 0) {
                        instruction = new Point(doX, doY+3);
                        active++;
                     }
                  }
               }
               if (myCount==4 && active>=1) {
                  win = new Threat(instruction, 4);
               }
               if (myCount==3 && active==2) {
                  win = new Threat(instruction, 3);
               }
               if (count==3 && special==true) {
                  threat = new Threat(hole, 3);
               }
               if (count==4 && active==1) {
                  threat = new Threat(new Point(i, j), new Point(i, j+3), 4, 0);
               }
               if (count==3 && active==2) {
                  if (foundHole) {
                     threat = new Threat(new Point(i, j), new Point(i, j+3), hole, 31, 0);
                  }
                  if (!foundHole) {
                     threat = new Threat(new Point(i, j), new Point(i, j+2), 3, 0);
                  }
               }
            }
            active = 0;
            count = 0;
            myCount = 0;
            doX = i;
            doY = j;
            special = false;
            hole = null;
            instruction = null;
            foundHole = false;
            if (calculated1[i][j] != true) {
               if (doX!=0 && grid[doX-1][doY]==0) {
                  instruction = new Point(doX-1, doY);
                  active++;
               }
               if (grid[doX][doY] == key) {
                  for (int k=doX; k<=(((doX+3)<14)?(doX+3):14); k++) {
                     if (grid[k][doY] == key) {
                        calculated1[k][doY] = true;
                        count++;
                     } else {
                        if (k != (((doX+3)<14)?(doX+3):14)) {
                           if (grid[k][doY] == 0) {
                              hole = new Point(k, doY);
                              foundHole = true;
                           }
                        }
                     }
                  }
               }
               if (grid[doX][doY] == myKey) {
                  for (int k=doX; k<=(((doX+3)<14)?(doX+3):14); k++) {
                     if (grid[k][doY] == myKey) {
                        calculated1[k][doY] = true;
                        myCount++;
                     }
                  }
               }
               if (doY != 15) {
                  if (count == 3) {
                     if (foundHole) {
                        if (doX+4<15 && grid[doX+4][doY] == 0) {
                           active++;
                        }
                        if (grid[(doX+4<15)?(doX+4):14][doY] == key) {
                           special = true;
                        }
                     } else {
                        if (doX+3<15 && grid[doX+3][doY] == 0) {
                           active++;
                        }
                     }
                  }
                  if (count == 4) {
                     if (doX+4<15 && grid[doX+4][doY] == 0) {
                        active++;
                     }
                  }
                  if (myCount == 4) {
                     if (doX+4<15 && grid[doX+4][doY] == 0) {
                        instruction = new Point(doX+4, doY);
                        active++;
                     }
                  }
                  if (myCount == 3) {
                     if (doX+3<15 && grid[doX+3][doY] == 0) {
                        instruction = new Point(doX+3, doY);
                        active++;
                     }
                  }
               }
               if (myCount==4 && active>=1) {
                  win = new Threat(instruction, 4);
               }
               if (myCount==3 && active==2) {
                  win = new Threat(instruction, 3);
               }
               if (count==3 && special==true) {
                  threat = new Threat(hole, 3);
               }
               if (count==3 && active==2) {
                  if (foundHole) {
                     threat = new Threat(new Point(i, j), new Point(i+3, j), hole, 31, 1);
                  }
                  if (!foundHole) {
                     threat = new Threat(new Point(i, j), new Point(i+2, j), 3, 1);
                  }
               }
               if (count==4 && active==1) {
                  threat = new Threat(new Point(i, j), new Point(i+3, j), 4, 1);
               }
            }
            active = 0;
            count = 0;
            myCount = 0;
            doX = i;
            doY = j;
            special = false;
            hole = null;
            instruction = null;
            foundHole = false;
            if (calculated2[i][j] != true) {
               if (doX!=0 && doY!=0 && grid[doX-1][doY-1]==0) {
                  instruction = new Point(doX-1, doY-1);
                  active++;
               }
               int a = (((doX+3)<14)?3:(14-doX));
               int b = (((doY+3)<14)?3:(14-doY));
               int limit = Math.min(a, b);
               if (grid[doX][doY] == key) {
                  for (int k=0; k<=limit; k++) {
                     if (grid[doX+k][doY+k] == key) {
                        calculated1[doX+k][doY+k] = true;
                        count++;
                     } else {
                        if (k != limit) {
                           if (grid[doX+k][doY+k] == 0) {
                              hole = new Point(doX+k, doY+k);
                              foundHole = true;
                           }
                        }
                     }
                  }
               }
               if (grid[doX][doY] == myKey) {
                  for (int k=0; k<=limit; k++) {
                     if (grid[doX+k][doY+k] == myKey) {
                        calculated1[doX+k][doY+k] = true;
                        myCount++;
                     }
                  }
               }
               if (doY != 15) {
                  if (count == 3) {
                     if (foundHole) {
                        if (doX+4<15 && doY+4<15 && grid[doX+4][doY+4] == 0) {
                           active++;
                        }
                        int l = (doX+4<15)?(doX+4):14;
                        int m = (doY+4<15)?(doY+4):14;
                        if (grid[l][m] == key) {
                           special = true;
                        }
                     } else {
                        if (doX+3<15 && doY+3<15 && grid[doX+3][doY+3] == 0) {
                           active++;
                        }
                     }
                  }
                  if (count==4 && doX+4<15 && doY+4<15) {
                     if (grid[doX+4][doY+4] == 0) {
                        active++;
                     }
                  }
                  if (myCount==4 && doX+4<15 && doY+4<15) {
                     if (grid[doX+4][doY+4] == 0) {
                        instruction = new Point(doX+4, doY+4);
                        active++;
                     }
                  }
                  if (myCount==3 && doX+3<15 && doY+3<15) {
                     if (grid[doX+3][doY+3]==0) {
                        instruction = new Point(doX+3, doY+3);
                        active++;
                     }
                  }
               }
               if (myCount==4 && active>=1) {
                  win = new Threat(instruction, 4);
               }
               if (myCount==3 && active==2) {
                  win = new Threat(instruction, 3);
               }
               if (count==3 && special==true) {
                  threat = new Threat(hole, 3);
               }
               if (count==3 && active==2) {
                  if (foundHole) {
                     threat = new Threat(new Point(i, j), new Point(i+3, j+3), hole, 31, 2);
                  }
                  if (!foundHole) {
                     threat = new Threat(new Point(i, j), new Point(i+2, j+2), 3, 2);
                  }
               }
               if (count==4 && active==1) {
                  threat = new Threat(new Point(i, j), new Point(i+3, j+3), 4, 2);
               }
            }
            active = 0;
            count = 0;
            myCount = 0;
            doX = i;
            doY = j;
            special = false;
            hole = null;
            instruction = null;
            foundHole = false;
            if (calculated2[i][j] != true) {
               if (doX!=0 && doY<14 && grid[doX-1][doY+1]==0) {
                  instruction = new Point(doX-1, doY+1);
                  active++;
               }
               int a = (((doX+3)<14)?3:(14-doX));
               int b = (((doY-3)>=0)?3:(doY));
               int limit = Math.min(a, b);
               if (grid[doX][doY] == key) {
                  for (int k=0; k<=limit; k++) {
                     if (grid[doX+k][doY-k] == key) {
                        calculated1[doX+k][doY-k] = true;
                        count++;
                     } else {
                        if (k != limit) {
                           if (grid[doX+k][doY-k] == 0) {
                              hole = new Point(doX+k, doY-k);
                              foundHole = true;
                           }
                        }
                     }
                  }
               }
               if (grid[doX][doY] == myKey) {
                  for (int k=0; k<=limit; k++) {
                     if (grid[doX+k][doY-k] == myKey) {
                        calculated1[doX+k][doY-k] = true;
                        myCount++;
                     }
                  }
               }
               if (doY != 15) {
                  if (count == 3) {
                     if (foundHole) {
                        if (doX+4<15 && doY-4>=0 && grid[doX+4][doY-4] == 0) {
                           active++;
                        }
                        int l = (doX+4<15)?(doX+4):14;
                        int m = (doY-4>=0)?(doY-4):0;
                        if (grid[l][m] == key) {
                           special = true;
                        }
                     } else {
                        if (doX+3<15 && doY-3>=0 && grid[doX+3][doY-3] == 0) {
                           active++;
                        }
                     }
                  }
                  if (count==4 && doX+4<15 && doY-4>=0) {
                     if (grid[doX+4][doY-4] == 0) {
                        active++;
                     }
                  }
                  if (myCount==4 && doX+4<15 && doY-4>=0) {
                     if (grid[doX+4][doY-4] == 0) {
                        instruction = new Point(doX+4, doY-4);
                        active++;
                     }
                  }
                  if (myCount==3 && doX+3<15 && doY-3>=0) {
                     if (grid[doX+3][doY-3] == 0) {
                        instruction = new Point(doX+3, doY-3);
                        active++;
                     }
                  }
               }
               if (myCount==4 && active>=1) {
                  win = new Threat(instruction, 4);
               }
               if (myCount==3 && active==2) {
                  win = new Threat(instruction, 3);
               }
               if (count==3 && special==true) {
                  win = new Threat(hole, 3);
               }
               if (count==3 && active==2) {
                  if (foundHole) {
                     win = new Threat(new Point(i, j), new Point(i+3, j-3), hole, 31, 3);
                  }
                  if (!foundHole) {
                     win = new Threat(new Point(i, j), new Point(i+2, j-2), 3, 3);
                  }
               }
               if (count==4 && active==1) {
                  win = new Threat(new Point(i, j), new Point(i+3, j-3), 4, 3);
               }
            }
         }
      }
      if (win.hasThreat && win.threatClass==4) {
         return win;
      } else if(win.hasThreat && win.threatClass==3 && threat.threatClass==3) {
         return win;
      }
      return threat;
   }

   private int search(int depth, Point start, Point end, 
                    int alpha, int beta, boolean computerTurn) {
      int best = computerTurn?-99999:99999;
      int current;
      if (depth <= 0) {
         return evaluate(start, end);
      }
      if (computerTurn) {
         for (int i=start.x; i<=end.x; i++) {
            for (int j=start.y; j<=end.y; j++) {
               if (grid[i][j] == 0) {
                  if (alpha >= beta) {
                     return alpha;
                  }
                  put(i, j, 2);
                  current = search(depth--, start, end, alpha, beta, !computerTurn);
                  take(i, j);
                  if (current > alpha) {
                     alpha = current;
                     currentPoint = new Point(i, j);
                  }
               }
            }
         }
         best = alpha;
      } else {
         for (int i=start.x; i<=end.x; i++) {
            for (int j=start.y; j<=end.y; j++) {
               if (grid[i][j] == 0) {
                  if (alpha >= beta) {
                     return beta;
                  }
                  put(i, j, 1);
                  current = search(depth--, start, end, alpha, beta, !computerTurn);
                  take(i, j);
                  if (current < beta) {
                     beta = current;
                  }
               }
            }
         }
         best = beta;
      }
      return best;
   }

   private Point eliminateThreat(int x1, int y1, int x2, int y2, int tc) {
      Point p1 = new Point(x1, y1);
      Point p2 = new Point(x2, y2);
      Point result = new Point(0, 0);
      if (tc == 3) {
         put(x1, y1, 2);
         int evaluation1 = evaluate(new Point(0, 0), new Point(14, 14));
         take(x1, y1);
         put(x2, y2, 2);
         int evaluation2 = evaluate(new Point(0, 0), new Point(14, 14));
         take(x2, y2);
         result = (evaluation1>evaluation2)?p1:p2;
      } else if (tc == 4) {
         result = (grid[x1][y1]==0)?p1:p2;
      }
      return result;
   }

   private Point eliminateThreat(int x1, int y1, int x2, int y2, 
                               int tc, Threat threat) {
      Point p1 = new Point(x1, y1);
      Point p2 = new Point(x2, y2);
      Point result = new Point(0, 0);
      if (threat.hasHole) {
         Point hole = threat.hole;
         put(x1, y1, 2);
         int evaluation1 = evaluate(new Point(0, 0), new Point(14, 14));
         take(x1, y1);
         put(x2, y2, 2);
         int evaluation2 = evaluate(new Point(0, 0), new Point(14, 14));
         take(x2, y2);
         put(hole.x, hole.y, 2);
         int evaluation3 = evaluate(new Point(0, 0), new Point(14, 14));
         take(hole.x, hole.y);
         int max = Math.max(evaluation1, Math.max(evaluation2, evaluation3));
         if (max == evaluation1) {
            return p1;
         }
         if (max == evaluation2) {
            return p2;
         }
         if (max == evaluation3) {
            return hole;
         }
      } else {
         if (tc == 3) {
            put(x1, y1, 2);
            int evaluation1 = evaluate(new Point(0, 0), new Point(14, 14));
            take(x1, y1);
            put(x2, y2, 2);
            int evaluation2 = evaluate(new Point(0, 0), new Point(14, 14));
            take(x2, y2);
            result = (evaluation1>evaluation2)?p1:p2;
         } else if (tc == 4) {
            int ox1 = x1;
            int ox2 = x2;
            if (x1 < 0) {
               x1 += 5;
            }
            if (x1 > 15) {
               x1 -= 5;
            }
            if (y1 < 0) {
               y1 += 5;
            }
            if (y1 > 15) {
               y1 += 5;
            }
            result = (grid[x1][y1]==0)?p1:p2;
            if (x1!=ox1 || x2!=ox2) {
               result = (result.equals(p1))?p2:p1;
            }
         }
      }
      return result;
   }

   private boolean checkPoint(int x, int y) {
      int countJoint = 0;
      if (x != 0) {
         if (grid[x-1][y] != 0) {
            countJoint++;
         }
      }
      if (x != 14) {
         if (grid[x+1][y] != 0) {
            countJoint++;
         }
      }
      if (y != 0) {
         if (grid[x][y-1] != 0) {
            countJoint++;
         }
      }
      if (y != 14) {
         if (grid[x][y+1] != 0) {
            countJoint++;
         }
      }
      if (x!=0 && y!=0) {
         if (grid[x-1][y-1] != 0) {
            countJoint++;
         }
      }
      if (x!=14 && y!=14) {
         if (grid[x+1][y+1] != 0) {
            countJoint++;
         }
      }
      if (x!=0 && y!=14) {
         if (grid[x-1][y+1] != 0) {
            countJoint++;
         }
      }
      if (x!=14 && y!=0) {
         if (grid[x+1][y-1] != 0) {
            countJoint++;
         }
      }
      if (x > 1) {
         if (grid[x-2][y] != 0) {
            countJoint++;
         }
      }
      if (x < 13) {
         if (grid[x+2][y] != 0) {
            countJoint++;
         }
      }
      if (y > 1) {
         if (grid[x][y-2] != 0) {
            countJoint++;
         }
      }
      if (y < 13) {
         if (grid[x][y+2] != 0) {
            countJoint++;
         }
      }
      if (x>1 && y>1) {
         if (grid[x-2][y-2] != 0) {
            countJoint++;
         }
      }
      if (x<13 && y<13) {
         if (grid[x+2][y+2] != 0) {
            countJoint++;
         }
      }
      if (x>1 && y<13) {
         if (grid[x-2][y+2] != 0) {
            countJoint++;
         }
      }
      if (x<13 && y>1) {
         if (grid[x+2][y-2] != 0) {
            countJoint++;
         }
      }
      if (x>1 && y>0) {
         if (grid[x-2][y-1] != 0) {
            countJoint++;
         }
      }
      if (x>1 && y<14) {
         if (grid[x-2][y+1] != 0) {
            countJoint++;
         }
      }
      if (x<13 && y>0) {
         if (grid[x+2][y-1] != 0) {
            countJoint++;
         }
      }
      if (x<13 && y<14) {
         if (grid[x+2][y+1] != 0) {
            countJoint++;
         }
      }
      if (x>0 && y>1) {
         if (grid[x-1][y-2] != 0) {
            countJoint++;
         }
      }
      if (x>0 && y<13) {
         if (grid[x-1][y+2] != 0) {
            countJoint++;
         }
      }
      if (x<14 && y>1) {
         if (grid[x+1][y-2] != 0) {
            countJoint++;
         }
      }
      if (x<14 && y<13) {
         if (grid[x+1][y+2] != 0) {
            countJoint++;
         }
      }
      if (countJoint == 0) {
         return false;
      }
      return true;
   }

   /**
    * Generate a move based on the current situation by the computer.
    *
    * @param player The number representation of the computer.
    * @return The move generated by the computer.
    */
   public Point place(int player) {
      boolean firstFound = false;
      Threat threat = findThreat();
      if (threat.instructed) {
         put(threat.instruction.x, threat.instruction.y, player);
         return threat.instruction;
      }
      if (threat.hasThreat) {
         int x1 = threat.start.x;
         int y1 = threat.start.y;
         int x2 = threat.end.x;
         int y2 = threat.end.y;
         int tc = threat.threatClass;
         System.out.println(threat.start+" "+threat.end);
         switch (threat.direction) {
            case 0: currentPoint = eliminateThreat(x1, y1-1, x2, y2+1, tc, threat);
                    break;
            case 1: currentPoint = eliminateThreat(x1-1, y1, x2+1, y2, tc, threat);
                    break;
            case 2: currentPoint = eliminateThreat(x1-1, y1-1, x2+1, y2+1, tc, threat);
                    break;
            case 3: currentPoint = eliminateThreat(x1-1, y1+1, x2+1, y2-1, tc, threat);
                    break;
         }
      } else {
         int countPoint = 0;
         int[][] newGrid = new int[15][15];
         Point start = new Point(0, 0);
         Point end = new Point(14, 14);
         for (int i=0; i<15; i++) {
            for (int j=0; j<15; j++) {
               if (grid[i][j] == 0) {
                  newGrid[i][j] = 0;
               } else {
                  if (isFirst) {
                     System.out.println(i+" "+j);
                     first = new Point(i, j);
                  }
                  if (checkPoint(i, j)) {
                     newGrid[i][j] = grid[i][j];
                     countPoint++;
                  } else {
                     newGrid[i][j] = 0;
                  }
               }
            }
         }
         if (isFirst) {
            isFirst = false;
            int x = (int)(Math.random()*8);
            switch (x) {
               case 0: return put(first.x-1, first.y-1);
               case 1: return put(first.x-1, first.y);
               case 2: return put(first.x-1, first.y+1);
               case 3: return put(first.x, first.y-1);
               case 4: return put(first.x, first.y+1);
               case 5: return put(first.x+1, first.y-1);
               case 6: return put(first.x+1, first.y);
               case 7: return put(first.x+1, first.y+1);
               default: return put(first.x-1, first.y);
            }
         }
         for (int i=0; i<15; i++) {
            for (int j=0; j<15; j++) {
               if (newGrid[i][j]!=0 && !firstFound) {
                  start.x = i;
                  start.y = j;
                  firstFound = !firstFound;
               }
               if (firstFound && newGrid[i][j]!=0) {
                  end.x = i;
                  end.y = j;
               }
            }
         }
         int depth = 4;
         start.x = (start.x-depth/2>=0)?(start.x-depth/2):0;
         start.y = (start.y-depth/2>=0)?(start.y-depth/2):0;
         end.x = (end.x+depth/2<15)?(end.x+depth/2):14;
         end.y = (end.y+depth/2<15)?(end.y+depth/2):14;
         int size = (end.x-start.x+1)*(end.y-start.y+1);
         System.out.println("---->SIZE: "+size);
         if (size > 25) {
            int utilLevel = 10*countPoint/size;
            System.out.println("---->UTIL: "+utilLevel);
            switch (utilLevel) {
               case 0: case 1: case 2:
                  depth = 2;
                  break;
               case 3: case 4:
                  depth = 3;
                  break;
               case 5: case 6: case 7:
                  depth = 4;
                  break;
               case 8: case 9:
                  depth = 6;
                  break;
               default:
                  depth = 4;
                  break;
            }
         } else if (size <= 16) {
            depth = 6;
         }
         System.out.println("GRID SEARCHED: "+start+" "+end);
         System.out.println("EVALUATION "+search(depth, start, end, -99999, 99999, true));
      }
      System.out.println("------------------------------------");
      put(currentPoint.x, currentPoint.y, player);
      return currentPoint;
   }

}
