package commons;

import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;

public final class FlywayInitializer {
    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void initDB() {
        final Flyway flyway = Flyway.configure()
                .dataSource(
                        CREDS.url(),
                        CREDS.getLogin(),
                        CREDS.getPassword()
                )
                .locations("db")
                .load();
        flyway.clean();
        flyway.migrate();
    }
}
