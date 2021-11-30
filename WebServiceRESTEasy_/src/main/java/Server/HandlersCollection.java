package Server;

import Server.Security.SecurityHandler;
import commons.ServerConnection.GuiceListener;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import java.io.IOException;

public final class HandlersCollection {

    public static HandlerCollection handlerCollection() throws IOException {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.addServlet(HttpServletDispatcher.class, "/");
        context.addEventListener(new GuiceListener());

        ServletContextHandler loginContext = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        loginContext.setContextPath("/db");
        Handler login = SecurityHandler.connect(loginContext);

        return new HandlerCollection(context, login);
    }

}
