public class Player {
    private String name = "Player";
    private final Board board;

    Player (){
        this.board = new Board();
    }

    Player (String name){
        this.name = name;
        this.board = new Board();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Board getBoard() {
        return board;
    }

    public boolean checkLoss(){
        // A player loses if their board contains no surviving ship units.
        return (board.shipUnitsRemaining() <= 0);
    }
}
