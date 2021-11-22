package commons.dbConnection;

import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;

public class FlywayInitializer {
    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void initDb() {
        final Flyway flyway = Flyway.configure()
                .dataSource(
                        CREDS.url(),
                        CREDS.login(),
                        CREDS.password()
                )
                .locations("db")
                .load();

        flyway.migrate();
    }
}
