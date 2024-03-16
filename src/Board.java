public class Board {
    // A board's grid's rows should be labeled as LETTERS
    // Its columns should be labeled as NUMBERS
    Cell[][] grid;

    public Board(){
        grid = new Cell[10][10];
        initializeBoard();
    }
    public Board(int length, int width){
        if(length > 25){
            System.out.println("Maximum board size is 25 * 25.");
            length = 25;
        }

        if(width > 25){
            System.out.println("Maximum board size is 25 * 25.");
            width = 25;
        }

        grid = new Cell[length][width];
        initializeBoard();
    }

    // Populates the Board with Cell objects
    private void initializeBoard(){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    // Returns true if the action was successful; returns false otherwise
    public boolean addShip(String location, int length, boolean horizontal){
        // Check if location input is a valid coordinate
        if(parseLocation(location) == null){
            System.out.print("Please enter a valid coordinate.");
            return false;
        }

        // Check if the ship will actually be placed in bounds
        int[] coord = parseLocation(location);
        assert coord != null;
        if(coord[0] >= grid.length || coord[1] >= grid[0].length){
            System.out.println("Coordinate " + location + " is out of bounds of the board.");
            return false;
        } else if (coord[0] + (length * Cell.booleanToInt(!horizontal)) > grid.length || coord[1] + (length * Cell.booleanToInt(horizontal)) > grid[0].length){
            System.out.println("The ship will extend past the boundaries of the board.");
            return false;
        }


        // Check if all the cells the ship would occupy are vacant
        for(int i = 0; i < length; i++){
            Cell cell = grid[coord[0] + (i * Cell.booleanToInt(!horizontal))][coord[1] + (i * Cell.booleanToInt(horizontal))];
            if(cell.hasShip()){
                System.out.println("Coordinate " + location + " is already occupied by a ship!");
                return false;
            }
        }

        Ship ship = new Ship(location, length, horizontal);
        // Place the ship pieces!
        for(int i = 0; i < ship.getLength(); i++){
            Cell cell = grid[coord[0] + (i * Cell.booleanToInt(!horizontal))][coord[1] + (i * Cell.booleanToInt(horizontal))];
            if(!cell.placeShip(ship)){
                return false;
            }
        }

        return true;
    }

    // Takes a Battleship coordinate as a String input and returns the coordinate as an array of the 2 corresponding integers.
    // Returns null if the action was unsuccessful.
    public static int[] parseLocation(String location){
        // Ensures that the input is in the format of a Battleship coordinate, I.E. B10
        if(!location.matches("[A-Za-z][0-9]{1,2}")){
            System.out.println("Coordinates should be written as a letter followed by one or two digits, such as: D10");
            return null;
        }

        location = location.toUpperCase();
        int[] output = new int[2];
            // The first letter should always be an uppercase letter.
            output[0] = (int)location.charAt(0) - 64 - 1;
            // Everything after the first letter should always be an integer.
            output[1] = Integer.parseInt(location.substring(1)) - 1;
        return output;
    }

    @Override
    public String toString(){
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
}
