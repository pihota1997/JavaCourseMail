package handlers;

import commons.ServerConnection.ServerSettings;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.JDBCLoginService;
import org.eclipse.jetty.server.Handler;

public final class LoginHandler {

    public static ConstraintSecurityHandler connect(Handler context){

        final String jdbcConfig = ServerSettings.class.getResource("/jdbc_config").toExternalForm();
        final JDBCLoginService jdbcLoginService = new JDBCLoginService("login", jdbcConfig);
        final ConstraintSecurityHandler securityHandler = new SecurityHandlerBuilder().build(jdbcLoginService);
        securityHandler.setHandler(context);
        return securityHandler;
    }

}
