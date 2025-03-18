/*
 * This file is generated by jOOQ.
 */
package ru.crackedglass.nexign_demo.entities.jooq.tables;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import ru.crackedglass.nexign_demo.entities.jooq.DefaultSchema;
import ru.crackedglass.nexign_demo.entities.jooq.Keys;
import ru.crackedglass.nexign_demo.entities.jooq.tables.Cdrs.CdrsPath;
import ru.crackedglass.nexign_demo.entities.jooq.tables.records.SubscribersRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Subscribers extends TableImpl<SubscribersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>SUBSCRIBERS</code>
     */
    public static final Subscribers SUBSCRIBERS = new Subscribers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SubscribersRecord> getRecordType() {
        return SubscribersRecord.class;
    }

    /**
     * The column <code>SUBSCRIBERS.SUBSCRIBER_ID</code>.
     */
    public final TableField<SubscribersRecord, Long> SUBSCRIBER_ID = createField(DSL.name("SUBSCRIBER_ID"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>SUBSCRIBERS.NUMBER</code>.
     */
    public final TableField<SubscribersRecord, String> NUMBER = createField(DSL.name("NUMBER"), SQLDataType.VARCHAR(1000000000).nullable(false), this, "");

    private Subscribers(Name alias, Table<SubscribersRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Subscribers(Name alias, Table<SubscribersRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>SUBSCRIBERS</code> table reference
     */
    public Subscribers(String alias) {
        this(DSL.name(alias), SUBSCRIBERS);
    }

    /**
     * Create an aliased <code>SUBSCRIBERS</code> table reference
     */
    public Subscribers(Name alias) {
        this(alias, SUBSCRIBERS);
    }

    /**
     * Create a <code>SUBSCRIBERS</code> table reference
     */
    public Subscribers() {
        this(DSL.name("SUBSCRIBERS"), null);
    }

    public <O extends Record> Subscribers(Table<O> path, ForeignKey<O, SubscribersRecord> childPath, InverseForeignKey<O, SubscribersRecord> parentPath) {
        super(path, childPath, parentPath, SUBSCRIBERS);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class SubscribersPath extends Subscribers implements Path<SubscribersRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> SubscribersPath(Table<O> path, ForeignKey<O, SubscribersRecord> childPath, InverseForeignKey<O, SubscribersRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private SubscribersPath(Name alias, Table<SubscribersRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public SubscribersPath as(String alias) {
            return new SubscribersPath(DSL.name(alias), this);
        }

        @Override
        public SubscribersPath as(Name alias) {
            return new SubscribersPath(alias, this);
        }

        @Override
        public SubscribersPath as(Table<?> alias) {
            return new SubscribersPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<SubscribersRecord, Long> getIdentity() {
        return (Identity<SubscribersRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<SubscribersRecord> getPrimaryKey() {
        return Keys.PK_SUBSCRIBERS;
    }

    @Override
    public List<UniqueKey<SubscribersRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.CONSTRAINT_B);
    }

    private transient CdrsPath _fk_CallerSubscriber_Cdrs;

    /**
     * Get the implicit to-many join path to the <code>CDRS</code> table, via
     * the <code>FK__CALLER_SUBSCRIBER__CDRS</code> key
     */
    public CdrsPath fk_CallerSubscriber_Cdrs() {
        if (_fk_CallerSubscriber_Cdrs == null)
            _fk_CallerSubscriber_Cdrs = new CdrsPath(this, null, Keys.FK__CALLER_SUBSCRIBER__CDRS.getInverseKey());

        return _fk_CallerSubscriber_Cdrs;
    }

    private transient CdrsPath _fk_ReceiverSubscriber_Cdrs;

    /**
     * Get the implicit to-many join path to the <code>CDRS</code> table, via
     * the <code>FK__RECEIVER_SUBSCRIBER__CDRS</code> key
     */
    public CdrsPath fk_ReceiverSubscriber_Cdrs() {
        if (_fk_ReceiverSubscriber_Cdrs == null)
            _fk_ReceiverSubscriber_Cdrs = new CdrsPath(this, null, Keys.FK__RECEIVER_SUBSCRIBER__CDRS.getInverseKey());

        return _fk_ReceiverSubscriber_Cdrs;
    }

    @Override
    public Subscribers as(String alias) {
        return new Subscribers(DSL.name(alias), this);
    }

    @Override
    public Subscribers as(Name alias) {
        return new Subscribers(alias, this);
    }

    @Override
    public Subscribers as(Table<?> alias) {
        return new Subscribers(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Subscribers rename(String name) {
        return new Subscribers(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Subscribers rename(Name name) {
        return new Subscribers(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Subscribers rename(Table<?> name) {
        return new Subscribers(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Subscribers where(Condition condition) {
        return new Subscribers(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Subscribers where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Subscribers where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Subscribers where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Subscribers where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Subscribers where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Subscribers where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Subscribers where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Subscribers whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Subscribers whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
