/*
Homework 6 : BattleShip
File Name : OverlapException.java
 */
package battleship;

/**
 * A BattleshipException that informs the program that it attempted to place a
 * ship where there is already another ship
 *
 * @author Meenu Gigi, mg2578@rit.edu
 * @author Vedika Vishwanath Painjane, vp2312@rit.edu
 */
public class OverlapException extends BattleshipException{
    /**
     * Descriptive error message to display for this exception
     */
    static final String OVERLAP = "Ships placed in overlapping positions";

    /**
     * The constructor stores the coordinates of intersection and sets the
     * error message to OVERLAP.
     * @param row the row number of the bad coordinates
     * @param column the column number of the bad coordinates
     */
    public OverlapException(int row, int column){
        super(row, column, OVERLAP);

    }
}
