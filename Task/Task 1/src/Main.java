import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Select Login Type: ");
        System.out.println("1. Admin");
        System.out.println("2. Student");
        System.out.printf("Enter your choice: ");
        Scanner sc = new Scanner(System.in).useDelimiter("\\n");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                String user;
                String pass;

                user = "Admin510";
                pass = "Password510";
                System.out.printf("Enter admin username: ");
                String username = sc.next();
                System.out.printf("Enter admin password: ");
                String password = sc.next();

                if (username.equals(user) && password.equals(pass)) {

                    System.out.println("Admin Login Successful!");
                } else {
                    System.out.println("Login failed! Wrong username or password.");
                }
                break;
            case 2:
                String name;
                String nim;

                name = "Muh. Alifitrah Ismail";
                nim = "202410370110510";
                System.out.printf("Enter Name: ");
                String Name = sc.next();
                System.out.printf("Enter Student ID: ");
                String ID = sc.next();

                if (Name.equals(name) && ID.equals(nim)) {

                    System.out.println("Student Login Successful!");
                    System.out.println("Name: " + name);
                    System.out.println("Student ID: " + ID);

                } else {
                    System.out.println("Login Failed! Wrong name or student ID.");
                }
        } if (choice < 1 || choice > 2) {
            System.out.println("Invalid choice.");
        }
    }
}