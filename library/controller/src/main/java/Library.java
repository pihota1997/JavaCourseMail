import java.util.*;

public class Library {
    private HashMap<String, String> books = new HashMap();
    private String name;

    public Library(String name) {
        this.name = name;
    }

    public void addBook(String title, String author) {
        this.books.put(title, author);
    }

    public HashMap<String, String> getBooks() {
        return this.books;
    }
}
