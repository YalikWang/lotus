package com.lotus.transaction;

public enum TXState {
    /**运行中*/
    RUNING,
    /**回滚*/
    ROLLBACK,
    /**提交*/
    COMMIT
}
