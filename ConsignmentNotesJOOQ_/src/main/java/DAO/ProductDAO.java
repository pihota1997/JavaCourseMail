package DAO;

import commons.DAO;
import entity.Organization;
import entity.Product;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static generated.Tables.ORGANIZATIONS;
import static generated.Tables.PRODUCTS;

public class ProductDAO implements DAO<Product> {
    private final @NotNull Connection connection;

    public ProductDAO(@NotNull Connection connection) {
        this.connection = connection;
    }


    @Override
    public @NotNull Product get(String id) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);
        Product product = null;

        final Result<Record> record = context
                .select()
                .from(PRODUCTS)
                .where(PRODUCTS.INTERNAL_CODE.eq(id))
                .fetch();
        if (record.isEmpty()) {
            throw new IllegalStateException("Record with internal code = " + id + " not found");
        } else {
            for (Record rec : record) {
                product = new Product(rec.getValue(PRODUCTS.NAME),
                        rec.getValue(PRODUCTS.INTERNAL_CODE));
            }
        }
        return product;
    }

    @Override
    public @NotNull List<Product> all() {
        List<Product> list = new LinkedList<>();
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        final Result<Record> records = context
                .select()
                .from(PRODUCTS)
                .fetch();

        for (Record record : records) {
            list.add(new Product(record.getValue(PRODUCTS.NAME),
                    record.getValue(PRODUCTS.INTERNAL_CODE)));
        }
        return list;
    }

    @Override
    public void create(@NotNull Product entity) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        context
                .insertInto(PRODUCTS, PRODUCTS.NAME, PRODUCTS.INTERNAL_CODE)
                .values(entity.getName(), entity.getInternalCode())
                .execute();
    }

    @Override
    public void update(@NotNull Product entity) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        context
                .update(PRODUCTS)
                .set(PRODUCTS.NAME, entity.getName())
                .where(PRODUCTS.INTERNAL_CODE.eq(entity.getInternalCode()))
                .execute();
    }

    @Override
    public void delete(@NotNull Product entity) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        context
                .delete(PRODUCTS)
                .where(PRODUCTS.INTERNAL_CODE.eq(entity.getInternalCode()))
                .execute();
    }
}
