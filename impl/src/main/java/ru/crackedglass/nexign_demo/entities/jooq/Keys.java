/*
 * This file is generated by jOOQ.
 */
package ru.crackedglass.nexign_demo.entities.jooq;


import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

import ru.crackedglass.nexign_demo.entities.jooq.tables.Cdr;
import ru.crackedglass.nexign_demo.entities.jooq.tables.records.CdrRecord;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<CdrRecord> PK_CDR = Internal.createUniqueKey(Cdr.CDR, DSL.name("PK_CDR"), new TableField[] { Cdr.CDR.CDR_ID }, true);
}
