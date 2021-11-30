package Server;

import DAO.ProductDAO;
import DAO.ProductPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.dbConnection.JDBCCredentials;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.Result;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static java.lang.System.out;

public final class SecondaryFunction {

    private List<ProductPOJO> getData(Result<ProductsRecord> result) {
        List<ProductPOJO> pojos = new LinkedList<>();
        for (ProductsRecord productsRecord : result) {
            ProductPOJO productPOJO = new ProductPOJO(productsRecord.getId(),
                    productsRecord.getName(), productsRecord.getManufacturer(), productsRecord.getQuantity());
            out.println(productPOJO);
            pojos.add(productPOJO);
        }
        return pojos;
    }

    public String convertToJSON(Result<ProductsRecord> result) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(getData(result));
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
                                                      @NotNull Integer quantity){
        return new ProductsRecord(name, manufacturer, quantity);
    }

    public static SecondaryFunction createSecondaryFunction(){
        return new SecondaryFunction();
    }
}
