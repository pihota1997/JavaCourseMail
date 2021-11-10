import commons.FlywayInitializer;
import commons.JDBCCredentials;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class Application {
    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void main(@NotNull String[] args) {

        FlywayInitializer.initDB();

    }
}

