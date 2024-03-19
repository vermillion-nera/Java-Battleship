import java.util.ArrayList;

public class Board extends GamePiece {
    // A board's grid's rows should be labeled as LETTERS
    // Its columns should be labeled as NUMBERS
    Cell[][] grid;
    ArrayList<Ship> ships = new ArrayList<>();

    public Board(){
        grid = new Cell[10][10];

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    // Returns true if the action was successful; returns false otherwise
    public boolean addShip( int length, String location, boolean horizontal){
        // Check if location input is a valid coordinate
        int[] coord = parseLocation(location);
        if(isInvalidCoordinate(coord)){
            return false;
        } else {
            assert coord != null;
            if (coord[0] + (length * booleanToInt(!horizontal)) > grid.length || coord[1] + (length * booleanToInt(horizontal)) > grid[0].length){
                System.out.println("The ship will extend past the boundaries of the board.");
                return false;
            }
        }


        // Check if all the cells the ship would occupy are vacant
        for(int i = 0; i < length; i++){
            Cell cell = grid[coord[0] + (i * booleanToInt(!horizontal))][coord[1] + (i * booleanToInt(horizontal))];
            if(cell.hasShip()){
                System.out.println("Coordinate " + location + " is already occupied by a ship!");
                return false;
            }
        }

        Ship ship = new Ship(length);
        ships.add(ship);
        // Place the ship pieces!
        for(int i = 0; i < ship.getLength(); i++){
            Cell cell = grid[coord[0] + (i * booleanToInt(!horizontal))][coord[1] + (i * booleanToInt(horizontal))];
            if(!cell.placeShip(ship)){
                return false;
            }
        }

        return true;
    }

    // Takes a Battleship coordinate and attempts to make a hit on the specified grid cell.
    // Returns true if the action was successful; returns false otherwise.
    public boolean strikeBoard(String location){
        int[] coord = parseLocation(location);
        if(isInvalidCoordinate(coord)){
           return false;
        }

        assert coord != null;
        Cell c = grid[coord[0]][coord[1]];
        if(c.setStruck()){ // If the strike is successful
            if(c.getShip() != null) { // Check if there is a ship on that cell
                if (c.getShip().getSurvivingUnits() <= 0) { // If that cell contained the last surviving unit of the ship
                    ships.remove(c.getShip()); // Sink the battleship.
                    System.out.println("A battleship sank!");
                }
            }
            return true;
        }
        return false;
    }

    // Crucial for determining the winner!
    public int shipUnitsRemaining(){
        int output = 0;
        for(Ship ship : ships){
            output += ship.getSurvivingUnits();
        }
        return output;
    }

    public Cell getCell(String location){
        int[] coord = parseLocation(location);
        if(isInvalidCoordinate(coord)){
            return null;
        }

        assert coord != null;
        return grid[coord[0]][coord[1]];
    }

    // Takes a string and ensures that it is in the format of a valid Battleship coordinate that is within the Board's boundaries.
    public boolean isValidCoordinate(String location){
        return !isInvalidCoordinate(location);
    }

    public boolean isInvalidCoordinate(String location){
        int[] coord = parseLocation(location);
        return isInvalidCoordinate(coord);
    }

    public boolean isInvalidCoordinate(int[] coord){
        // Check if location input is a valid coordinate
        if(coord == null){
            System.out.println("Please enter a valid coordinate.");
            return true;
        }

        // Check if the strike is actually in bounds
        if(coord[0] >= grid.length || coord[1] >= grid[0].length || coord[0] < 0 || coord[1] < 0){
            System.out.println("Coordinate " + coordinateToString(coord) + " is out of bounds of the board.");
            return true;
        }

        return false;
    }

    @Override
    public String toString(){
        return ("Board with " + ships.size() + " ships and " + shipUnitsRemaining() + " ship cells remaining.");
    }

    // Returns a String that represents a single row of the board's contents.
    // Extremely useful for printing two boards adjacent to each other (see generateView());
    // Setting shipsHidden to true will hide all ship units that have not yet been struck.
    private String rowToString(int row, boolean shipsHidden){
        StringBuilder output = new StringBuilder();
        if(row == 0){
            // Number labels at the top of each column
            output.append("- ");
            for (int i = 0; i < grid.length; i++){
                output.append((i + 1)).append(" ");
            }
        } else if (row <= grid.length) {
            // Letter labels on the left side of each row
            output.append(((char) (row + 64))).append(" ");
            // Row contents
            for (int i = 0; i < grid.length; i++) {
                if(shipsHidden){
                    output.append(grid[row - 1][i].getContentsHidden());
                } else {
                    output.append(grid[row - 1][i].getContents());
                }
            }
        }

        return output.toString();
    }

    // Returns a String that can be printed to show a visual representation of the board's contents, labels included.
    // Setting shipsHidden to true will hide all ship units that have not yet been struck.
    public String boardToString(boolean shipsHidden){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i <= grid.length; i++){
            output.append(rowToString(i, shipsHidden));
            output.append("\n");
        }

        return output.toString();
    }

    // This function generates a string that can be printed to show the current state of the game
    // with `this` being the player's board and `other` being the opponent's board.
    // Surviving ship units are hidden on the opponent's board.
    // ------------------------------------------------------------------------------------------
    // WARNING: While most other code was written to scale with different board sizes,
    // this function designed to work specifically with 2 10*10 boards.
    public String generateView(Board other){
        StringBuilder output = new StringBuilder();
        output.append("\t  YOUR BOARD\t\t\t|\t\t\tOPPONENT'S BOARD\t\t\n");
        for(int i = 0; i <= grid.length; i++){
            output.append(rowToString(i, false));
            output.append("\t\t|\t\t");
            output.append(other.rowToString(i, true));
            output.append("\n");
        }

        return output.toString();
    }

    public String generateGameOverView(Board other){
        StringBuilder output = new StringBuilder();
        output.append("\t\tPlayer 1\t\t\t|\t\t\t\tPlayer 2\n");
        for(int i = 0; i <= grid.length; i++){
            output.append(rowToString(i, false));
            output.append("\t\t|\t\t");
            output.append(other.rowToString(i, false));
            output.append("\n");
        }

        return output.toString();
    }
}
