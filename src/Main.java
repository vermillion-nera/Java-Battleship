public class Main {
    public static void main(String[] args) {
        Board b = new Board();
        Board b2 = new Board();

        b.addShip("B4", 4, true);
        b.addShip("D5", 3, false);
        b.addShip("E7", 5, false);
        b.strikeBoard("B2");
        b.strikeBoard("E5");
        b.strikeBoard("D5");
        b.strikeBoard("F5");
        b.strikeBoard("G5");
        System.out.println(b.boardToString(false));
        System.out.println(b.boardToString(true));

        b2.addShip("A4", 5, true);
        b2.addShip("D3", 4, false);
        b2.addShip("F4", 3, true);
        b2.addShip("J9", 2, true);
        b2.strikeBoard("E9");
        b2.strikeBoard("C4");
        b2.strikeBoard("J1");
        b2.strikeBoard("F4");
        b2.strikeBoard("A7");
        System.out.println(b2.boardToString(false));
        System.out.println(b2.boardToString(true));

        System.out.println("Player 1:");
        System.out.println(b.generateView(b2));
        System.out.println("Player 2:");
        System.out.println(b2.generateView(b));
    }
}