/*
 * This file is generated by jOOQ.
 */
package generated.tables;


import generated.Keys;
import generated.Public;
import generated.tables.records.ConsignmentNotesRecord;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
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
public class ConsignmentNotes extends TableImpl<ConsignmentNotesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.consignment_notes</code>
     */
    public static final ConsignmentNotes CONSIGNMENT_NOTES = new ConsignmentNotes();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConsignmentNotesRecord> getRecordType() {
        return ConsignmentNotesRecord.class;
    }

    /**
     * The column <code>public.consignment_notes.no</code>.
     */
    public final TableField<ConsignmentNotesRecord, Integer> NO = createField(DSL.name("no"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.consignment_notes.date</code>.
     */
    public final TableField<ConsignmentNotesRecord, LocalDate> DATE = createField(DSL.name("date"), SQLDataType.LOCALDATE.nullable(false), this, "");

    /**
     * The column <code>public.consignment_notes.organization</code>.
     */
    public final TableField<ConsignmentNotesRecord, String> ORGANIZATION = createField(DSL.name("organization"), SQLDataType.VARCHAR(10).nullable(false), this, "");

    private ConsignmentNotes(Name alias, Table<ConsignmentNotesRecord> aliased) {
        this(alias, aliased, null);
    }

    private ConsignmentNotes(Name alias, Table<ConsignmentNotesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.consignment_notes</code> table reference
     */
    public ConsignmentNotes(String alias) {
        this(DSL.name(alias), CONSIGNMENT_NOTES);
    }

    /**
     * Create an aliased <code>public.consignment_notes</code> table reference
     */
    public ConsignmentNotes(Name alias) {
        this(alias, CONSIGNMENT_NOTES);
    }

    /**
     * Create a <code>public.consignment_notes</code> table reference
     */
    public ConsignmentNotes() {
        this(DSL.name("consignment_notes"), null);
    }

    public <O extends Record> ConsignmentNotes(Table<O> child, ForeignKey<O, ConsignmentNotesRecord> key) {
        super(child, key, CONSIGNMENT_NOTES);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<ConsignmentNotesRecord, Integer> getIdentity() {
        return (Identity<ConsignmentNotesRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<ConsignmentNotesRecord> getPrimaryKey() {
        return Keys.CONSIGNMENT_NOTES_PK;
    }

    @Override
    public List<ForeignKey<ConsignmentNotesRecord, ?>> getReferences() {
        return Arrays.asList(Keys.CONSIGNMENT_NOTES__CONSIGNMENT_NOTES_ORGANIZATION_FKEY);
    }

    private transient Organizations _organizations;

    public Organizations organizations() {
        if (_organizations == null)
            _organizations = new Organizations(this, Keys.CONSIGNMENT_NOTES__CONSIGNMENT_NOTES_ORGANIZATION_FKEY);

        return _organizations;
    }

    @Override
    public ConsignmentNotes as(String alias) {
        return new ConsignmentNotes(DSL.name(alias), this);
    }

    @Override
    public ConsignmentNotes as(Name alias) {
        return new ConsignmentNotes(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ConsignmentNotes rename(String name) {
        return new ConsignmentNotes(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ConsignmentNotes rename(Name name) {
        return new ConsignmentNotes(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, LocalDate, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
