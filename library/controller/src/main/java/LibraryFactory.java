import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LibraryFactory {

    public Library createLibrary(File file) throws FileNotFoundException {
            Scanner scanner = new Scanner(file);
            return new Library(scanner.nextLine());
    }
}
