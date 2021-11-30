package DAO;

import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;

import static generated.Tables.PRODUCTS;

public final class ProductDAO {

    private static DSLContext context;

    public ProductDAO(@NotNull Connection connection) {
        context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    public @NotNull Result<ProductsRecord> all() {
        return context
                .select()
                .from(PRODUCTS)
                .fetchInto(PRODUCTS);
    }

    public void create(@NotNull ProductsRecord entity) {
        context
                .insertInto(PRODUCTS, PRODUCTS.NAME, PRODUCTS.MANUFACTURER, PRODUCTS.QUANTITY)
                .values(entity.getName(), entity.getManufacturer(), entity.getQuantity())
                .execute();
    }

    public ProductsRecord get(String name){
        return context
                .select()
                .from(PRODUCTS)
                .where(PRODUCTS.NAME.eq(name))
                .fetchOneInto(PRODUCTS);
    }

    public @NotNull Result<ProductsRecord> getManufacturerProducts(String manufacturer) {
        return context
                .select()
                .from(PRODUCTS)
                .where(PRODUCTS.MANUFACTURER.eq(manufacturer))
                .fetchInto(PRODUCTS);
    }

    public void delete(@NotNull String name) {
        context
                .delete(PRODUCTS)
                .where(PRODUCTS.NAME.eq(name))
                .execute();
    }
}
