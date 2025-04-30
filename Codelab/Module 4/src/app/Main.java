package perpustakaan;

public class Main {
    public static void main(String[] args) {
        Buku Fiksi = new Fiksi("Rantau 1 Muara", "Ahmad Fuadi");
        Buku NonFiksi = new NonFiksi("Sapiens", "Yuval Noah Harari");

        Fiksi.displayInfo();
        NonFiksi.displayInfo();

        Anggota member = new Anggota("Ali Fitrah", "510");
        member.peminjaman();
        member.pengembalian();
        member.peminjaman("1984");
        member.peminjaman("Sapiens", 14);
    }
}
