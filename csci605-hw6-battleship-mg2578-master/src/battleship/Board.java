/*
Homework 6 : BattleShip
File Name : Board.java
 */
package battleship;


import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static battleship.Battleship.*;

/**
 * The class to represent the grid of cells (squares). A collection of ships
 * is also kept so the Board can be asked if the game is over. The class is
 * Serializable so that its instance can be saved to a file in binary form
 * using an ObjectOutputStream and restored with an ObjectInputStream. Because
 * the object holds references to all other objects in the system, no other
 * objects need to be separately saved.
 *
 * @author Meenu Gigi, mg2578@rit.edu
 * @author Vedika Vishwanath Painjane, vp2312@rit.edu
 */
public class Board implements Serializable {
    /**
     * number of cells down
     */
    private final int height;
    /**
     * number of cells across
     */
    private final int width;
    // Cell Matrix
    Cell [][] cellToken;
    // Arraylist to store ships
    List<Ship> shipList = new ArrayList<>();

    /**
     * Construct a board.
     * @param height number of cells down
     * @param width number of cells across
     */
    Board(int height, int width){
        this.height = height;
        this.width = width;
    }

    /**
     * Used for input error checking.
     * @return the number of rows in the board
     */
    public int getHeight(){
        return height;
    }

    /**
     * Used for error checking.
     * @return the number of columns in the board
     */
    public int getWidth(){
        return width;
    }

    /**
     * Fetch the Cell object at the given location.
     * @param row       row number (0-based)
     * @param column    column number (0-based)
     * @return the Cell created for this position on the board
     * @throws OutOfBoundsException  if either coordinate is negative or too
     * high
     * @throws IOException Input Output Exception
     * @throws CellPlayedException if this cell had already been hit
     */
    public Cell getCell(int row, int column) throws OutOfBoundsException,
            IOException, CellPlayedException {
//        check if cell dimensions exceed board dimensions
        if(row >= getHeight() || column >= getWidth()){
            try{
                throw new OutOfBoundsException(row, column);
            } catch (OutOfBoundsException e) {
                System.out.println(e);
//                play game
                battleship.play();
            }
        }
        return cellToken[row][column];
    }

    /**
     * Creates a new cell
     * Puts cell in the board
     */
    public void putCellsInBoard(){
        cellToken = new Cell[height][width];
        for(int i=0; i<getHeight(); i++){
            for(int j =0; j<getWidth(); j++){
                this.cellToken[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * Useful for debugging. This is not the method that displays the board to
     * the user.
     * @return a one-line (hopefully) description of the board
     */
    @Override
    public String toString() {
        return "Board{" +
                "height=" + height +
                ", width=" + width +
                '}';
    }

    /**
     * Display the board in character form to the user. Cells' display
     * characters are described in Cell. Output is double-spaced in both
     * dimensions. The numbers of the columns appear above the first row, and
     * the numbers of each row appears to the left of the row.
     * @param out       the output stream to which the display should be sent
     */
    public void display(PrintStream out){
        int rowIndex =0;
        out.print(" ");
//        print column index
        for(int colIndex = 0; colIndex < board.getWidth(); colIndex++){
            out.print(" "+ colIndex);
        }
        out.println();
//        display each cell data
        for(Cell[] cellColumn : cellToken){
            out.print(rowIndex);
            for(Cell cellRow : cellColumn){
                if(cellRow.newShip != null){
//                    update cell state if ship has sunk
                    if(cellRow.newShip.isSunk() == true){
                        cellRow.cellState = Cell.SUNK_SHIP_SECTION;
                    }
                }
                out.print(" " +String.valueOf(cellRow.displayHitStatus()));
            }
            out.println();
            rowIndex++;
        }
    }

    /**
     * This is the "cheating" form of the display because the user can see
     * where the unsunk parts of ships are. Cells' display characters are
     * described in Cell. Output is double-spaced in both dimensions. The
     * numbers of the columns appear above the first row, and the numbers of
     * each row appears to the left of the row.
     * @param out  the output stream to which the display should be sent
     */
    public void fullDisplay(PrintStream out){
        int rowIndex =0;
        out.print(" ");
//        print column index
        for(int colIndex = 0; colIndex < board.getWidth(); colIndex++){
            out.print(" "+ colIndex);
        }
        out.println();
//        print data for each cell
        for(Cell[] cellColumn : cellToken) {
            out.print(rowIndex);
            for (Cell cellRow : cellColumn) {
                out.print(" " + String.valueOf(cellRow.displayChar()));
            }
            out.println();
            rowIndex++;
        }
    }

    /**
     * Add a ship to the board. The only current reason that the board needs
     * direct access to the ships is to poll them to see if they are all sunk
     * and the game is over.
     * @param ship  the as-yet un-added ship
     */
    public void addShip(Ship ship){
        shipList.add(ship);
    }

    /**
     * Add ship to the Board
     * @param row Row Number
     * @param column Column Number
     * @param cell Cell
     */
    public void addShipToBoard(int row, int column, Cell cell){
        this.cellToken[row][column] = cell;
    }

    /**
     * Is the game over?
     * @return true if all ships report being sunk
     */
    public boolean allSunk(){
        if(shipList.size() != 0){
            return false;
        }
        return true;
    }
}
