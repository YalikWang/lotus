package com.lotus.transaction;

/**
 * 事务类型
 */
public enum TXType {

    /**
     * 支持当前事务，如果当前没有事务，就新建一个事务
     */
    REQUIRE,

    /**
     *新建事务，如果当前存在事务，把当前事务挂起
     */
    NEW,

    /**
     * 支持当前事务，如果当前没有事务，就以非事务方式执行
     */
    SUPPORT,

    /**
     * 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
     */
    NOT_SUPPORT,

    /**
     * 以非事务方式执行，如果当前存在事务，则抛出异常
     */
    NEVER,

    /**
     * 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，新建事务。
     */
    NESTED
}
