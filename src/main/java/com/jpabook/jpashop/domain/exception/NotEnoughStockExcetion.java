package com.jpabook.jpashop.domain.exception;

public class NotEnoughStockExcetion extends RuntimeException {
    public NotEnoughStockExcetion() {
        super();
    }

    public NotEnoughStockExcetion(String message) {
        super(message);
    }

    public NotEnoughStockExcetion(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockExcetion(Throwable cause) {
        super(cause);
    }
}
