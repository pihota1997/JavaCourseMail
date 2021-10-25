public class Book{
    private String name;
    private Author author;

    public Book(String name, Author author){
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }
}
