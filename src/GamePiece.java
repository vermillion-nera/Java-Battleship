public abstract class GamePiece {
    // Takes a Battleship coordinate as a String input and returns the coordinate as an array of the 2 corresponding integers.
    // Returns null if the action was unsuccessful.
    public static int[] parseLocation(String location){
        // Ensures that the input is in the format of a Battleship coordinate, I.E. B10
        if(!location.matches("[A-Za-z][1-9]{1,2}")){
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

    // Takes two integers as an input and rewrites them in the format of a Battleship coordinate: I.E. B7
    public static String coordinateToString(int x, int y){
        return (char)(x + 65) + Integer.toString(y + 1);
    }

    public static String coordinateToString(int[] coord){
        return (char)(coord[0] + 65) + Integer.toString(coord[1] + 1);
    }

    public static int booleanToInt(boolean bool){
        return bool ? 1 : 0;
    }
}
