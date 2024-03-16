public class Ship {
    private final String location;
    private final int length;
    private boolean horizontal;
    private int survivingUnits;

    public Ship(String location, int length, boolean horizontal){
        this.location = location;
        this.length = length;
        this.horizontal = horizontal;
        survivingUnits = length;
    }

    public int getLength(){
        return length;
    }

    public boolean isHorizontal(){
        return horizontal;
    }
}
