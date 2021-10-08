import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LibraryFactory {

    public Library createLibrary(File file) {
        try {
            Scanner scanner = new Scanner(file);
            return new Library(scanner.nextLine());
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return new Library("ExceptionLibrary");
        }
    }
}
