package commons.ServerConnection;

import handlers.InfoHandler;
import handlers.LoginHandler;
import handlers.WelcomePageHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.ServletHttp;

import java.io.IOException;

public final class ServerSettings {

    public static void connect(Server server) throws IOException {

        server.setHandler(contextCollection());

    }

    private static HandlerCollection contextCollection() throws IOException {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/db");
        context.addServlet(new ServletHolder("dbConnection", new ServletHttp()), "/db");
        Handler login = LoginHandler.connect(context);

        ContextHandler contextInfo = new ContextHandler();
        contextInfo.setContextPath("/info");
        contextInfo.setHandler(InfoHandler.connect());

        ContextHandler contextWelcome = new ContextHandler();
        context.setContextPath("/");
        contextWelcome.setHandler(WelcomePageHandler.connect());

        return new HandlerCollection(contextWelcome, contextInfo, login);
    }

}
