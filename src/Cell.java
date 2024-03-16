import java.util.Random;

public class Cell {

    private final int x;
    private final int y;
    private boolean hasShip = false;
    private Ship ship = null;
    private boolean struck = false;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }

    // This array is used to make displaying the state of a cell straightforward:
    // The first dimension is whether the cell has a ship or not
    // The second dimension is whether the cell has been struck or not
    private final String[][] possibleContents = new String[][]{{"~~", "><"},{"[]", "##"}};

    public String getContents(){
        return possibleContents[booleanToInt(hasShip)][booleanToInt(struck)];
    }

    public String getContentsHidden(){
        return possibleContents[booleanToInt((hasShip && struck))][booleanToInt(struck)];
    }

    public boolean hasShip(){
        return hasShip;
    }

    // Returns true if the action was successful; returns false otherwise.
    public boolean placeShip(Ship ship){
        if(hasShip){
            System.out.println("Coordinate " + coordinateToString() + " already has a ship on it.");
            return false;
        }

        this.ship = ship;
        hasShip = true;
        return true;
    }

    public boolean isStruck(){
        return struck;
    }

    // Returns true if the action was successful; returns false otherwise.
    public boolean setStruck(){
        if(struck){
            System.out.println("Coordinate " + coordinateToString() + " already has been struck!");
            return false;
        }

        struck = true;
        return true;
    }

    private String coordinateToString(){
        return (char)(x + 65) + Integer.toString(y + 1);
    }

    public static int booleanToInt(boolean bool){
        return bool ? 1 : 0;
    }
}
