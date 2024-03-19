public class Cell extends GamePiece {

    // This array is used to make displaying the state of a cell straightforward:
    // The first dimension is whether the cell has a ship or not
    // The second dimension is whether the cell has been struck or not
    private static final String[][] possibleContents = new String[][]{{"~~", "><"},{"[]", "##"}};
    private final int x;
    private final int y;
    private boolean hasShip = false;
    private Ship ship = null;
    private boolean struck = false;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String getContents(){
        return possibleContents[booleanToInt(hasShip)][booleanToInt(struck)];
    }

    public String getContentsHidden(){
        return possibleContents[booleanToInt((hasShip && struck))][booleanToInt(struck)];
    }

    // Returns whether the cell has a ship object on it at ALL (Not to be confused with getShip())
    public boolean hasShip(){
        return hasShip;
    }

    // Returns the specific Ship object placed on the cell (Not to be confused with hasShip())
    public Ship getShip(){
        if(hasShip){
            return ship;
        }
        return null;
    }

    // Returns true if the action was successful; returns false otherwise.
    public boolean placeShip(Ship ship){
        if(hasShip){
            System.out.println("Coordinate " + coordinateToString(x, y) + " already has a ship on it.");
            return false;
        }

        this.ship = ship;
        hasShip = true;
        return true;
    }

    // Returns true if the action was successful; returns false otherwise.
    public boolean setStruck(){
        if(struck){
            System.out.println("Coordinate " + coordinateToString(x, y) + " already has been struck!");
            return false;
        } else if (hasShip){
            System.out.println("Hit!");
            ship.hitShip();
        } else {
            System.out.println("Miss...");
        }

        struck = true;
        return true;
    }

    @Override
    public String toString(){
        return ("Cell " + coordinateToString(x, y) + " has " + (hasShip ? "a" : "no") + " ship and " + (struck ? "has" : "hasn't") + " been struck.");
    }
}
