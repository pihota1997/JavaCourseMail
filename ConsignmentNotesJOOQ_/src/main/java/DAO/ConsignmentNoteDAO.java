package DAO;

import commons.DAO;
import entity.ConsignmentNote;
import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static generated.Tables.CONSIGNMENT_NOTES;

public class ConsignmentNoteDAO implements DAO<ConsignmentNote> {

    private final @NotNull Connection connection;

    public ConsignmentNoteDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @Override
    public @NotNull ConsignmentNote get(String id) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);
        ConsignmentNote consignmentNote = null;

        final Result<Record> record = context
                .select()
                .from(CONSIGNMENT_NOTES)
                .where(CONSIGNMENT_NOTES.NO.eq(Integer.parseInt(id)))
                .fetch();
        if (record.isEmpty()) {
            throw new IllegalStateException("Record with No = " + id + " not found");
        } else {
            for (Record rec : record) {
                consignmentNote = new ConsignmentNote(rec.getValue(CONSIGNMENT_NOTES.NO),
                        rec.getValue(CONSIGNMENT_NOTES.DATE), rec.getValue(CONSIGNMENT_NOTES.ORGANIZATION));
            }
        }
        return consignmentNote;
    }

    @Override
    public @NotNull List<ConsignmentNote> all() {
        List<ConsignmentNote> list = new LinkedList<>();
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        final Result<Record> records = context
                .select()
                .from(CONSIGNMENT_NOTES)
                .fetch();

        for (Record record : records) {
            list.add(new ConsignmentNote(record.getValue(CONSIGNMENT_NOTES.NO),
                    record.getValue(CONSIGNMENT_NOTES.DATE), record.getValue(CONSIGNMENT_NOTES.ORGANIZATION)));
        }
        return list;
    }

    @Override
    public void create(@NotNull ConsignmentNote entity) {

        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        context
                .insertInto(CONSIGNMENT_NOTES, CONSIGNMENT_NOTES.NO,
                        CONSIGNMENT_NOTES.DATE, CONSIGNMENT_NOTES.ORGANIZATION)
                .values(entity.getNo(), entity.getDate(), entity.getOrganizationTIN())
                .execute();
    }

    @Override
    public void update(@NotNull ConsignmentNote entity) {

        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        context
                .update(CONSIGNMENT_NOTES)
                .set(CONSIGNMENT_NOTES.DATE, entity.getDate())
                .set(CONSIGNMENT_NOTES.ORGANIZATION, entity.getOrganizationTIN())
                .where(CONSIGNMENT_NOTES.NO.eq(entity.getNo()))
                .execute();
    }

    @Override
    public void delete(@NotNull ConsignmentNote entity) {

        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        context
                .delete(CONSIGNMENT_NOTES)
                .where(CONSIGNMENT_NOTES.NO.eq(entity.getNo()))
                .execute();

    }
}
