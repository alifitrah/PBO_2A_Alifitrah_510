import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String name;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name : ");
        name = scanner.nextLine();
        System.out.print("Enter gender (M/F): ");
        String gender = scanner.nextLine();
        System.out.print("Enter your year of birth: ");
        int yearOfBirth = scanner.nextInt();
        int currentYear = LocalDate.now().getYear();
        int age = currentYear - yearOfBirth;
        
        System.out.println("Name : " + name);
        switch (gender) {
            case "M":
                System.out.println("Gender: Male");
                break;
                case "F":
                    System.out.println("Gender: Female");
        }
        System.out.println("Age : " + age);

    }
}