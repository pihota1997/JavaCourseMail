package DAO;

import commons.DAO;
import entity.Organization;
import entity.Position;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static generated.Tables.ORGANIZATIONS;
import static generated.Tables.POSITIONS;

public class PositionDAO implements DAO<Position> {
    private final @NotNull Connection connection;

    public PositionDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @Override
    public @NotNull Position get(String id) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);
        Position position = null;

        final Result<Record> record = context
                .select()
                .from(POSITIONS)
                .where(POSITIONS.NO.eq(Integer.parseInt(id)))
                .fetch();
        if (record.isEmpty()) {
            throw new IllegalStateException("Record with No = " + id + " not found");
        } else {
            for (Record rec : record) {
                position = new Position(rec.getValue(POSITIONS.NO),
                        rec.getValue(POSITIONS.PRICE), rec.getValue(POSITIONS.PRODUCT),
                        rec.getValue(POSITIONS.QUANTITY), rec.getValue(POSITIONS.CONSIGNMENT_NOTE_NO));
            }
        }
        return position;
    }

    @Override
    public @NotNull List<Position> all() {
        List<Position> list = new LinkedList<>();
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        final Result<Record> records = context
                .select()
                .from(POSITIONS)
                .fetch();

        for (Record record : records) {
            list.add(new Position(record.getValue(POSITIONS.NO),
                    record.getValue(POSITIONS.PRICE), record.getValue(POSITIONS.PRODUCT),
                    record.getValue(POSITIONS.QUANTITY), record.getValue(POSITIONS.CONSIGNMENT_NOTE_NO)));
        }
        return list;
    }

    @Override
    public void create(@NotNull Position entity) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        context
                .insertInto(POSITIONS, POSITIONS.NO, POSITIONS.PRICE, POSITIONS.PRODUCT,
                        POSITIONS.QUANTITY, POSITIONS.CONSIGNMENT_NOTE_NO)
                .values(entity.getNo(), entity.getPrice(), entity.getProduct(), entity.getQuantity(),
                        entity.getConsignmentNoteNo())
                .execute();
    }

    @Override
    public void update(@NotNull Position entity) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        context
                .update(POSITIONS)
                .set(POSITIONS.PRICE, entity.getPrice())
                .set(POSITIONS.PRODUCT, entity.getProduct())
                .set(POSITIONS.QUANTITY, entity.getQuantity())
                .set(POSITIONS.CONSIGNMENT_NOTE_NO, entity.getConsignmentNoteNo())
                .where(POSITIONS.NO.eq(entity.getNo()))
                .execute();
    }

    @Override
    public void delete(@NotNull Position entity) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        context
                .delete(POSITIONS)
                .where(POSITIONS.NO.eq(entity.getNo()))
                .execute();
    }
}
