import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a, b;

        System.out.print("Nhap so nguyen a: ");
        a = sc.nextInt();

        System.out.print("Nhap so nguyen b: ");
        b = sc.nextInt();

        System.out.println("Tong = " + (a + b));
        System.out.println("Hieu = " + (a - b));
        System.out.println("Tichdgivhhvdiuhfivhdsfsdhvkdshf khánh ăn cứt )
        if (b != 0) {
            System.out.println("Thuong = " + ((double) a / b));
        } else {
            System.out.println("Thuong = Khong the chia cho 0!");
        }

        sc.close();
    }
}
