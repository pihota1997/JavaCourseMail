package commons.ServerConnection;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public enum DefaultServer {
    ;

    public static Server build() {
        final Server server = new Server();
        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setHost("localhost");
        serverConnector.setPort(3466);
        server.setConnectors(new Connector[]{serverConnector});
        return server;
    }
}
