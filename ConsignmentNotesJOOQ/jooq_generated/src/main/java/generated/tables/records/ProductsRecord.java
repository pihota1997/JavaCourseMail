/*
 * This file is generated by jOOQ.
 */
package generated.tables.records;


import generated.tables.Products;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProductsRecord extends UpdatableRecordImpl<ProductsRecord> implements Record2<String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.products.name</code>.
     */
    public ProductsRecord setName(String value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.products.name</code>.
     */
    public String getName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.products.internal_code</code>.
     */
    public ProductsRecord setInternalCode(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.products.internal_code</code>.
     */
    public String getInternalCode() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<String, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Products.PRODUCTS.NAME;
    }

    @Override
    public Field<String> field2() {
        return Products.PRODUCTS.INTERNAL_CODE;
    }

    @Override
    public String component1() {
        return getName();
    }

    @Override
    public String component2() {
        return getInternalCode();
    }

    @Override
    public String value1() {
        return getName();
    }

    @Override
    public String value2() {
        return getInternalCode();
    }

    @Override
    public ProductsRecord value1(String value) {
        setName(value);
        return this;
    }

    @Override
    public ProductsRecord value2(String value) {
        setInternalCode(value);
        return this;
    }

    @Override
    public ProductsRecord values(String value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProductsRecord
     */
    public ProductsRecord() {
        super(Products.PRODUCTS);
    }

    /**
     * Create a detached, initialised ProductsRecord
     */
    public ProductsRecord(String name, String internalCode) {
        super(Products.PRODUCTS);

        setName(name);
        setInternalCode(internalCode);
    }
}
