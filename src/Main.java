import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board b = new Board();
        b.addShip("B4", 4, true);
        b.addShip("D5", 3, false);
        b.addShip("E7", 5, false);
        //b.addShip();
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