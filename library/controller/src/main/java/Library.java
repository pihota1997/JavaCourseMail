import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.*;

public class Library {
    private HashMap<String, String> books;

    public Library() {
      this.books  = new HashMap();
    }

    public void addBook(String title, String author) {
        this.books.put(title, author);
    }

    public HashMap<String, String> getBooks() {
        return this.books;
    }

    public void authorBooks() {
        final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        Scanner scanner = new Scanner(System.in);
        List<String> thisAuthorBooks = new ArrayList<>();
        String author = scanner.nextLine();
        for (Map.Entry<String, String> entry : books.entrySet()) {
            if (author.equals(entry.getValue()))
                thisAuthorBooks.add(entry.getKey());
        }
        String json = GSON.toJson(thisAuthorBooks);
        System.out.println(json);
    }
}
