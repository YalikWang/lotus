package com.lotus.transaction;

public class NotSupportTransaction implements Transaction {
    @Override
    public void begin() {

    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void rollback(String point) {

    }
}
