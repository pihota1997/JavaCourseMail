package servlets;

import DAO.ProductDAO;
import commons.dbConnection.JDBCCredentials;
import generated.tables.records.ProductsRecord;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public final class ServletHttp extends HttpServlet {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain");
        try (final PrintWriter out = resp.getWriter()) {

            out.println(new ProductDAO(CREDS.getConnection()).all());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        final String name = req.getParameter("name");
        final String manufacture = req.getParameter("manufacturer");
        final String quantity = req.getParameter("quantity");

        if (name == null || manufacture == null || quantity == null) {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            return;
        }

        try {

            new ProductDAO(CREDS.getConnection())
                    .create(new ProductsRecord(name, manufacture, Integer.parseInt(quantity)));
            resp.sendError(HttpStatus.OK_200);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
