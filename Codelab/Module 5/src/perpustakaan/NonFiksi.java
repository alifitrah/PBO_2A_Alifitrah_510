package perpustakaan;

public class NonFiksi extends Buku {

    public NonFiksi(String title, String author) {
        super(title, author);
    }

    @Override
    public void displayInfo() {
        System.out.println("Buku NonFiksi: " + judul + "oleh" + penulis);
    }
}
