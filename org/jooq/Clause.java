package org.jooq;

public enum Clause {
   CONSTRAINT,
   CATALOG,
   CATALOG_REFERENCE,
   SCHEMA,
   SCHEMA_REFERENCE,
   SEQUENCE,
   SEQUENCE_REFERENCE,
   TABLE,
   TABLE_ALIAS,
   TABLE_REFERENCE,
   TABLE_JOIN,
   TABLE_JOIN_INNER,
   TABLE_JOIN_CROSS,
   TABLE_JOIN_NATURAL,
   TABLE_JOIN_OUTER_LEFT,
   TABLE_JOIN_OUTER_RIGHT,
   TABLE_JOIN_OUTER_FULL,
   TABLE_JOIN_NATURAL_OUTER_LEFT,
   TABLE_JOIN_NATURAL_OUTER_RIGHT,
   TABLE_JOIN_CROSS_APPLY,
   TABLE_JOIN_OUTER_APPLY,
   TABLE_JOIN_SEMI_LEFT,
   TABLE_JOIN_ANTI_LEFT,
   TABLE_JOIN_STRAIGHT,
   TABLE_JOIN_ON,
   TABLE_JOIN_USING,
   TABLE_JOIN_PARTITION_BY,
   TABLE_VALUES,
   FIELD,
   FIELD_ALIAS,
   FIELD_REFERENCE,
   FIELD_VALUE,
   FIELD_CASE,
   FIELD_ROW,
   FIELD_FUNCTION,
   CONDITION,
   CONDITION_IS_NULL,
   CONDITION_IS_NOT_NULL,
   CONDITION_COMPARISON,
   CONDITION_BETWEEN,
   CONDITION_BETWEEN_SYMMETRIC,
   CONDITION_NOT_BETWEEN,
   CONDITION_NOT_BETWEEN_SYMMETRIC,
   CONDITION_OVERLAPS,
   CONDITION_AND,
   CONDITION_OR,
   CONDITION_NOT,
   CONDITION_IN,
   CONDITION_NOT_IN,
   CONDITION_EXISTS,
   CONDITION_NOT_EXISTS,
   WITH,
   SELECT,
   SELECT_UNION,
   SELECT_UNION_ALL,
   SELECT_INTERSECT,
   SELECT_INTERSECT_ALL,
   SELECT_EXCEPT,
   SELECT_EXCEPT_ALL,
   SELECT_SELECT,
   SELECT_INTO,
   SELECT_FROM,
   SELECT_WHERE,
   SELECT_START_WITH,
   SELECT_CONNECT_BY,
   SELECT_GROUP_BY,
   SELECT_HAVING,
   SELECT_WINDOW,
   SELECT_ORDER_BY,
   INSERT,
   INSERT_INSERT_INTO,
   INSERT_VALUES,
   INSERT_SELECT,
   INSERT_ON_DUPLICATE_KEY_UPDATE,
   INSERT_ON_DUPLICATE_KEY_UPDATE_ASSIGNMENT,
   INSERT_RETURNING,
   UPDATE,
   UPDATE_UPDATE,
   UPDATE_SET,
   UPDATE_SET_ASSIGNMENT,
   UPDATE_FROM,
   UPDATE_WHERE,
   UPDATE_RETURNING,
   DELETE,
   DELETE_DELETE,
   DELETE_WHERE,
   DELETE_RETURNING,
   MERGE,
   MERGE_MERGE_INTO,
   MERGE_USING,
   MERGE_ON,
   MERGE_WHEN_MATCHED_THEN_UPDATE,
   MERGE_SET,
   MERGE_SET_ASSIGNMENT,
   MERGE_WHERE,
   MERGE_DELETE_WHERE,
   MERGE_WHEN_NOT_MATCHED_THEN_INSERT,
   MERGE_VALUES,
   TRUNCATE,
   TRUNCATE_TRUNCATE,
   CREATE_TABLE,
   CREATE_TABLE_NAME,
   CREATE_TABLE_AS,
   CREATE_TABLE_COLUMNS,
   CREATE_TABLE_CONSTRAINTS,
   CREATE_SCHEMA,
   CREATE_SCHEMA_NAME,
   CREATE_VIEW,
   CREATE_VIEW_NAME,
   CREATE_VIEW_AS,
   CREATE_INDEX,
   CREATE_SEQUENCE,
   CREATE_SEQUENCE_SEQUENCE,
   ALTER_SEQUENCE,
   ALTER_SEQUENCE_SEQUENCE,
   ALTER_SEQUENCE_RESTART,
   ALTER_SEQUENCE_RENAME,
   ALTER_TABLE,
   ALTER_TABLE_TABLE,
   ALTER_TABLE_RENAME,
   ALTER_TABLE_RENAME_COLUMN,
   ALTER_TABLE_RENAME_CONSTRAINT,
   ALTER_TABLE_ADD,
   ALTER_TABLE_ALTER,
   ALTER_TABLE_ALTER_DEFAULT,
   ALTER_TABLE_DROP,
   ALTER_SCHEMA,
   ALTER_SCHEMA_SCHEMA,
   ALTER_SCHEMA_RENAME,
   ALTER_VIEW,
   ALTER_VIEW_VIEW,
   ALTER_VIEW_RENAME,
   ALTER_INDEX,
   ALTER_INDEX_INDEX,
   ALTER_INDEX_RENAME,
   DROP_SCHEMA,
   DROP_SCHEMA_SCHEMA,
   DROP_VIEW,
   DROP_VIEW_TABLE,
   DROP_TABLE,
   DROP_TABLE_TABLE,
   DROP_INDEX,
   DROP_SEQUENCE,
   DROP_SEQUENCE_SEQUENCE,
   TEMPLATE,
   CUSTOM;
}
