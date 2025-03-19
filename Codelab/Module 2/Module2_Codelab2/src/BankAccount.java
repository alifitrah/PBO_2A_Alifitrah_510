public class BankAccount {
    String accountNumber;
    String ownerName;
    double balance;

    void displayInfo(){
        System.out.println("Nomor Rekening: " + accountNumber);
        System.out.println("Nama Pemilik: " + ownerName);
        System.out.println("Saldo: Rp" + balance);
        System.out.println();
    }
    void depositMoney(double amount){
        balance += amount;
        System.out.println(ownerName + " " + "Menyetor Rp" + amount + " " + "Saldo sekarang: Rp" + balance);
    }
    void withdrawMoney(double amount){
        if(balance < amount){
            System.out.println();
            System.out.println(ownerName + " " + "Menarik Rp" + amount + " " + "(Gagal, saldo tidak mencukupi) Saldo saat ini: " + balance);
        } else {
            balance -= amount;
            System.out.println(ownerName + " " + "Menarik Rp" + amount + " " + "(Berhasil) Saldo sekarang: " + balance);
            System.out.println();
        }

    }
}
