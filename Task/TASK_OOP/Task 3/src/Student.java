public class Student extends User {

    public Student(String name, String studentId) {
        super(name, studentId); // Using super to call superclass constructor
    }

    @Override
    public boolean login(String inputName, String inputId) {
        return inputName.equals(getName()) && inputId.equals(getStudentId());
    }

    @Override
    public void displayInfo() {
        System.out.println("Student login successful.");
        System.out.println("Name: " + getName());
        System.out.println("Student ID: " + getStudentId());
    }
}