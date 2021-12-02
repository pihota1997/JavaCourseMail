package Server;

import DAO.ProductDAO;
import DTO.ProductPOJO;
import commons.dbConnection.JDBCCredentials;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.Result;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public final class RequestProcessingMethods {

    public List<ProductPOJO> getData(Result<ProductsRecord> result) {
        List<ProductPOJO> pojos = new LinkedList<>();
        for (ProductsRecord productsRecord : result) {
            ProductPOJO productPOJO = new ProductPOJO(productsRecord.getId(),
                    productsRecord.getName(), productsRecord.getManufacturer(), productsRecord.getQuantity());
            pojos.add(productPOJO);
        }
        return pojos;
    }

    public static ProductDAO createProductDao() throws SQLException {
        final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
        return new ProductDAO(CREDS.getConnection());
    }

    public static FileInputStream readWelcomePage() throws FileNotFoundException {
        return new FileInputStream("src/main/resources/static/welcome.html");
    }

    public static FileInputStream readInfoPage() throws FileNotFoundException {
        return new FileInputStream("src/main/resources/static/info");
    }

    public ProductsRecord createProductsRecord(@NotNull String name,
                                               @NotNull String manufacturer,
                                               @NotNull Integer quantity) {
        return new ProductsRecord(name, manufacturer, quantity);
    }

    public static RequestProcessingMethods createSecondaryFunction() {
        return new RequestProcessingMethods();
    }
}
