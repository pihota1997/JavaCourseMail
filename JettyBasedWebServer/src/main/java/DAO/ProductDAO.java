package DAO;

import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;

import static generated.Tables.PRODUCTS;

public class ProductDAO {

    private @NotNull Connection connection;

    public ProductDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    public @NotNull Result<ProductsRecord> all() {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        return context
                .select()
                .from(PRODUCTS)
                .fetchInto(PRODUCTS);
    }

    public void create(@NotNull ProductsRecord entity) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        context
                .insertInto(PRODUCTS, PRODUCTS.NAME, PRODUCTS.MANUFACTURER, PRODUCTS.QUANTITY)
                .values(entity.getName(), entity.getManufacturer(), entity.getQuantity())
                .execute();
    }
}
