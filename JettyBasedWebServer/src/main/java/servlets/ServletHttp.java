package servlets;

import DAO.ProductManager;
import commons.dbConnection.JDBCCredentials;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.exception.DataAccessException;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public final class ServletHttp extends HttpServlet {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        resp.setContentType("text/plain");
        try (final PrintWriter out = resp.getWriter()) {

            out.println(ProductManager.createProductDao().all());

        } catch (SQLException | IOException e) {
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        final String name = req.getParameter("name");
        final String manufacturer = req.getParameter("manufacturer");
        final String quantity = req.getParameter("quantity");

        if (name == null || manufacturer == null || quantity == null) {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            return;
        }

        try {

            ProductManager.createProductDao()
                    .create(ProductManager.createProductsRecord(name, manufacturer, Integer.parseInt(quantity)));
            resp.sendRedirect("/db");

        } catch (SQLException | DataAccessException | IOException e) {
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
    }
}
