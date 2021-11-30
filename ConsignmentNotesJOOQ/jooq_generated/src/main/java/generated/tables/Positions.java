/*
 * This file is generated by jOOQ.
 */
package generated.tables;


import generated.Keys;
import generated.Public;
import generated.tables.records.PositionsRecord;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Positions extends TableImpl<PositionsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.positions</code>
     */
    public static final Positions POSITIONS = new Positions();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PositionsRecord> getRecordType() {
        return PositionsRecord.class;
    }

    /**
     * The column <code>public.positions.no</code>.
     */
    public final TableField<PositionsRecord, Integer> NO = createField(DSL.name("no"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.positions.price</code>.
     */
    public final TableField<PositionsRecord, BigDecimal> PRICE = createField(DSL.name("price"), SQLDataType.NUMERIC(19, 2).nullable(false), this, "");

    /**
     * The column <code>public.positions.product</code>.
     */
    public final TableField<PositionsRecord, String> PRODUCT = createField(DSL.name("product"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.positions.quantity</code>.
     */
    public final TableField<PositionsRecord, Integer> QUANTITY = createField(DSL.name("quantity"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.positions.consignment_note_no</code>.
     */
    public final TableField<PositionsRecord, Integer> CONSIGNMENT_NOTE_NO = createField(DSL.name("consignment_note_no"), SQLDataType.INTEGER.nullable(false), this, "");

    private Positions(Name alias, Table<PositionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Positions(Name alias, Table<PositionsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.positions</code> table reference
     */
    public Positions(String alias) {
        this(DSL.name(alias), POSITIONS);
    }

    /**
     * Create an aliased <code>public.positions</code> table reference
     */
    public Positions(Name alias) {
        this(alias, POSITIONS);
    }

    /**
     * Create a <code>public.positions</code> table reference
     */
    public Positions() {
        this(DSL.name("positions"), null);
    }

    public <O extends Record> Positions(Table<O> child, ForeignKey<O, PositionsRecord> key) {
        super(child, key, POSITIONS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<PositionsRecord, Integer> getIdentity() {
        return (Identity<PositionsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<PositionsRecord> getPrimaryKey() {
        return Keys.POSITIONS_PK;
    }

    @Override
    public List<ForeignKey<PositionsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.POSITIONS__POSITIONS_PRODUCT_FKEY, Keys.POSITIONS__POSITIONS_CONSIGNMENT_NOTE_NO_FKEY);
    }

    private transient Products _products;
    private transient ConsignmentNotes _consignmentNotes;

    public Products products() {
        if (_products == null)
            _products = new Products(this, Keys.POSITIONS__POSITIONS_PRODUCT_FKEY);

        return _products;
    }

    public ConsignmentNotes consignmentNotes() {
        if (_consignmentNotes == null)
            _consignmentNotes = new ConsignmentNotes(this, Keys.POSITIONS__POSITIONS_CONSIGNMENT_NOTE_NO_FKEY);

        return _consignmentNotes;
    }

    @Override
    public Positions as(String alias) {
        return new Positions(DSL.name(alias), this);
    }

    @Override
    public Positions as(Name alias) {
        return new Positions(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Positions rename(String name) {
        return new Positions(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Positions rename(Name name) {
        return new Positions(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, BigDecimal, String, Integer, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}