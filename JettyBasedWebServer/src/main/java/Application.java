import commons.ServerConnection.DefaultServer;
import commons.dbConnection.FlywayInitializer;
import commons.ServerConnection.ServerSettings;
import org.eclipse.jetty.server.*;


public final class Application {
    public static void main(String[] args) {

        FlywayInitializer.initDb();

        final Server server = new DefaultServer().build(3466);
        try {
            ServerSettings.connect(server);
            server.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
