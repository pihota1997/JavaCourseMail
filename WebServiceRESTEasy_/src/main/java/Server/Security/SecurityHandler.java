package Server.Security;

import Server.ResourcesREST;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.JDBCLoginService;
import org.eclipse.jetty.server.Handler;

import javax.inject.Inject;
import java.io.IOException;

public final class SecurityHandler {

    @Inject
    public static ConstraintSecurityHandler connect(Handler context) throws IOException {

        final String jdbcConfig = ResourcesREST.class.getResource("/jdbc_config").toExternalForm();
        final JDBCLoginService jdbcLoginService = new JDBCLoginService("login", jdbcConfig);
        final ConstraintSecurityHandler securityHandler = new SecurityHandlerBuilder().build(jdbcLoginService);
        securityHandler.setHandler(context);
        return securityHandler;
    }

}
