public class Board {
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

    private void initializeBoard(){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = new Cell();
            }
        }
    }

    public void addShip(Ship ship, String location){

    }

    // Takes a Battleship coordinate as a String input and returns the coordinate as an array of the 2 corresponding integers.
    public static int[] parseLocation(String location){
        // Ensures that the input is in the format of a Battleship coordinate, I.E. B10
        if(!location.matches("[A-Za-z][0-9]{1,2}")){
            System.out.println("Coordinates should be written as a letter followed by one or two digits, such as: D10");
            return null;
        }

        location = location.toUpperCase();
        int[] output = new int[2];
            // The first letter should always be an uppercase letter.
            output[0] = (int)location.charAt(0) - 64;
            // Everything after the first letter should always be an integer.
            output[1] = Integer.parseInt(location.substring(1));
        return output;
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                output.append(cell.getContents());
            }
            output.append("\n");
        }
        return output.toString();
    }
}
