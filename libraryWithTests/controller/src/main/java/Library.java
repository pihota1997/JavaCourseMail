import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Library{
    private LimitedLinkedList books;

    public Library(int capacity, FileBookFactory fileBookFactory) {
        this.books = new LimitedLinkedList(capacity);
        for (Book book : fileBookFactory.books()) {
            books.add(book);
        }
    }

    public LimitedLinkedList getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void takeBook(int cellNumber) throws IndexOutOfBoundsException, NullPointerException {
        System.out.println("Cell number is " + cellNumber + ", "
                + getInformationAboutBook(cellNumber));
    }

    public String getInformationAboutBook(int cellNumber) {
        return getBook(cellNumber).getName() + " " + getBook(cellNumber).getAuthor().getName();
    }

    public Book getBook(int cellNumber){
        return books.get(cellNumber);
    }

    public void printLibrary() {
        final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        String json = GSON.toJson(this.books);
        System.out.println(json);
    }
}
