public class Ship {
    private int length;
    private String location;
    private boolean horizontal;
    private boolean sunk = false;

    public Ship(){
        length = 1;
        horizontal = true;
    }

    public Ship(int length){
        this.length = length;
        horizontal = true;
    }

    public Ship(int length, boolean horizontal){
        this.length = length;
        this.horizontal = horizontal;
    }
}
