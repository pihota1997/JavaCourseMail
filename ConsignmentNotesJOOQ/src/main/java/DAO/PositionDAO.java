package DAO;

import commons.DAO;
import generated.tables.records.PositionsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;

import static generated.Tables.POSITIONS;

public class PositionDAO implements DAO<PositionsRecord> {
    private static DSLContext context;

    public PositionDAO(@NotNull Connection connection) {
        context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    @Override
    public @NotNull PositionsRecord get(String id) {
        return context
                .select()
                .from(POSITIONS)
                .where(POSITIONS.NO.eq(Integer.parseInt(id)))
                .fetchOneInto(POSITIONS);

    }

    @Override
    public @NotNull Result<PositionsRecord> all() {
        return context
                .select()
                .from(POSITIONS)
                .fetchInto(POSITIONS);
    }

    @Override
    public void create(@NotNull PositionsRecord entity) {
        context
                .insertInto(POSITIONS, POSITIONS.NO, POSITIONS.PRICE, POSITIONS.PRODUCT,
                        POSITIONS.QUANTITY, POSITIONS.CONSIGNMENT_NOTE_NO)
                .values(entity.getNo(), entity.getPrice(), entity.getProduct(), entity.getQuantity(),
                        entity.getConsignmentNoteNo())
                .execute();
    }

    @Override
    public void update(@NotNull PositionsRecord entity) {
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
    public void delete(@NotNull PositionsRecord entity) {
        context
                .delete(POSITIONS)
                .where(POSITIONS.NO.eq(entity.getNo()))
                .execute();
    }
}
