import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.Scanner;


public class Application {
    @NotNull
    private final BaseClass obj1;
    @NotNull
    private final BaseClass obj2;

    @Inject
    public Application(@ConsoleAnnotation @NotNull BaseClass obj1,
                       @FileAnnotation @NotNull BaseClass obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public void waitForInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            long n = 1;
            String tag = "";
            System.out.println("Specify one of the logging types: console, file, composite");
            String type = scanner.nextLine();
            if (type.equals("file") || type.equals("composite")) {
                System.out.println("Enter tag name");
                tag = scanner.nextLine();
            } else if (!(type.equals("console"))) {
                throw new IllegalStateException();
            }
            System.out.println("Waiting for new lines. Key in Ctrl+D to exit.");
            while (true) {
                String message = scanner.nextLine();
                if (type.equals("console")) {
                    obj1.run(message + " " + n++);
                } else if (type.equals("file")) {
                    obj2.run("<" + tag + ">" + message + " " + n++ + "</" + tag + ">");
                } else {
                    obj1.run(message + " " + n++);
                    obj2.run("<" + tag + ">" + message + " " + n++ + "</" + tag + ">");
                }
            }
        } catch (IllegalStateException | NoSuchElementException e) {
            System.out.println("Data entered incorrectly");
        }
    }
}
