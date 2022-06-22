/*
Homework 6 : BattleShip
File Name : Cell.java
 */
package battleship;

import java.io.Serializable;
import static battleship.Battleship.board;
import static battleship.Ship.SUNK_MESSAGE;

/**
 * A single spot on the Battleship game board.
 * A cell knows if there is a ship on it, and it remember
 * if it has been hit.
 *
 * @author Meenu Gigi, mg2578@rit.edu
 * @author Vedika Vishwanath Painjane, vp2312@rit.edu
 */
public class Cell implements Serializable {

    /** Character to display for a ship that has been entirely sunk */
    public static final char SUNK_SHIP_SECTION = '*';

    /** Character to display for a ship that has been hit but not sunk */
    public static final char HIT_SHIP_SECTION = '☐';

    /** Character to display for a water cell that has been hit */
    public static final char HIT_WATER = '.';

    /**
     * Character to display for a water cell that has not been hit.
     * This character is also used for an unhit ship segment.
     */
    public static final char PRISTINE_WATER = '_';

    /**
     * Character to display for a ship section that has not been
     * sunk, when revealing the hidden locations of ships
     */
    public static final char HIDDEN_SHIP_SECTION = 'S';
    //the cell's row position
    private final int row;
    //the cell's column position
    private final int column;
    // Ship Object
    Ship newShip;
    // The cell state
    char cellState;

    /**
     * Create a new cell.
     * @param row the cell's row position
     * @param column the cell's column position
     */
    Cell(int row, int column){
        this.row = row;
        this.column = column;
        cellState = PRISTINE_WATER;
    }

    /**
     * Place a ship on this cell. Of course, ships typically cover more than
     * one Cell, so the same ship will usually be passed to more than one
     * Cell's putShip method.
     * @param ship      the ship that is to be on this Cell
     * @throws OverlapException - if there is already a ship here.
     */
    public void putShip(Ship ship) throws OverlapException{
        try {
//            put ship if no ship exists in cell
            if(this.newShip == null){
                this.newShip = ship;
                cellState = HIDDEN_SHIP_SECTION;
            }
            else {
                throw new OverlapException(row, column);
            }
        } catch (OverlapException e) {
            System.out.println(e);
            System.exit(0);
        }


    }

    /**
     * Simulate hitting this cell. If there is a ship here, it will be hit.
     * Calling this method changes the status of the cell, as reflected by
     * displayChar() and displayHitStatus().
     * @throws CellPlayedException if this cell had already been hit
     */
    public void hit() throws CellPlayedException{
        try{
//            hit ship if ship has not sunk
            if(cellState == HIDDEN_SHIP_SECTION){
                newShip.hit();
//                if ship has sunk, remove from list of ships
//                update cell state
                if(newShip.isSunk()){
                    System.out.println(SUNK_MESSAGE);
                    board.shipList.remove(0);
                    cellState = SUNK_SHIP_SECTION;
                }
                else {
                    cellState = HIT_SHIP_SECTION;
                }
            }
//            update cell state on hitting pristine water cell
            else if(cellState == PRISTINE_WATER){
                cellState = HIT_WATER;
            }
            else{
                throw new CellPlayedException(row,column);
            }
        } catch (CellPlayedException e) {
            System.out.println(e);;
        }
    }

    /**
     * Return a character representing the state of this Cell but without
     * revealing unhit portions of ships. Unhit portions of ships appear as
     * PRISTINE_WATER.
     * @return one of the characters declared as a constant static field in
     * this class, according to the state of the cell and the state of the
     * ship upon it, if any.
     */
    public char displayHitStatus(){
        if(cellState == HIDDEN_SHIP_SECTION){
            return PRISTINE_WATER;
        }
        return cellState;
    }

    /**
     * Return a character representing the state of this Cell. This display
     * method reveals all.
     * @return one of the characters declared as a constant static field in
     * this class, according to the state of the cell and the state of the
     * ship upon it, if any.
     */
    public char displayChar(){
        return cellState;
    }

}
