public class LibraryFactory {

    public Library library(Integer capacity, FileBookFactory fileBookFactory){
        return new Library(capacity, fileBookFactory);
    }
}
