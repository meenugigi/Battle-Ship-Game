/*
Homework 6 : BattleShip
File Name : Battleship.java
 */
package battleship;

import java.io.*;
import static java.lang.System.out;

/**
 *  The main class that runs the game
 *
 * @author Meenu Gigi, mg2578@rit.edu
 * @author Vedika Vishwanath Painjane, vp2312@rit.edu
 */
public class Battleship {

    /** At end of game */
    static final String ALL_SHIPS_SUNK = "All ships sunk!";
    /**
     * For player commands
     */
    static final String BAD_ARG_COUNT = "Wrong number of arguments for command";
    /**
     * For when the config file has too large a dimension in it
     */
    static final String DIM_TOO_BIG = "Wrong number of arguments for command";
    /**
     * the hit command
     */
    static final String HIT = "h";
    /**
     * We don't allow boards larger than this value, height or width.
     */
    static final int MAX_DIM = 20;
    /**
     * What to display when the program is ready for a user command
     */
    static final String PROMPT = "> ";
    /**
     * the quit command
     */
    static final String QUIT = "q";
    /**
     * the reveal command
     */
    static final String REVEAL = "!";
    /**
     * the help command
     */
    static final String HELP = "help";
    /**
     * the save command
     */
    static final String SAVE = "s";
    /**
     * The regular expression to use with String.split(String)
     */
    static final String WHITESPACE = "\\s+";
    /**
     * the input file name
     */
    private static String fileName;
    /**
     * instance of Battleship
     */
    static Battleship battleship;
    /**
     * instance of board
     */
    static Board board;

    /**
     * Construct a battlefield game.
     * @param filename The setup file
     * @throws battleship.BattleshipException - If the init configuration file
     * is incorrect
     */
    public Battleship(String filename) throws battleship.BattleshipException{
        this.fileName = filename;
    }

    /**
     * Read the setup file and build all data structures needed later.
     * The setup file is either a text file describing the initial state of a
     * new game
     * or ObjectStream file that contains a saved game.
     * @param args  - one element: the name of the setup file
     * @throws BattleshipException
     */
    public static void main(String[] args) throws BattleshipException{
        battleship = new Battleship(fileName);
        if(args.length == 1){
            fileName = args[0];
            File file = new File("input/"+fileName);
//            checks if file exists
            if(!file.exists()){
                out.println("File does not exist!");
                System.exit(0);
            }
            String absolute = file.getAbsolutePath();
//            attempt to open file as Object
            battleship.openfileAsObject(absolute);
        }
    }

