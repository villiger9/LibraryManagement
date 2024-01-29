// LogExecutionTimeEnum.java
package com.example.library.aspect;

public enum LogExecutionTimeEnum {
    ADD_BOOK("Add Book"),
    UPDATE_BOOK("Update Book"),
    DELETE_BOOK("Delete Book"),
    ADD_PATRON("Add Patron"),
    UPDATE_PATRON("Update Patron"),
    DELETE_PATRON("Delete Patron"),
    BORROW_BOOK("Borrow Book"),
    RETURN_BOOK("Return Book");

    private final String operation;

    LogExecutionTimeEnum(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
