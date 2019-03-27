/*
 * This file is generated by jOOQ.
 */
package com.genius.backend.domain.db.tables;


import com.genius.backend.domain.db.Genius;
import com.genius.backend.domain.db.Indexes;
import com.genius.backend.domain.db.Keys;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AlimyUnits extends TableImpl<Record> {

    private static final long serialVersionUID = 1902258687;

    /**
     * The reference instance of <code>genius.ALIMY_UNITS</code>
     */
    public static final AlimyUnits ALIMY_UNITS = new AlimyUnits();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>genius.ALIMY_UNITS.ID</code>.
     */
    public final TableField<Record, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>genius.ALIMY_UNITS.ALIMY_ID</code>.
     */
    public final TableField<Record, Integer> ALIMY_ID = createField("ALIMY_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>genius.ALIMY_UNITS.UNIT_TYPE</code>.
     */
    public final TableField<Record, String> UNIT_TYPE = createField("UNIT_TYPE", org.jooq.impl.SQLDataType.VARCHAR(3).nullable(false), this, "");

    /**
     * The column <code>genius.ALIMY_UNITS.UNIT_VALUE</code>.
     */
    public final TableField<Record, String> UNIT_VALUE = createField("UNIT_VALUE", org.jooq.impl.SQLDataType.VARCHAR(40).nullable(false), this, "");

    /**
     * The column <code>genius.ALIMY_UNITS.REG_DATETIME</code>.
     */
    public final TableField<Record, Timestamp> REG_DATETIME = createField("REG_DATETIME", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>genius.ALIMY_UNITS.MOD_DATETIME</code>.
     */
    public final TableField<Record, Timestamp> MOD_DATETIME = createField("MOD_DATETIME", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>genius.ALIMY_UNITS</code> table reference
     */
    public AlimyUnits() {
        this(DSL.name("ALIMY_UNITS"), null);
    }

    /**
     * Create an aliased <code>genius.ALIMY_UNITS</code> table reference
     */
    public AlimyUnits(String alias) {
        this(DSL.name(alias), ALIMY_UNITS);
    }

    /**
     * Create an aliased <code>genius.ALIMY_UNITS</code> table reference
     */
    public AlimyUnits(Name alias) {
        this(alias, ALIMY_UNITS);
    }

    private AlimyUnits(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private AlimyUnits(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> AlimyUnits(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, ALIMY_UNITS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Genius.GENIUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ALIMY_UNITS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<Record, Integer> getIdentity() {
        return Keys.IDENTITY_ALIMY_UNITS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_ALIMY_UNITS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<Record>> getKeys() {
        return Arrays.<UniqueKey<Record>>asList(Keys.KEY_ALIMY_UNITS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AlimyUnits as(String alias) {
        return new AlimyUnits(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AlimyUnits as(Name alias) {
        return new AlimyUnits(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AlimyUnits rename(String name) {
        return new AlimyUnits(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AlimyUnits rename(Name name) {
        return new AlimyUnits(name, null);
    }
}
