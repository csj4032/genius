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
public class Logs extends TableImpl<Record> {

    private static final long serialVersionUID = -1485462183;

    /**
     * The reference instance of <code>genius.LOGS</code>
     */
    public static final Logs LOGS = new Logs();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>genius.LOGS.ID</code>.
     */
    public final TableField<Record, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>genius.LOGS.TYPE</code>.
     */
    public final TableField<Record, Integer> TYPE = createField("TYPE", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>genius.LOGS.VALUE</code>.
     */
    public final TableField<Record, String> VALUE = createField("VALUE", org.jooq.impl.SQLDataType.CLOB.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.CLOB)), this, "");

    /**
     * The column <code>genius.LOGS.REG_DATETIME</code>.
     */
    public final TableField<Record, Timestamp> REG_DATETIME = createField("REG_DATETIME", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>genius.LOGS.MOD_DATETIME</code>.
     */
    public final TableField<Record, Timestamp> MOD_DATETIME = createField("MOD_DATETIME", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>genius.LOGS</code> table reference
     */
    public Logs() {
        this(DSL.name("LOGS"), null);
    }

    /**
     * Create an aliased <code>genius.LOGS</code> table reference
     */
    public Logs(String alias) {
        this(DSL.name(alias), LOGS);
    }

    /**
     * Create an aliased <code>genius.LOGS</code> table reference
     */
    public Logs(Name alias) {
        this(alias, LOGS);
    }

    private Logs(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Logs(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Logs(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, LOGS);
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
        return Arrays.<Index>asList(Indexes.LOGS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<Record, Integer> getIdentity() {
        return Keys.IDENTITY_LOGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_LOGS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<Record>> getKeys() {
        return Arrays.<UniqueKey<Record>>asList(Keys.KEY_LOGS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Logs as(String alias) {
        return new Logs(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Logs as(Name alias) {
        return new Logs(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Logs rename(String name) {
        return new Logs(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Logs rename(Name name) {
        return new Logs(name, null);
    }
}
