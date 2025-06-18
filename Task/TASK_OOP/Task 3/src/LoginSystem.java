import java.util.Scanner;

public class LoginSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create one Admin and one Student
        Admin admin = new Admin("Admin", "0000", "admin", "admin123");
        Student student = new Student("Ali Fitrah", "510");

        System.out.println("=== Welcome to the Login System ===");
        System.out.println("1. Login as Admin");
        System.out.println("2. Login as Student");
        System.out.print("Enter your choice (1 or 2): ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            System.out.print("Enter Admin username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Admin password: ");
            String password = scanner.nextLine();

            if (admin.login(username, password)) {
                admin.displayInfo();
            } else {
                System.out.println("Invalid admin credentials.");
            }

        } else if (choice.equals("2")) {
            System.out.print("Enter Student name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Student ID: ");
            String id = scanner.nextLine();

            if (student.login(name, id)) {
                student.displayInfo();
            } else {
                System.out.println("Invalid student credentials.");
            }

        } else {
            System.out.println("Invalid choice. Please select 1 or 2.");
        }

        scanner.close();
    }
}