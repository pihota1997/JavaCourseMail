/*
 * This file is generated by jOOQ.
 */
package generated.tables;


import generated.Keys;
import generated.Public;
import generated.tables.records.ProductsRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
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
public class Products extends TableImpl<ProductsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.products</code>
     */
    public static final Products PRODUCTS = new Products();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProductsRecord> getRecordType() {
        return ProductsRecord.class;
    }

    /**
     * The column <code>public.products.name</code>.
     */
    public final TableField<ProductsRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.products.internal_code</code>.
     */
    public final TableField<ProductsRecord, String> INTERNAL_CODE = createField(DSL.name("internal_code"), SQLDataType.VARCHAR.nullable(false), this, "");

    private Products(Name alias, Table<ProductsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Products(Name alias, Table<ProductsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.products</code> table reference
     */
    public Products(String alias) {
        this(DSL.name(alias), PRODUCTS);
    }

    /**
     * Create an aliased <code>public.products</code> table reference
     */
    public Products(Name alias) {
        this(alias, PRODUCTS);
    }

    /**
     * Create a <code>public.products</code> table reference
     */
    public Products() {
        this(DSL.name("products"), null);
    }

    public <O extends Record> Products(Table<O> child, ForeignKey<O, ProductsRecord> key) {
        super(child, key, PRODUCTS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<ProductsRecord> getPrimaryKey() {
        return Keys.PRODUCTS_PK;
    }

    @Override
    public Products as(String alias) {
        return new Products(DSL.name(alias), this);
    }

    @Override
    public Products as(Name alias) {
        return new Products(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Products rename(String name) {
        return new Products(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Products rename(Name name) {
        return new Products(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
