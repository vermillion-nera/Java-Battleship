import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while(true){
            System.out.print("Input a coordinate: ");
            String s = input.nextLine();

            if(s.equalsIgnoreCase("exit")){
                break;
            }

            System.out.println(Arrays.toString(Board.parseLocation(s)));
        }
    }
}