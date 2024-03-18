// ----------------------------------------------
//              JAVA BATTLESHIP
// ----------------------------------------------
//  A faithful recreation of the Battleship board game,
//  playable entirely within an IDE's output log.
//
//  Written by Wren Caillouet, 3/15/24 - 3/18/24


import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    // QOL information trackers to display information to the player on their turn
    static int turnCount = 0;
    static String lastSquare;
    static boolean shipStruck;
    static boolean shipSank;

    enum states {
        START,
        P1SETUP,
        P2SETUP,
        P1TURN,
        P2TURN,
        END
    }
    public static void main(String[] args) {
        states gameState = states.START;
        boolean gameActive = true;

        Player player1 = new Player();
        Player player2 = new Player();
        Player winner = null;

        // Big ol' do-while loop. One iteration of this game loop should correspond to one in-game step or turn.
        // The value of `gameState` should change at some point over every iteration.
        do{
            switch (gameState){
                case START -> {
                    System.out.println("\t\tJAVA BATTLESHIP");
                    System.out.println("\t Written by Wren");
                    System.out.println();

                    String name;
                    System.out.print("Enter a name for Player 1: ");
                    name = input.nextLine().trim();
                    player1.setName(name);

                    System.out.print("Enter a name for Player 2: ");
                    name = input.nextLine().trim();
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
                    takeTurn(player1, player2);
                    if(player2.checkLoss()){
                        winner = player1;
                        gameState = states.END;
                        continue;
                    }
                    gameState = states.P2TURN;
                }

                case P2TURN -> {
                    takeTurn(player2, player1);
                    if(player1.checkLoss()){
                        winner = player2;
                        gameState = states.END;
                        continue;
                    }
                    gameState = states.P1TURN;
                }

                case END -> {
                    String str;

                    System.out.println("\n\n----------- GAME OVER -----------");
                    System.out.println(winner.getName() + " sunk all battleships and won!");
                    System.out.println();

                    System.out.print("Rematch? ");
                    str = input.nextLine().trim();
                    if(str.toLowerCase().contains("y")){
                        player1 = new Player(player1.getName());
                        player2 = new Player(player2.getName());
                        turnCount = 0;
                        gameState = states.P1SETUP;
                        continue;
                    }

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

        // Print a bunch of newlines so the player will not be able to see the previous player's actions.
        for(int i = 0; i < 40; i++){
            System.out.println();
        }

        System.out.println(player.getName() + ", press enter to begin setting up your board.");
        input.nextLine();

        for (int piece : pieces) {
            System.out.println("Next piece: " + piece + " long ship.");
            manualPlaceShip(player, piece);
        }

        System.out.println("Final board: ");
        System.out.println(player.getBoard().boardToString(false));

        // Show the results until the player gives the OK to hide them.
        System.out.print("Press enter to finish setup.");
        input.nextLine();
    }

    // Takes a player object and a piece length, and walks a human player through the process
    // of placing the piece on the player object's board. Cool stuff.
    private static void manualPlaceShip(Player player, int piece){
        Board board = player.getBoard();

        boolean horizontal = false;
        String location;

        do{
            boolean orientationUndecided = true;
            boolean locationUndecided = true;

            System.out.println(board.boardToString(false));

            // --------------- Decide orientation -----------------
            // Iterates until a valid input is made.
            do{
                String str;
                System.out.println("Should your " + piece + " long ship be vertical or horizontal?");
                System.out.print("Input V for vertical, or H for horizontal: ");
                str = input.nextLine().trim();

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
            // Iterates until a valid input is made.
            do{
                System.out.println("Where should the " + (horizontal ? "left" : "top") +" square of the ship be placed?");
                System.out.print("Enter a coordinate: ");
                location = input.nextLine().trim();

                if(board.isValidCoordinate(location)){
                    locationUndecided = false;
                }

                System.out.println();
            } while (locationUndecided);

        } while (!board.addShip(piece, location, horizontal));
    }

    private static void takeTurn(Player player, Player opponent) {
        turnCount++;
        String target;
        boolean targetUndecided = true;
        Board board = player.getBoard();
        Board opponentBoard = opponent.getBoard();

        // Print a bunch of newlines so the player will not be able to see the previous player's actions.
        for(int i = 0; i < 40; i++){
            System.out.println();
        }

        // Hide the board until the intended player gives the OK
        System.out.println(player.getName() + "'s turn!");
        System.out.print("Press enter when ready.");
        input.nextLine();

        // Show the board
        System.out.println();
        System.out.println(board.generateView(opponentBoard));

        // QOL feature: Display data about the last turn for the current player.
        if(turnCount > 1){ // At least one turn needs to occur to be able to display data
            System.out.println("Your opponent struck square " + lastSquare + ".");
            System.out.println("It was a " + (shipStruck ? "hit." : "miss."));
            if(shipSank){
                System.out.println("It sunk your battleship...");
            }
        }

        // Main turn loop - iterates until a valid move is made.
        do {
            System.out.print("Enter a coordinate on your opponent's board to strike: ");
            target = input.nextLine().trim();
            if(opponentBoard.isValidCoordinate(target)){
                if(opponentBoard.strikeBoard(target)){
                    // A successful strike was made on the board. Break out of the turn loop.
                    targetUndecided = false;

                    // Collect data about the square that was hit to display to the next player.
                    lastSquare = target.toUpperCase();
                    Cell c = opponentBoard.getCell(lastSquare);

                    if(c.hasShip()){
                        shipStruck = true;
                        if(c.getShip().getSurvivingUnits() <= 0){
                            shipSank = true;
                        }
                    } else {
                        shipStruck = false;
                        shipSank = false;
                    }
                }
            }
        } while (targetUndecided);

        // Show the results until the player gives the OK to hide them.
        System.out.print("Press enter to end your turn.");
        input.nextLine();
    }

}