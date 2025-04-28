public class User {
    private String name;
    private String studentId;

    public User(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
    }

    // Encapsulation: Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    // Method to be overridden
    public boolean login(String input1, String input2) {
        return false;
    }

    public void displayInfo() {
        System.out.println("User information.");
    }
}