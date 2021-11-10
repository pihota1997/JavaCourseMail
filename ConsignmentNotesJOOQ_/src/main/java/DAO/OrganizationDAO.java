package DAO;

import commons.DAO;
import entity.ConsignmentNote;
import entity.Organization;
import groovyjarjarantlr4.v4.runtime.atn.SemanticContext;
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

import static generated.Tables.CONSIGNMENT_NOTES;
import static generated.Tables.ORGANIZATIONS;

public final class OrganizationDAO implements DAO<Organization> {

    private final @NotNull Connection connection;

    public OrganizationDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @Override
    public @NotNull Organization get(String id) {

        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);
        Organization organization = null;

        final Result<Record> record = context
                .select()
                .from(ORGANIZATIONS)
                .where(ORGANIZATIONS.TIN.eq(id))
                .fetch();
        if (record.isEmpty()) {
            throw new IllegalStateException("Record with TIN = " + id + " not found");
        } else {
            for (Record rec : record) {
                organization = new Organization(rec.getValue(ORGANIZATIONS.NAME),
                        rec.getValue(ORGANIZATIONS.TIN), rec.getValue(ORGANIZATIONS.CURRENT_ACCOUNT));
            }
        }
        return organization;
    }

    @Override
    public @NotNull List<Organization> all() {
        List<Organization> list = new LinkedList<>();
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        final Result<Record> records = context
                .select()
                .from(ORGANIZATIONS)
                .fetch();

        for (Record record : records) {
            list.add(new Organization(record.getValue(ORGANIZATIONS.NAME),
                    record.getValue(ORGANIZATIONS.TIN), record.getValue(ORGANIZATIONS.CURRENT_ACCOUNT)));
        }
        return list;
    }

    @Override
    public void create(@NotNull Organization entity) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        context
                .insertInto(ORGANIZATIONS, ORGANIZATIONS.NAME,
                        ORGANIZATIONS.TIN, ORGANIZATIONS.CURRENT_ACCOUNT)
                .values(entity.getName(), entity.getTIN(), entity.getCurrentAccount())
                .execute();
    }

    @Override
    public void update(@NotNull Organization entity) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        context
                .update(ORGANIZATIONS)
                .set(ORGANIZATIONS.NAME, entity.getName())
                .set(ORGANIZATIONS.CURRENT_ACCOUNT, entity.getCurrentAccount())
                .where(ORGANIZATIONS.TIN.eq(entity.getTIN()))
                .execute();
    }

    @Override
    public void delete(@NotNull Organization entity) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        context
                .delete(ORGANIZATIONS)
                .where(ORGANIZATIONS.TIN.eq(entity.getTIN()))
                .execute();
    }
}
