public class Main {
    public static void main(String[] args) {
        Board b = new Board();
        b.addShip("B4", 4, true);
        System.out.println(b);

        b.addShip("D5", 3, false);
        b.addShip("E7", 5, false);
        System.out.println(b);

        b.strikeBoard("B2");
        b.strikeBoard("E5");
        System.out.println(b);

        b.strikeBoard("D5");
        b.strikeBoard("F5");
        b.strikeBoard("G5");
        System.out.println(b);

//        Scanner input = new Scanner(System.in);
//        while(true){
//            System.out.print("Input a coordinate: ");
//            String s = input.nextLine();
//
//            if(s.equalsIgnoreCase("exit")){
//                break;
//            }
//
//            //System.out.println(Arrays.toString(Board.parseLocation(s)));
//        }
    }
}