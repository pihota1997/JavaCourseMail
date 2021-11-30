package commons.ServerConnection;

import org.eclipse.jetty.server.*;
import org.jetbrains.annotations.NotNull;

public final class DefaultServer {

    @NotNull
    private final Server server = new Server();

    public Server build(int port) {

        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setHost("localhost");
        serverConnector.setPort(port);
        server.setConnectors(new Connector[]{serverConnector});
        return server;

    }
}
