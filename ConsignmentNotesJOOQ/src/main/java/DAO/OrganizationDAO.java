package DAO;

import commons.DAO;
import generated.tables.records.OrganizationsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;

import static generated.Tables.ORGANIZATIONS;

public final class OrganizationDAO implements DAO<OrganizationsRecord> {

    private static DSLContext context;

    public OrganizationDAO(@NotNull Connection connection) {
        context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    @Override
    public @NotNull OrganizationsRecord get(String id) {
        return context
                .select()
                .from(ORGANIZATIONS)
                .where(ORGANIZATIONS.TIN.eq(id))
                .fetchOneInto(ORGANIZATIONS);
    }

    @Override
    public @NotNull Result<OrganizationsRecord> all() {
       return context
                .select()
                .from(ORGANIZATIONS)
                .fetchInto(ORGANIZATIONS);
    }

    @Override
    public void create(@NotNull OrganizationsRecord entity) {
        context
                .insertInto(ORGANIZATIONS, ORGANIZATIONS.NAME,
                        ORGANIZATIONS.TIN, ORGANIZATIONS.CURRENT_ACCOUNT)
                .values(entity.getName(), entity.getTin(), entity.getCurrentAccount())
                .execute();
    }

    @Override
    public void update(@NotNull OrganizationsRecord entity) {
        context
                .update(ORGANIZATIONS)
                .set(ORGANIZATIONS.NAME, entity.getName())
                .set(ORGANIZATIONS.CURRENT_ACCOUNT, entity.getCurrentAccount())
                .where(ORGANIZATIONS.TIN.eq(entity.getTin()))
                .execute();
    }

    @Override
    public void delete(@NotNull OrganizationsRecord entity) {
        context
                .delete(ORGANIZATIONS)
                .where(ORGANIZATIONS.TIN.eq(entity.getTin()))
                .execute();
    }
}
