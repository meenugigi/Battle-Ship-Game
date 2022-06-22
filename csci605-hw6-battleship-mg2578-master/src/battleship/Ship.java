/*
Homework 6 : BattleShip
File Name : Ship.java
 */
package battleship;

import java.io.IOException;
import java.io.Serializable;
import static java.lang.System.out;

/**
 * A single ship in a Battleship game
 *
 * @author Meenu Gigi, mg2578@rit.edu
 * @author Vedika Vishwanath Painjane, vp2312@rit.edu
 */
public class Ship implements Serializable {
    /**
     * Message to display if ship has been sunk
     */
    public static final String SUNK_MESSAGE = "A battleship has been sunk!";
    //Board
    private final Board board;
    //the uppermost row that the ship is on
    private final int uRow;
    // the leftmost column that the ship is on
    private final int lCol;
    // the ship's orientation
    private final Orientation ort;
    //how many cells the ship is on
    private final int length;
    // Length of the ship which was hit
    private int hitLength = 0;


    /**
     * Initialize this new ship's state. Tell the Board object and each
     * involved Cell object about the existence of this ship by trying to put
     * the ship at each applicable Cell.
     * @param board holds a collection of ships
     * @param uRow the uppermost row that the ship is on
     * @param lCol the leftmost column that the ship is on
     * @param ort the ship's orientation
     * @param length how many cells the ship is on
     * @throws OverlapException if this ship would overlap another one that
     * already exists
     * @throws OutOfBoundsException if this ship would extend beyond the board
     * @throws IOException Input output exception
     * @throws CellPlayedException if this cell had already been hit
     */
    Ship(Board board, int uRow, int lCol, Orientation ort, int length)
            throws OverlapException, OutOfBoundsException, IOException,
            CellPlayedException {
        this.board = board;
        this.uRow = uRow;
        this.lCol = lCol;
        this.ort = ort;
        this.length = length;
        placeShip(board, uRow, lCol, ort, length);
    }

    /**
     * Place the ship in all the cells it occupies
     * @param board Board
     * @param newRow The row number
     * @param newCol The column number
     * @param shipOrientation The ship's orientation
     * @param newLength The length of the ship
     * @throws OutOfBoundsException if this ship would extend beyond the board
     * @throws IOException Input Output exception
     * @throws CellPlayedException if this cell had already been hit
     * @throws OverlapException if this ship would overlap another one that
     * already exists
     */
    private void placeShip(Board board, int newRow, int newCol,
                           Orientation shipOrientation, int newLength)
            throws OutOfBoundsException, IOException, CellPlayedException,
            OverlapException {
        Cell cell;
        while(newLength != 0 ){
//            check if ship locations are within board dimensions
            if(newRow >= board.getHeight() || newCol >= board.getWidth()){
                try {
                    throw new OutOfBoundsException(newRow, newCol);
                } catch (Exception exception) {
                    out.println(exception);
                    System.exit(0);
                }
            }
            cell = board.getCell(newRow, newCol);
            cell.putShip(this);
            board.addShipToBoard(newRow, newCol, cell);
//            update adjacent cells as per ship orientation and ship length
            newRow += shipOrientation.rDelta;
            newCol += shipOrientation.cDelta;
            newLength--;
        }
    }

    /**
     * A Cell object has been hit and tells this ship that is sitting on it
     * that the cell has been hit. If this ship has been hit as many times as
     * it is long, the SUNK_MESSAGE is displayed.
     */
    public void hit(){
        hitLength++;
    }

    /**
     * Is this ship already sunk?
     * @return true if the number of calls to hit() is the same as the ship is
     * long
     */
    public boolean isSunk(){
        if(hitLength == length){
            return true;
        }
        return false;
    }

}
