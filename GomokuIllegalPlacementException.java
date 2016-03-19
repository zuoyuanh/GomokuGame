/**
 * GomokuIllegalPlacementException - An exception for wrong placement
 *
 * @author Zuoyuan Huang
 * @version 03/03/2016 - Initial version developed
 */

public class GomokuIllegalPlacementException extends RuntimeException {

   /**
    * Construct an empty GomokuIllegalPlacementException
    */
   public GomokuIllegalPlacementException() {
      super();
   }

   /**
    * Construct a GomokuIllegalPlacementException with message
    *
    * @param msg The destinated message
    */
   public GomokuIllegalPlacementException(String msg) {
      super(msg);
   }

}
