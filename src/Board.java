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
    public boolean addShip(String location, int length, boolean horizontal){
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

    // Takes a string and ensures that it is in the format of a valid Battleship coordinate that is within the Board's boundaries.
    public boolean isInvalidCoordinate(String location){
        int[] coord = parseLocation(location);
        return isInvalidCoordinate(coord);
    }

    public boolean isInvalidCoordinate(int[] coord){
        // Check if location input is a valid coordinate
        if(coord == null){
            System.out.print("Please enter a valid coordinate.");
            return true;
        }

        // Check if the strike is actually in bounds
        if(coord[0] >= grid.length || coord[1] >= grid[0].length){
            System.out.println("Coordinate " + coordinateToString(coord) + " is out of bounds of the board.");
            return true;
        }

        return false;
    }

    @Override
    public String toString(){
        return ("Board of size " + grid.length + "*" + grid[0].length + " with " + ships.size() + " ships and " + shipUnitsRemaining() + " ship cells remaining.");
    }

    public String boardToString(){
        StringBuilder output = new StringBuilder("- ");
        for (int i = 0; i < grid.length; i++){
            output.append((i + 1)).append(" ");
        }
        output.append("\n");

        for (int i = 0; i < grid.length; i++) {
            output.append(((char) (i + 65))).append(" ");
            for (int j = 0; j < grid[i].length; j++) {
                output.append(grid[i][j].getContents());
            }
            output.append("\n");
        }
        return output.toString();
    }

    public String shipsToString(){
        StringBuilder output = new StringBuilder();
        for(Ship ship : ships){
            output.append(ship.toString());
            output.append("\n");
        }
        return output.toString();
    }
}
