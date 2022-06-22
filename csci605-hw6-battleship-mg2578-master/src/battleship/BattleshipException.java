/*
Homework 6 : BattleShip
File Name : BattleshipException.java
 */
package battleship;

/**
 *  A class to represent different violations of the game' rules.
 *
 * @author Meenu Gigi, mg2578@rit.edu
 * @author Vedika Vishwanath Painjane, vp2312@rit.edu
 */
public class BattleshipException extends Exception {

    /** Unset default value for coordinates */
    public static final int UNSET = -1;

    /** the row number of the cell's coordinates. */
    private final int row;

    /** the column number of the cell's coordinates */
    private final int column;

    /**
     * The constructor stores the coordinates where the violation occurred and
     * sets the given error message.
     * @param row       the row number of the cell's coordinates.
     * @param column    the column number of the cell's coordinates
     * @param message   the row number of the cell's coordinates
     */
    public BattleshipException( int row, int column, String message ) {
        super( message + ", row=" + row + ", column=" + column );
        this.row = row;
        this.column = column;
    }


    /**
     * The constructor stores the coordinates where the violation occurred and
     * sets the given error message.
     * @param msg
     */
    public BattleshipException(String msg){
        super(msg + ", row=" + UNSET + ", column=" + UNSET );
        this.row = UNSET;
        this.column = UNSET;

    }
}
