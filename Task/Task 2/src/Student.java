import java.util.HashMap;

public class Student {
    public HashMap<String, String> studentData;

    public Student() {
        studentData = new HashMap<>();
        studentData.put("Ali Fitrah", "510");
        studentData.put("Valentino", "511");
        studentData.put("Ismail", "512");
    }

    public boolean login(String name, String studentId) {
        return studentData.containsKey(name) && studentData.get(name).equals(studentId);
    }

    public void displayInfo(String name) {
        System.out.println("\n--- Informasi Mahasiswa 2024 ---");
        System.out.println("Nama         : " + name);
        System.out.println("NIM Mahasiswa: 202410370110" + studentData.get(name));
        System.out.println("----------------------------\n");
    }
}