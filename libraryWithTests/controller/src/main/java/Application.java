import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.*;

public class Application {
    public static void main(@NotNull String[] args) throws NumberFormatException {
        final Injector injector = Guice.createInjector();
        injector.getInstance(LibraryFactory.class).library(Integer.parseInt(args[1]),
                injector.getInstance(BooksFactory.class).fileBookFactory(args[0])).printLibrary();
    }
}
