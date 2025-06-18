import java.util.Scanner;

public class loginSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();
        Student student = new Student();

        while (true) {
            System.out.println("=== Selamat datang ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Student Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Masukkan Username Admin: ");
                    String adminUser = scanner.nextLine();
                    System.out.print("Masukkan Password Admin: ");
                    String adminPass = scanner.nextLine();

                    if (admin.login(adminUser, adminPass)) {
                        System.out.println("Login Admin berhasil!\n");
                    } else {
                        System.out.println("Error: Kredensial tidak valid.\n");
                    }
                    break;

                case "2":
                    System.out.print("Masukkan Nama Siswa: ");
                    String studentName = scanner.nextLine();
                    System.out.print("Masukkan NIM: ");
                    String studentId = scanner.nextLine();

                    if (student.login(studentName, studentId)) {
                        System.out.println("Login siswa berhasil!\n!");
                        student.displayInfo(studentName);
                    } else {
                        System.out.println("Error: Kredensial tidak valid.\n");
                    }
                    break;

                case "3":
                    System.out.println("Mengeluarkan... Selamat Tinggal!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Error: Pilihan salah. Tolong pilih pilihan ke 1, 2, atau 3.\n");
            }
        }
    }
}
