public class Main {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount();
        BankAccount account2 = new BankAccount();

        account1.accountNumber = "202410370110510";
        account1.ownerName = "Muh. Alifitrah Ismail";
        account1.balance = 1000000;

        account2.accountNumber = "202410370110506";
        account2.ownerName = "Ovan Kusuma Dewa";
        account2.balance = 2000000;

        account1.displayInfo();
        account2.displayInfo();

        account1.depositMoney(500000);
        account2.depositMoney(1000000);

        account1.withdrawMoney(2000000);
        account2.withdrawMoney(2700000);

        account1.displayInfo();
        account2.displayInfo();

    }
}