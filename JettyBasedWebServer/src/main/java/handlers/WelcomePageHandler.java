package handlers;

import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

import java.io.IOException;
import java.net.URL;

public final class WelcomePageHandler {

    public static ResourceHandler connect() throws IOException {
        final ResourceHandler resourceHandler = new ResourceHandler();
        final URL resource = WelcomePageHandler.class.getResource("/static/welcome.html");
        resourceHandler.setBaseResource(Resource.newResource(resource.toExternalForm()));
        resourceHandler.setDirectoriesListed(false);
        return resourceHandler;
    }

}
