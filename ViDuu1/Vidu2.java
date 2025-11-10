import java.util.InputMismatchException;
import java.util.Scanner;

public class Vidu2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Chương trình tính toán đơn giản ===");
        int a = readInt(sc, "Nhập số nguyên a: ");
        int b = readInt(sc, "Nhập số nguyên b: ");

        System.out.println();
        System.out.println("Kết quả:");
        System.out.println("a = " + a + "    b = " + b);
        System.out.println("Tổng (a + b)    = " + (a + b));
        System.out.println("Hiệu (a - b)    = " + (a - b));
        System.out.println("Tích (a * b)    = " + (a * b));

        if (b != 0) {
            System.out.println("Thương (a / b)  = " + ((double) a / b));
            System.out.println("Phần dư (a % b) = " + (a % b));
        } else {
            System.out.println("Thương (a / b)  = Không thể chia cho 0!");
            System.out.println("Phần dư (a % b) = Không xác định (chia cho 0).");
        }

        sc.close();
        System.out.println("=== Kết thúc chương trình ===");
    }

    /**
     * Đọc một số nguyên từ Scanner, lặp lại nếu người dùng nhập không hợp lệ.
     * @param sc Scanner đã mở
     * @param prompt Dòng hiển thị yêu cầu nhập
     * @return số nguyên hợp lệ
     */
    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                // bỏ token lỗi và yêu cầu nhập lại
                sc.nextLine(); // clear invalid input
                System.out.println("Giá trị không hợp lệ. Vui lòng nhập một số nguyên.");
            }
        }
    }
}
