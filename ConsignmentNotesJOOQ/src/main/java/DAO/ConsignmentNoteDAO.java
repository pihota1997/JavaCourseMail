package DAO;

import commons.DAO;
import generated.tables.records.ConsignmentNotesRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;

import static generated.Tables.CONSIGNMENT_NOTES;

public class ConsignmentNoteDAO implements DAO<ConsignmentNotesRecord> {

    private static DSLContext context;

    public ConsignmentNoteDAO(@NotNull Connection connection) {
        context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    @Override
    public @NotNull ConsignmentNotesRecord get(String id) {
        return context
                .select()
                .from(CONSIGNMENT_NOTES)
                .where(CONSIGNMENT_NOTES.NO.eq(Integer.parseInt(id)))
                .fetchOneInto(CONSIGNMENT_NOTES);
    }

    @Override
    public @NotNull Result<ConsignmentNotesRecord> all() {
        return context
                .select()
                .from(CONSIGNMENT_NOTES)
                .fetchInto(CONSIGNMENT_NOTES);
    }

    @Override
    public void create(@NotNull ConsignmentNotesRecord entity) {
        context
                .insertInto(CONSIGNMENT_NOTES, CONSIGNMENT_NOTES.NO,
                        CONSIGNMENT_NOTES.DATE, CONSIGNMENT_NOTES.ORGANIZATION)
                .values(entity.getNo(), entity.getDate(), entity.getOrganization())
                .execute();
    }

    @Override
    public void update(@NotNull ConsignmentNotesRecord entity) {
        context
                .update(CONSIGNMENT_NOTES)
                .set(CONSIGNMENT_NOTES.DATE, entity.getDate())
                .set(CONSIGNMENT_NOTES.ORGANIZATION, entity.getOrganization())
                .where(CONSIGNMENT_NOTES.NO.eq(entity.getNo()))
                .execute();
    }

    @Override
    public void delete(@NotNull ConsignmentNotesRecord entity) {
        context
                .delete(CONSIGNMENT_NOTES)
                .where(CONSIGNMENT_NOTES.NO.eq(entity.getNo()))
                .execute();
    }
}
