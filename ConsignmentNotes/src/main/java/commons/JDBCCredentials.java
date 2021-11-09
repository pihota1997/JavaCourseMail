package commons;

import org.jetbrains.annotations.NotNull;

public final class JDBCCredentials {
    public static final @NotNull JDBCCredentials DEFAULT = new JDBCCredentials(
            "127.0.0.1",
            "5432",
            "consignment_notes",
            "postgres",
            "postgre"
    );

    public static final @NotNull String PREFIX = "jdbc:postgresql";

    private final @NotNull String host;
    private final @NotNull String port;
    private final @NotNull String dbname;
    private final @NotNull String login;
    private final @NotNull String password;

    private JDBCCredentials(@NotNull String host,
                            @NotNull String port,
                            @NotNull String dbname,
                            @NotNull String login,
                            @NotNull String password) {
        this.host = host;
        this.port = port;
        this.dbname = dbname;
        this.login = login;
        this.password = password;
    }

    public @NotNull String url() {
        return PREFIX + "://" + host + ":" + port + "/" + dbname;
    }

    public @NotNull String getLogin() {
        return login;
    }

    public @NotNull String getPassword() {
        return password;
    }
}