    /**
     * Opening saved game
     * @param absolute file name
     * @throws BattleshipException
     */
    private void openfileAsObject(String absolute) throws BattleshipException{

        try(ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream(absolute))){
            out.println("Checking if " + fileName + " is a saved game file... yes");
            out.println();
//            read byte stream and type cast to Board class
            board = (Board) objectInputStream.readObject();
            board.display(out);
//            continue playing saved game
            battleship.play();
        }
        catch(ClassNotFoundException | OutOfBoundsException | IOException
                | CellPlayedException streamCorruptedException){
            out.println("Checking if data/" + fileName + " is a saved game file... no; " +
                    "will read as a text setup file.");
            out.println();
//            open file using reader.
            openFileWithReader(absolute);
        }
    }

    /**
     * Opens new input configuration file
     * @param absolute file name
     */
    private void openFileWithReader(String absolute) {
        try(BufferedReader bufferedReader = new BufferedReader(
                new FileReader(absolute))) {
//            reads first line of configuration file
            String line = bufferedReader.readLine();
            String [] tokens = line.split(" ");

            int height = Integer.parseInt(tokens[0]);
            int width = Integer.parseInt(tokens[1]);
            board = new Board(height, width);
//            check if board dimensions exceed maximum dimensions.
//            if yes, terminate program
            if(height > MAX_DIM || width > MAX_DIM){
                out.println(DIM_TOO_BIG);
                System.exit(0);
            }
            board.putCellsInBoard();
            board.display(out);
            out.println();
//            reads Ship related data from configuration file
            readShipData(bufferedReader);
            battleship.play();
        }
        catch (IOException | OverlapException | OutOfBoundsException |
                CellPlayedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads Ship related data from configuration file
     * @param bufferedReader To read file
     * @throws IOException
     * @throws OverlapException
     * @throws OutOfBoundsException
     * @throws CellPlayedException
     */
    private void readShipData(BufferedReader bufferedReader)
            throws IOException, OverlapException, OutOfBoundsException,
            CellPlayedException {
        String line = bufferedReader.readLine();
//        iterate till all data has been read
        while(line != null){
            String [] shipData = line.split(" ");
            int shipRow = Integer.parseInt(shipData[0]);
            int shipCol = Integer.parseInt((shipData[1]));
            Orientation shipOrientation = Orientation.valueOf(shipData[2]);
            int shipLength = Integer.parseInt(shipData[3]);
//            create a new ship
            Ship newShip = new Ship(board, shipRow, shipCol, shipOrientation,
                    shipLength);
//            adds ship to board
            board.addShip(newShip);
            line = bufferedReader.readLine();
        }
    }

    /**
     * Play game until all ships are sunk.
     * @throws OutOfBoundsException
     * @throws IOException
     * @throws CellPlayedException
     */
    public void play() throws OutOfBoundsException, IOException,
            CellPlayedException {
            InputStreamReader inStream = new InputStreamReader(System.in);
            BufferedReader stdin = new BufferedReader(inStream);
            String input;
//            continue playing till all ships are sunk
            while (!board.allSunk()){
//                take and read user input
                out.print(PROMPT);
                input = stdin.readLine();
                String[] userInput = input.split(WHITESPACE);
//                check for incorrect arguments
                if(userInput.length == 0){
                    out.println("Usage: java Battleship setup-file");
                    System.exit(0);
                }
                else {
                    checkInput(userInput);
                }
            }
            out.println(ALL_SHIPS_SUNK);
            stdin.close();
            inStream.close();
            System.exit(0);
    }

    /**
     * Checks user input
     * @param userInput Input from command line
     * @throws OutOfBoundsException
     * @throws IOException
     * @throws CellPlayedException
     */
    private void checkInput(String[] userInput)
            throws OutOfBoundsException, IOException, CellPlayedException {
        if(userInput.length == 3){
            takeUserInput(userInput);
        }
        else if(userInput.length == 1){
            additionalCommands(userInput);
        }
        else if(userInput.length == 2){
            userSaveCommand(userInput);
        }
        else if(userInput.length > 3){
            out.println(BAD_ARG_COUNT);
        }
    }

    /**
     * SAVE command method
     * @param userInput User Input
     */
    private void userSaveCommand(String [] userInput) {
        if(userInput[0].equals(SAVE)){
            out.println(userInput[1]);
            saveGame(userInput[1]);
        }
        else {
            out.println(BAD_ARG_COUNT);
        }
    }

    /**
     * Additional commands method
     * @param userInput Input by user
     */
    private void additionalCommands(String [] userInput) {
        if(userInput[0].equals(QUIT)){
            System.exit(0);
        }
        else if(userInput[0].equals(REVEAL)){
            board.fullDisplay(out);
        }
        else if(userInput[0].equals(HELP)){
            displayCommands();
        }
    }

    /**
     * Takes user input for hit positions
     * @param userInput Input by user
     * @throws OutOfBoundsException
     * @throws IOException
     * @throws CellPlayedException
     */
    private void takeUserInput(String [] userInput)
            throws OutOfBoundsException, IOException, CellPlayedException {
        int hitRow, hitCol;
        if(userInput[0].equals(HIT)){
            hitRow = Integer.parseInt(userInput[1]);
            hitCol = Integer.parseInt(userInput[2]);
//            hit locations provided by user
            board.getCell(hitRow,hitCol).hit();
            out.println();
            board.display(out);
            out.println();
        }
    }

    /**
     * This method saves the game state
     * @param outputFile Output file name
     */
    private void saveGame(String outputFile){
        try{
//            write byte stream in provided location
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("input/"+outputFile));
            out.writeObject(board);
        }
        catch (FileNotFoundException e){
            out.println(e);
        }
        catch (IOException e){
            out.println(e);
        }
        finally {
            out.close();
        }
    }

    /**
     * Displays commands
     */
    private void displayCommands(){
        out.println("help - Display a list of all commands available.\n" +
                "h row column - Hit a cell.\n" +
                "s file - Save game state to file. (Serialization process)\n"+
                "! - Reveal all ship locations.\n" +
                "q - Quit game.");
    }
}
