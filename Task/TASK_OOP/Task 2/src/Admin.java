public class Admin {
    public String username = "admin";
    public String password = "admin510";

    public boolean login(String inputUsername, String inputPassword) {
        return inputUsername.equals(username) && inputPassword.equals(password);
    }
}