/*
Homework 6 : BattleShip
File Name : CellPlayedException.java
 */
package battleship;

/**
 * A BattleshipException that informs the program that it attempted to "hit"
 * the same Cell instance more than once
 *
 * @author Meenu Gigi, mg2578@rit.edu
 * @author Vedika Vishwanath Painjane, vp2312@rit.edu
 */
public class CellPlayedException extends BattleshipException{
    /**
     * Descriptive error message to display for this exception
     */
    public static final String ALREADY_HIT = "This cell has already been hit";
    // the column number of the cell's coordinates
    private final int column;
    // the row number of the cell's coordinates
    private final int row;

    /**
     * The constructor stores the coordinates where the violation occurred and
     * sets the error message to ALREADY_HIT.
     * @param row the row number of the cell's coordinates
     * @param column the column number of the cell's coordinates
     */
    public CellPlayedException(int row, int column){
        super(ALREADY_HIT);
        this.row = row;
        this.column = column;
    }


}
