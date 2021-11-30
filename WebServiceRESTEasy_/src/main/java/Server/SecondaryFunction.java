package Server;

import DAO.ProductPOJO;
import commons.ServerConnection.GuiceListener;
import generated.tables.records.ProductsRecord;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jooq.Result;

import javax.swing.text.AbstractDocument;
import java.util.LinkedList;
import java.util.List;

import static java.lang.System.out;

public final class SecondaryFunction {

    public static List<ProductPOJO> getInfo(Result<ProductsRecord> result) {
        List<ProductPOJO> pojos = new LinkedList<>();
        for (ProductsRecord productsRecord : result) {
            ProductPOJO productPOJO = new ProductPOJO(productsRecord.getId(),
                    productsRecord.getName(), productsRecord.getManufacturer(), productsRecord.getQuantity());
            out.println(productPOJO);
            pojos.add(productPOJO);
        }
        return pojos;
    }
}
