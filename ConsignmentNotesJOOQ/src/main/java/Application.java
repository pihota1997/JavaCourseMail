import commons.FlywayInitializer;
import org.jetbrains.annotations.NotNull;

public class Application {
    public static void main(@NotNull String[] args){

        FlywayInitializer.initDB();

    }
}

