import DAO.OrganizationDAO;
import commons.FlywayInitializer;
import commons.JDBCCredentials;
import entity.Organization;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void main(@NotNull String[] args) {

        FlywayInitializer.initDB();
    }
}

