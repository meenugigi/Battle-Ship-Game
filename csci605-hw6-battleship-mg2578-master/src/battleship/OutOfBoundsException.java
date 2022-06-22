/*
Homework 6 : BattleShip
File Name : OutOfBoundsException.java
 */
package battleship;

/**
 * A BattleshipException that informs the program that it attempted to place a
 * ship outside the bounds of the board
 *
 * @author Meenu Gigi, mg2578@rit.edu
 * @author Vedika Vishwanath Painjane, vp2312@rit.edu
 */
public class OutOfBoundsException extends BattleshipException{
    /**
     * Descriptive error message to display for this exception
     */
    static final String PAST_EDGE = "Coordinates are past board edge";

    /**
     * The constructor stores the illegal coordinates where the violation
     * occurred and sets the error message to PAST_EDGE.
     * @param row the row number of the bad coordinates
     * @param column the column number of the bad coordinates
     */
    public OutOfBoundsException(int row, int column){
        super(row, column, PAST_EDGE);
    }

}
