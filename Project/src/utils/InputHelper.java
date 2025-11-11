package utils;

import java.util.Scanner;

public class InputHelper {
    private static Scanner sc = new Scanner(System.in);

    public static String input(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    public static double inputDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }
}
