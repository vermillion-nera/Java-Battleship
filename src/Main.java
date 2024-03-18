import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // All possible states the game can be in at any given time.
        enum states {
            START,
            P1SETUP,
            P2SETUP,
            P1TURN,
            P2TURN,
            END
        }

        states gameState= states.START;
        boolean gameActive = true;
        boolean vsCPU = false;

        Player player1 = new Player();
        Player player2 = new Player();

        // Big ol' do-while loop. One iteration of this game loop should correspond to one in-game step or turn.
        // The value of `gameState` should change at some point over every iteration.
        do{
            switch (gameState){
                case START -> {
                    System.out.println("\t\tJAVA BATTLESHIP");
                    System.out.println("\t Written by Wren Caillouet");
                    System.out.println();

                    String name;
                    System.out.print("Enter a name for Player 1: ");
                    name = input.nextLine();
                    player1.setName(name);

                    System.out.print("Enter a name for Player 2: ");
                    name = input.nextLine();
                    player2.setName(name);
                    System.out.println();

                    gameState = states.P1SETUP;
                }

                case P1SETUP -> {
                    setupBoard(player1);
                    gameState = states.P2SETUP;
                }

                case P2SETUP -> {
                    setupBoard(player2);
                    gameState = states.P1TURN;
                }

                case P1TURN -> {
                    takeTurn(player1);
                    if(player2.checkLoss()){
                        gameState = states.END;
                        continue;
                    }
                    gameState = states.P2TURN;
                }

                case P2TURN -> {
                    takeTurn(player2);
                    if(player1.checkLoss()){
                        gameState = states.END;
                    }
                    gameState = states.P1TURN;
                }

                case END -> {
                    gameActive = false;
                }
            }
        } while (gameActive);
    }

    // Should ONLY be called once per player in the setup phases... Ideally with a blank board.
    // Does what it says on the box.
    private static void setupBoard(Player player) {
        // These are the lengths of the 5 ship pieces that come with a physical battleship game.
        int[] pieces = {5, 4, 3, 3, 2};

        System.out.println("Setting up " + player.getName() + "'s board...");

        for (int piece : pieces) {
            System.out.println("Next piece: " + piece + " long ship.");
            manualPlaceShip(player, piece);
        }

        System.out.println("Final board: ");
        System.out.println(player.getBoard().boardToString(false));
    }

    // Takes a player object input and a piece length, and walks a human player through the inputs
    // to place the piece on the player object's board. Cool stuff.
    private static void manualPlaceShip(Player player, int piece){
        Scanner input = new Scanner(System.in);
        Board board = player.getBoard();

        boolean horizontal = false;
        String location;

        do{
            boolean orientationUndecided = true;
            boolean locationUndecided = true;

            System.out.println(board.boardToString(false));

            // --------------- Decide orientation -----------------
            do{
                String str;
                System.out.println("Should your " + piece + " long ship be vertical or horizontal?");
                System.out.print("Input V for vertical, or H for horizontal: ");
                str = input.nextLine();

                if(str.equalsIgnoreCase("V")){
                    orientationUndecided = false;
                } else if(str.equalsIgnoreCase("H")){
                    horizontal = true;
                    orientationUndecided = false;
                } else {
                    System.out.println("Please enter a valid input.");
                }

                System.out.println();
            } while (orientationUndecided);

            // --------------- Decide location -----------------
            do{
                System.out.println("Where should the " + (horizontal ? "left" : "top") +" square of the ship be placed?");
                System.out.print("Enter a coordinate: ");
                location = input.nextLine();

                if(!board.isInvalidCoordinate(location)){
                    locationUndecided = false;
                }

                System.out.println();
            } while (locationUndecided);

        } while (!board.addShip(piece, location, horizontal));
    }

    private static void takeTurn(Player player) {

    }

    private static void gameStep(){

    }
}