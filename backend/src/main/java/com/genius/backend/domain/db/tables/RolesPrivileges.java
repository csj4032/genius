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
public class RolesPrivileges extends TableImpl<Record> {

    private static final long serialVersionUID = -210536012;

    /**
     * The reference instance of <code>genius.roles_privileges</code>
     */
    public static final RolesPrivileges ROLES_PRIVILEGES = new RolesPrivileges();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>genius.roles_privileges.id</code>.
     */
    public final TableField<Record, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>genius.roles_privileges.ROLE_ID</code>.
     */
    public final TableField<Record, Integer> ROLE_ID = createField("ROLE_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>genius.roles_privileges.PRIVILEGE_ID</code>.
     */
    public final TableField<Record, Integer> PRIVILEGE_ID = createField("PRIVILEGE_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>genius.roles_privileges</code> table reference
     */
    public RolesPrivileges() {
        this(DSL.name("roles_privileges"), null);
    }

    /**
     * Create an aliased <code>genius.roles_privileges</code> table reference
     */
    public RolesPrivileges(String alias) {
        this(DSL.name(alias), ROLES_PRIVILEGES);
    }

    /**
     * Create an aliased <code>genius.roles_privileges</code> table reference
     */
    public RolesPrivileges(Name alias) {
        this(alias, ROLES_PRIVILEGES);
    }

    private RolesPrivileges(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private RolesPrivileges(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> RolesPrivileges(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, ROLES_PRIVILEGES);
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
        return Arrays.<Index>asList(Indexes.ROLES_PRIVILEGES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<Record, Integer> getIdentity() {
        return Keys.IDENTITY_ROLES_PRIVILEGES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_ROLES_PRIVILEGES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<Record>> getKeys() {
        return Arrays.<UniqueKey<Record>>asList(Keys.KEY_ROLES_PRIVILEGES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RolesPrivileges as(String alias) {
        return new RolesPrivileges(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RolesPrivileges as(Name alias) {
        return new RolesPrivileges(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public RolesPrivileges rename(String name) {
        return new RolesPrivileges(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public RolesPrivileges rename(Name name) {
        return new RolesPrivileges(name, null);
    }
}
