package DAO;

import commons.dbConnection.JDBCCredentials;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public final class ProductManager {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static ProductDAO createProductDao() throws SQLException {
        return new ProductDAO(CREDS.getConnection());
    }

    public static ProductsRecord createProductsRecord(@NotNull String name,
                                                      @NotNull String manufacturer,
                                                      @NotNull Integer quantity){
        return new ProductsRecord(name, manufacturer, quantity);
    }

}
