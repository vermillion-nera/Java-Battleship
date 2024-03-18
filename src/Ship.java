public class Ship extends GamePiece {
    private final int length;
    private int survivingUnits;

    public Ship(int length){
        this.length = length;
        survivingUnits = length;
    }

    public int getLength(){
        return length;
    }

    public int getSurvivingUnits(){
        return survivingUnits;
    }

    public void hitShip(){
        if(survivingUnits > 0){
            survivingUnits--;
        }
    }

    @Override
    public String toString(){
        return ("Ship " + length + " units long with " + survivingUnits + " units remaining.");
    }
}
