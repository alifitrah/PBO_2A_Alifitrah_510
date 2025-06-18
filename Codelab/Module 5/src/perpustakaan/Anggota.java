package perpustakaan;

public class Anggota implements Peminjaman {
    private String nama;
    private String NIM;

    public Anggota(String nama, String NIM) {
        this.nama = nama;
        this.NIM = NIM;
    }

    @Override
    public void peminjaman() {
        System.out.println(nama + " (" + NIM + ") meminjam buku");
    }

    @Override
    public void pengembalian() {
        System.out.println(nama + " (" + NIM + ") mengembalikan Buku.");
    }

    public void peminjaman(String title) {
        System.out.println(nama + " meminjam buku berjudul: " + title);
    }

    public void peminjaman(String title, int duration) {
        System.out.println(nama + " meminjam buku berjudul: " + title + " selama " + duration + " hari.");
    }

    public String getName() {
        return nama;
    }

    public String getMemberID() {
        return NIM;
    }
}
