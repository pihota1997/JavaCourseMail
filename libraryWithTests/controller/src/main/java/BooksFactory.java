import org.jetbrains.annotations.NotNull;

public class BooksFactory {
    public FileBookFactory fileBookFactory(@NotNull String fileName){
        return new FileBookFactory(fileName);
    }
}
