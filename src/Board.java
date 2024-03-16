public class Board extends GamePiece {
    // A board's grid's rows should be labeled as LETTERS
    // Its columns should be labeled as NUMBERS
    Cell[][] grid;

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
