package com.example.apt_process;

public class IdAlreadyUsedException extends Exception {

    private FactoryAnnotatedClass existing;

    public IdAlreadyUsedException(FactoryAnnotatedClass existing) {
        this.existing = existing;
    }
}
