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
                    Board b = player1.getBoard();
                    b.addShip(4, "B4", true);
//                    b.addShip(3, "D5", false);
//                    b.addShip(5, "E7", false);
//                    b.addShip(2, "J9", true);
//                    b.strikeBoard("B2");
//                    b.strikeBoard("E5");
//                    b.strikeBoard("D5");
//                    b.strikeBoard("F5");
//                    b.strikeBoard("G5");
                    setupBoard(player1);
                    gameState = states.P2SETUP;
                }

                case P2SETUP -> {
                    Board b2 = player2.getBoard();
                    b2.addShip(5, "A4", true);
//                    b2.addShip(4, "D3", false);
//                    b2.addShip(3, "F4", true);
//                    b2.addShip(2, "J9", true);
//                    b2.strikeBoard("E9");
//                    b2.strikeBoard("C4");
//                    b2.strikeBoard("J1");
//                    b2.strikeBoard("F4");
//                    b2.strikeBoard("A7");
                    setupBoard(player2);
                    gameState = states.P1TURN;
                }

                case P1TURN -> {
                    takeTurn(player1, player2);
                    if(player2.checkLoss()){
                        gameState = states.END;
                        continue;
                    }
                    gameState = states.P2TURN;
                }

                case P2TURN -> {
                    takeTurn(player2, player1);
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

    // Should only be called once per player in the setup phases.
    // Takes a player object and walks a human player through the process of populating that player
    // object's board with ship pieces.
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

    // Takes a player object and a piece length, and walks a human player through the process
    // of placing the piece on the player object's board. Cool stuff.
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

    private static void takeTurn(Player player, Player opponent) {
        Scanner input = new Scanner(System.in);
        String target;
        boolean targetUndecided = true;
        Board board = player.getBoard();
        Board opponentBoard = opponent.getBoard();

        // Print a bunch of newlines so the player will not be able to see the previous player's actions.
        for(int i = 0; i < 50; i++){
            System.out.println();
        }

        // Hide the board until the intended player gives the OK
        System.out.println(player.getName() + "'s turn!");
        System.out.print("Press enter when ready.");
        input.nextLine();

        System.out.println();
        System.out.println(board.generateView(opponentBoard));
        do {
            System.out.print("Enter a coordinate on your opponent's board to strike: ");
            target = input.nextLine();
            if(!opponentBoard.isInvalidCoordinate(target)){
                if(opponentBoard.strikeBoard(target)){
                    targetUndecided = false;
                }
            }
        } while (targetUndecided);

        // Show the results until the player gives the OK to hide them.
        System.out.print("Press enter to end your turn.");
        input.nextLine();
    }

}