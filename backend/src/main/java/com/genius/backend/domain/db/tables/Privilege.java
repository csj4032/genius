/*
 * This file is generated by jOOQ.
 */
package com.genius.backend.domain.db.tables;


import com.genius.backend.domain.db.Genius;
import com.genius.backend.domain.db.Indexes;
import com.genius.backend.domain.db.Keys;

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
public class Privilege extends TableImpl<Record> {

    private static final long serialVersionUID = 1769585173;

    /**
     * The reference instance of <code>genius.privilege</code>
     */
    public static final Privilege PRIVILEGE = new Privilege();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>genius.privilege.ID</code>.
     */
    public final TableField<Record, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>genius.privilege.NAME</code>.
     */
    public final TableField<Record, String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR(45).nullable(false), this, "");

    /**
     * Create a <code>genius.privilege</code> table reference
     */
    public Privilege() {
        this(DSL.name("privilege"), null);
    }

    /**
     * Create an aliased <code>genius.privilege</code> table reference
     */
    public Privilege(String alias) {
        this(DSL.name(alias), PRIVILEGE);
    }

    /**
     * Create an aliased <code>genius.privilege</code> table reference
     */
    public Privilege(Name alias) {
        this(alias, PRIVILEGE);
    }

    private Privilege(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Privilege(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Privilege(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, PRIVILEGE);
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
        return Arrays.<Index>asList(Indexes.PRIVILEGE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<Record, Integer> getIdentity() {
        return Keys.IDENTITY_PRIVILEGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_PRIVILEGE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<Record>> getKeys() {
        return Arrays.<UniqueKey<Record>>asList(Keys.KEY_PRIVILEGE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Privilege as(String alias) {
        return new Privilege(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Privilege as(Name alias) {
        return new Privilege(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Privilege rename(String name) {
        return new Privilege(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Privilege rename(Name name) {
        return new Privilege(name, null);
    }
}
