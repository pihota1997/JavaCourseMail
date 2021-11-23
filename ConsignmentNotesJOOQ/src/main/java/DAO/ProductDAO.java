package DAO;

import commons.DAO;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;

import static generated.Tables.PRODUCTS;

public class ProductDAO implements DAO<ProductsRecord> {

    private static DSLContext context;

    public ProductDAO(@NotNull Connection connection) {
        context = DSL.using(connection, SQLDialect.POSTGRES);
    }


    @Override
    public @NotNull ProductsRecord get(String id) {
        return context
                .select()
                .from(PRODUCTS)
                .where(PRODUCTS.INTERNAL_CODE.eq(id))
                .fetchOneInto(PRODUCTS);
    }

    @Override
    public @NotNull Result<ProductsRecord> all() {
        return context
                .select()
                .from(PRODUCTS)
                .fetchInto(PRODUCTS);
    }

    @Override
    public void create(@NotNull ProductsRecord entity) {
       context
                .insertInto(PRODUCTS, PRODUCTS.NAME, PRODUCTS.INTERNAL_CODE)
                .values(entity.getName(), entity.getInternalCode())
                .execute();
    }

    @Override
    public void update(@NotNull ProductsRecord entity) {
       context
                .update(PRODUCTS)
                .set(PRODUCTS.NAME, entity.getName())
                .where(PRODUCTS.INTERNAL_CODE.eq(entity.getInternalCode()))
                .execute();
    }

    @Override
    public void delete(@NotNull ProductsRecord entity) {
       context
                .delete(PRODUCTS)
                .where(PRODUCTS.INTERNAL_CODE.eq(entity.getInternalCode()))
                .execute();
    }
}
