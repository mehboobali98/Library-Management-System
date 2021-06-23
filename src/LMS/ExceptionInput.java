package LMS;

import java.util.Scanner;

public class ExceptionInput {

    public static double EInputdouble(String Errmessage) {
        Scanner scanner = new Scanner(System.in);
        double x;
        while (true) {
            try {
                x = Double.parseDouble(scanner.nextLine());
                break;
            } catch (Exception e) {
                Color.Print(Color.ANSI_RED, Errmessage);
            }
        }
        return x;
    }

    public static int EInputInt(String Errmessage) {
        Scanner scanner = new Scanner(System.in);
        int x;
        while (true) {
            try {
                x = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception e) {
                Color.Print(Color.ANSI_RED, Errmessage);

            }
        }
        return x;
    }
}