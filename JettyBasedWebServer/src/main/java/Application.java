import commons.ServerConnection.DefaultServer;
import commons.dbConnection.FlywayInitializer;
import commons.ServerConnection.ServerSettings;
import org.eclipse.jetty.server.*;


public final class Application {
    public static void main(String[] args) throws Exception {

        FlywayInitializer.initDb();

        final Server server = new DefaultServer().build(3466);
        
        ServerSettings.connect(server);
        server.start();
    }
}
