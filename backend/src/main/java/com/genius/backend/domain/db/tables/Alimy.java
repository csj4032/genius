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
public class Alimy extends TableImpl<Record> {

    private static final long serialVersionUID = -1453951308;

    /**
     * The reference instance of <code>genius.alimy</code>
     */
    public static final Alimy ALIMY = new Alimy();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>genius.alimy.ID</code>.
     */
    public final TableField<Record, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>genius.alimy.USER_ID</code>.
     */
    public final TableField<Record, Integer> USER_ID = createField("USER_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>genius.alimy.STATUS</code>.
     */
    public final TableField<Record, String> STATUS = createField("STATUS", org.jooq.impl.SQLDataType.CHAR(1).nullable(false).defaultValue(org.jooq.impl.DSL.inline("'1'", org.jooq.impl.SQLDataType.CHAR)), this, "");

    /**
     * The column <code>genius.alimy.SUBJECT</code>.
     */
    public final TableField<Record, String> SUBJECT = createField("SUBJECT", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>genius.alimy.MESSAGE</code>.
     */
    public final TableField<Record, String> MESSAGE = createField("MESSAGE", org.jooq.impl.SQLDataType.VARCHAR(500).nullable(false), this, "");

    /**
     * The column <code>genius.alimy.REG_DATETIME</code>.
     */
    public final TableField<Record, Timestamp> REG_DATETIME = createField("REG_DATETIME", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>genius.alimy.MOD_DATETIME</code>.
     */
    public final TableField<Record, Timestamp> MOD_DATETIME = createField("MOD_DATETIME", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>genius.alimy</code> table reference
     */
    public Alimy() {
        this(DSL.name("alimy"), null);
    }

    /**
     * Create an aliased <code>genius.alimy</code> table reference
     */
    public Alimy(String alias) {
        this(DSL.name(alias), ALIMY);
    }

    /**
     * Create an aliased <code>genius.alimy</code> table reference
     */
    public Alimy(Name alias) {
        this(alias, ALIMY);
    }

    private Alimy(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Alimy(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Alimy(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, ALIMY);
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
        return Arrays.<Index>asList(Indexes.ALIMY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<Record, Integer> getIdentity() {
        return Keys.IDENTITY_ALIMY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_ALIMY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<Record>> getKeys() {
        return Arrays.<UniqueKey<Record>>asList(Keys.KEY_ALIMY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Alimy as(String alias) {
        return new Alimy(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Alimy as(Name alias) {
        return new Alimy(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Alimy rename(String name) {
        return new Alimy(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Alimy rename(Name name) {
        return new Alimy(name, null);
    }
}
