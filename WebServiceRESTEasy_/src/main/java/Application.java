import commons.ServerConnection.DefaultServer;
import commons.dbConnection.FlywayInitializer;
import org.eclipse.jetty.server.Server;

import Server.HandlersCollection;

public final class Application {
    public static void main(String[] args){

        FlywayInitializer.initDb();

        final Server server = DefaultServer.build();

        try {
            server.setHandler(HandlersCollection.handlerCollection());
            server.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
