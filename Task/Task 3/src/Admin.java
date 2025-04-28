public class Admin extends User {
    private String username;
    private String password;

    public Admin(String name, String studentId, String username, String password) {
        super(name, studentId); // Using super to call superclass constructor
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean login(String inputUsername, String inputPassword) {
        return inputUsername.equals(username) && inputPassword.equals(password);
    }

    @Override
    public void displayInfo() {
        System.out.println("Admin login successful.");
    }
}